import Enzyme, {shallow} from 'enzyme';
import EnzymeAdapter from 'enzyme-adapter-react-16'
import * as React from "react";
import {TodoDetail} from "../component/TodoDetail";
import {act} from "react-dom/test-utils";

Enzyme.configure({adapter: new EnzymeAdapter()});

describe("Todo Detail test", () => {
    let wrapper: any
    let spyFetch: any
    let spyReload: ()=> {}

    beforeEach(async ()=>{
        // @ts-ignore
        spyFetch = jest.spyOn(global, 'fetch').mockImplementation(() => Promise.resolve({
            json: () => Promise.resolve([]),
        }));
        const todoItem = {id: 1, task: "task1", isDone: false};
        spyReload = jest.fn()
        wrapper = await shallow(<TodoDetail todoItem={todoItem} reloadTodos={spyReload}/>);
    })

    test('finishes todo with PUT API', async () => {
        await act(async () => {
            wrapper.update()
            expect(spyFetch).toBeCalledTimes(0)
            wrapper.find(".isDoneCheckbox").simulate("change", {target: {checked: true}})
        });

        expect(wrapper.find(".isDoneCheckbox").prop("checked")).toBe(true)
        expect(spyFetch).toHaveBeenCalledWith(process.env.REACT_APP_API_BASE_URL + "todoItems/1", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({id: 1, task: "task1", isDone: true})
        })
    })

    test('restarts todo with PUT API', async () => {
        const todoItem = {id: 1, task: "task1", isDone: true};
        spyReload = jest.fn()
        wrapper = await shallow(<TodoDetail todoItem={todoItem} reloadTodos={spyReload}/>);

        await act(async () => {
            wrapper.update()
            expect(spyFetch).toBeCalledTimes(0)
            wrapper.find(".isDoneCheckbox").simulate("change", {target: {checked: false}})
        });
        expect(wrapper.find(".isDoneCheckbox").prop("checked")).toBe(false)
        expect(spyFetch).toHaveBeenCalledWith(process.env.REACT_APP_API_BASE_URL + "todoItems/1", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({id: 1, task: "task1", isDone: false})
        })
    })

    test('delete a task with DELETE API', async () => {
        await act(async()=>{
            expect(spyFetch).toBeCalledTimes(0)
            wrapper.find(".todoDeleteButton").simulate("click")
        })
        expect(spyFetch).toHaveBeenCalledWith(process.env.REACT_APP_API_BASE_URL + "todoItems/1", {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
        })
        expect(spyReload).toBeCalledTimes(1)
    })
})

