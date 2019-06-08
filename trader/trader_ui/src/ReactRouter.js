import React, { Component } from 'react';
import { Router, Route, browserHistory, IndexRoute } from 'react-router';
import App from './App';
import Login from './Login'
class ReactRouter extends Component {
    render(){
        return(
            <Router history={ browserHistory }>
                <Route exact path="/" component={Login}/>
                <Route exact path="/homepage" component={App}/>
            </Router>
        )
    }
}
export default ReactRouter;