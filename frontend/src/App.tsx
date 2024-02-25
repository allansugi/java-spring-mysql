import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { NavBar } from './NavBar';
import PasswordVault from './PasswordVault';
import Login from './Login/Login';
import Home from './home/Home';

function App() {

  return (
    <BrowserRouter>
      <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path='/login' element={<Login />} />
        <Route path='/passwords' element={<PasswordVault />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
