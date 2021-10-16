import Enzyme, {mount} from "enzyme";
import EnzymeAdapter from "enzyme-adapter-react-16";
import {NewTodo} from "../component/NewTodo";
import React from "react";
import {flushPromises} from "../__test_tools/helpers";

Enzyme.configure({adapter: new EnzymeAdapter()});

describe("New Todo test", () => {


    test('renders input box and submit button', () => {
        const spyReloadTodos = jest.fn()
        const wrapper = mount(<NewTodo reloadTodos={spyReloadTodos}/>)
        expect(wrapper.find('input[type="text"]').exists()).toBe(true)
        expect(wrapper.find('button').text()).toBe("Submit")
    })

    test('submit a new todo', async () => {
        const spyReloadTodos = jest.fn()
        const wrapper = mount(<NewTodo reloadTodos={spyReloadTodos}/>)
        // @ts-ignore
        const spyFetch = jest.spyOn(global, 'fetch').mockImplementation(() => Promise.resolve({
            json: () => Promise.resolve([]),
        }));
        wrapper.find('input[type="text"]').simulate("change", {target: {value: "this is a new todo"}})
        wrapper.find('button').simulate("click");
        await flushPromises()
        expect(spyFetch).toHaveBeenCalledWith(process.env.REACT_APP_API_BASE_URL + 'todoItems', {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: "this is a new todo"
        })
        expect(spyReloadTodos).toBeCalledTimes(1)
    })
})

