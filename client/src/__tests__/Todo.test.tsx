import Enzyme, {mount} from 'enzyme';
import EnzymeAdapter from 'enzyme-adapter-react-16'
import * as React from "react";
import Todo from "../component/Todo";
import {TodoDetail} from "../component/TodoDetail";
import {NewTodo} from "../component/NewTodo";

Enzyme.configure({adapter: new EnzymeAdapter()});

test('renders title', () => {
    const wrapper = mount(<Todo/>);
    expect(wrapper.text()).toContain("TodoApp with React(TypeScript) and SpringBoot(Kotlin)");
    expect(wrapper.text()).toContain("This app is created to learn these Frameworks, languages and TDD(Test Driven Development).")
});

test('renders registration field', () => {
    const wrapper = mount(<Todo/>);
    console.log(wrapper.debug())
    expect(wrapper.find(NewTodo).exists()).toBe(true)
});

test('renders todo list', async () => {
    const mockSuccessResponse = [{id: 1, task: "task1", isDone: false},
        {id: 2, task: "task2", isDone: true}];
    const mockJsonPromise = Promise.resolve(mockSuccessResponse);
    const mockFetchPromise = Promise.resolve({
        json: () => mockJsonPromise,
    });
    // @ts-ignore
    jest.spyOn(global, 'fetch').mockImplementation(() => mockFetchPromise);

    const wrapper = await mount(<Todo/>);
    wrapper.update()
    // @ts-ignore
    await wrapper.instance().componentDidMount();
    wrapper.update()
    expect(wrapper.find(TodoDetail).at(0).text()).toContain("Finished");
    expect(wrapper.find(TodoDetail).at(0).text()).toContain("task1");
    expect(wrapper.find(TodoDetail).find('input[type="checkbox"]').at(0).prop("checked")).toBe(false);
    expect(wrapper.find(TodoDetail).find(".todoDeleteButton").at(0).exists()).toBe(true)
    expect(wrapper.find(TodoDetail).at(1).text()).toContain("Finished");
    expect(wrapper.find(TodoDetail).at(1).text()).toContain("task2");
    expect(wrapper.find(TodoDetail).find('input[type="checkbox"]').at(1).prop("checked")).toBe(true);
    expect(wrapper.find(TodoDetail).find(".todoDeleteButton").at(1).exists()).toBe(true)
});
