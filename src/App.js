import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import IngredientesList from './components/IngredientesList';
import CreateIngredienteForm from './components/CreateIngredienteForm';
import UpdateIngredienteForm from './components/UpdateIngredienteForm';
import LoginPage from './components/LoginPage';
import UserRecetasPage from './components/UserRecentPage';
import WelcomePage from './components/WelcomePage';
import DashboardPagePage from './components/DashboardPage';
import './App.css';

function App() {
    const [user, setUser] = useState(null);

    return (
        <Router>
            <div className="container">
                {!user ? (
                    <Routes>
                        <Route path="/" element={<WelcomePage />} />
                        <Route path="/login" element={<LoginPage setUser={setUser} />} />
                    </Routes>
                ) : (
                    <div>
                        <nav className="navbar">
                            <ul>
                               
                            </ul>
                        </nav>
                        <Routes>
                            <Route path="/recetas" element={<UserRecetasPage user={user} />} />
                            <Route path="/ingredientes" element={<IngredientesList />} />
                            <Route path="/create" element={<CreateIngredienteForm />} />
                            <Route path="/update/:id" element={<UpdateIngredienteForm />} />
                            <Route path="/graficas" element={<DashboardPagePage user={user}/>}/>
                        </Routes>
                    </div>
                )}
            </div>
        </Router>
    );
}

export default App;