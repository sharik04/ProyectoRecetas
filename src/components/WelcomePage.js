// WelcomePage Component - Página de Bienvenida
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './WelcomePage.css'; // Archivo CSS para estilos específicos

function WelcomePage() {
    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="welcome-container">
            <h1>Bienvenido a Nuestra Aplicación</h1>
            <p>Por favor, inicia sesión o regístrate para continuar.</p>
            <div className="welcome-buttons">
                <button className="btn-login" onClick={goToLogin}>Iniciar Sesión</button>
            </div>
        </div>
    );
}

export default WelcomePage;
