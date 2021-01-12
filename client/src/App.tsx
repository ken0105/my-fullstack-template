import React, {useEffect, useState} from 'react';
import './App.css';
import {Todo} from "./component/Todo";
import {NewTodo} from "./component/NewTodo";
import styled from "styled-components"
import AppBar from '@material-ui/core/AppBar';
export class TodoItem {
    id: number | undefined
    task: string | undefined
    isDone: boolean | undefined
}

function App() {
    const [todoItems, setTodoItems] = useState<TodoItem[] | null>(null)
    useEffect(() => {
            reloadTodos()
        }, []
    );
    const reloadTodos = () => {
        fetch("http://localhost:8080/todoItems").then((response) => {
            return response.json()
        }).then((data) => {
            console.log(data)
            setTodoItems(data)
        });
    };
    return <div>
        <AppBar position="static">
        <TodoAppTitle>TodoApp with React(TypeScript) and SpringBoot(Kotlin)</TodoAppTitle>
        <h2>This app is created to learn these Frameworks, languages and TDD(Test Driven Development).</h2>
        </AppBar>
        <NewTodo reloadTodos={reloadTodos}/>
        {todoItems &&
        <div>{todoItems?.map((todoItem) => {
            console.log(todoItem)
            return <Todo key={todoItem.id} todoItem={todoItem} reloadTodos={reloadTodos}/>
        })}</div>
        }
    </div>
}

export default App;

const TodoAppTitle = styled.h1`
    margin: 0;
`