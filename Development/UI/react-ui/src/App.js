import React, { Component } from 'react';
import {Route, Switch, BrowserRouter} from 'react-router-dom';
import {css} from 'emotion';
import 'font-awesome/css/font-awesome.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.css';
import './App.css';
import Conversation from './Conversation';
import Login from "./Login";
import ConversationPreview from './ConversationPreview';
import HomePage from "./HomePage";
import ThreadContainer from "./ThreadContainer";

class App extends Component {
  render() {
      return (
          <div>
              <BrowserRouter>
                  <Switch>
                      <Route path="/home">
                          {() => <HomePage/>}
                      </Route>
                      <Route path="/login">
                          {() => <Login/>}
                      </Route>
                      <Route path="/conversation/:id"
                             component={Conversation}>
                          {/*{() => <Conversation/>}*/}
                      </Route>
                      <Route path="/">
                          {() => {
                              if(localStorage.getItem('id') == null) {
                                  return <Login/>
                              } else {
                                  return <HomePage/>
                              }
                            }
                          }
                      </Route>
                  </Switch>
              </BrowserRouter>
          </div>
    );
  }
}

export default App;