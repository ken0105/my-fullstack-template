import {useState} from "react";
import styled from "styled-components";
import {Button} from "@material-ui/core";
import TextField from '@material-ui/core/TextField';

type Props = {
    reloadTodos: () => void
}

export const NewTodo: React.FC<Props> = ({reloadTodos}) => {
    const [task, setTask] = useState("")
    const registerTask = async () => {
        await fetch( process.env.REACT_APP_API_BASE_URL + 'todoItems', {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: task
        })
        reloadTodos()
    };

    return (
        <NewTodoWrapper>
            <TaskTextarea label="New TodoDetail"
                       variant="outlined"
                       value={task}
                      onChange={(e) => {setTask(e.target.value)}}/>
            <SubmitButton variant="contained" color="primary"  onClick={registerTask}>Submit</SubmitButton>
        </NewTodoWrapper>
    );
};

const NewTodoWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    margin: 30px 0 30px 0;
`

const TaskTextarea = styled(TextField)`
    width: 70vw
`

const SubmitButton = styled(Button)`

`
