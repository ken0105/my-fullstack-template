import React from "react";
import AppBar from "@material-ui/core/AppBar";
import {NewTodo} from "./NewTodo";
import {TodoDetail} from "./TodoDetail";
import styled from "styled-components";

export class TodoItem {
    id: number | undefined
    task: string | undefined
    isDone: boolean | undefined
}

interface Props {}

interface State {
    todoItems: TodoItem[]
}

export default class Todo extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            todoItems: []
        }
    }

    public async componentDidMount() {
        await this.reloadTodos()
    }

    public reloadTodos = async () => {
        await fetch("http://localhost:8080/todoItems").then((response) => {
            return response.json()
        }).then((data) => {
            this.setState({todoItems: data})
        });
    };

    render() {
        return <>
            <AppBar position="static">
                <TodoAppTitle>The TodoApp</TodoAppTitle>
                <TodoAppSubTitle>This app is created to learn React(TypeScript), SpringBoot(Kotlin) and TDD(Test Driven Development).</TodoAppSubTitle>
            </AppBar>
            <NewTodo reloadTodos={this.reloadTodos}/>
            {this.state.todoItems &&
            <div>{this.state.todoItems?.map((todoItem) => {
                return <TodoDetail key={todoItem.id} todoItem={todoItem} reloadTodos={this.reloadTodos}/>
            })}</div>
            }
        </>;
    }

};

const TodoAppTitle = styled.h1`
    margin: 0;
`
const TodoAppSubTitle = styled.h2`
    margin: 0;
    font-size: 18px;
`