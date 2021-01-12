import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { StylesProvider } from '@material-ui/core';

ReactDOM.render(
    <React.StrictMode>
        <StylesProvider injectFirst>
        <App/>
        </StylesProvider>
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
