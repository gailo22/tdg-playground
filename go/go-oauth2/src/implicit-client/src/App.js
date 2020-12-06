import logo from './logo.svg';
import './App.css';

import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

// function App() {
//   return (
//     <div className="App">
      
//       <h3>Implicit Grant Type</h3>
//       <div><a href="http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/auth?client_id=implicitClient&response_type=token">Login</a></div>
//       <div><a href="/services">Services</a></div>

//     </div>
//   );
// }

class App extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      access_token: "", 
      expires_in: "", 
      session_state: "", 
      token_type: "", 
    };
  }

  setStateValue = (k, v) => {
    if (this.state[k] !== v) {
      this.setState({[k]: v})
    }
  }

  onCheckStateClick = () => {
    console.log(this.state);
  }

  render() {
    return (
      <Router>
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/services">Services</Link>
              </li>
              <li>
                <Link to="/logout">Logout</Link>
              </li>
            </ul>
          </nav>

          {/* A <Switch> looks through its children <Route>s and
              renders the first one that matches the current URL. */}
          <Switch>
            <Route path="/callback">
              <Callback setStateValue = {this.setStateValue}/>
            </Route>
            <Route path="/services">
              <Services accessToken = {this.state.access_token}/>
            </Route>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </div>
      </Router>
    );
  }
}

function Home() {
  return <h2>Home</h2>;
}

function Login() {
  window.location = "http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/auth?client_id=implicitClient&response_type=token&redirect_uri=http://localhost:3000/callback&scope=getBillingService";
  return null;
}

function Callback(props) {
  console.log(window.location.hash)
  const hashStr = window.location.hash;
  const hashMap = hashStr.substr(1).split("&").reduce((acc, item) => {
    const kv = item.split("=");
    acc[kv[0]] = kv[1];
    return acc;
  }, {});
  console.log(hashMap);

  const {setStateValue} = props;
  setStateValue("access_token", hashMap.access_token);
  setStateValue("expires_in", hashMap.expires_in);
  setStateValue("session_state", hashMap.session_state);
  setStateValue("token_type", hashMap.token_type);
  return <h2>Callback</h2>;
}

class Services extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      data: {}
    }
  }

  componentDidMount() {
    const {accessToken} = this.props;
    const formData = new FormData()
    formData.append("access_token", accessToken);
    fetch("http://localhost:8081/billing/v1/services", {
      method: "POST",
      body: formData
    }).then(response => response.json())
    .then(data => {
      console.log(data);
      this.setState({data})
    });
  }

  render() {
    return <div>
      <h2>Services</h2>
      <div>{JSON.stringify(this.state.data)}</div>
    </div>
  }
}


export default App;
