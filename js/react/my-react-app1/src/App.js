import logo from './logo.svg';
import './App.css';
import Footer from './components/footer/Footer';
import Body from './components/body/Body';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          Header
        </p>
      </header>

      <Body />

      <Footer />
    </div>
  );
}

export default App;
