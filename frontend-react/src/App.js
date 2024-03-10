import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import BeerList from './BeerList';

class App extends Component {
  render() {
      return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/beers' exact={true} component={BeerList}/>
          </Switch>
        </Router>
      );
  }
}

export default App;
