import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginPage.css';

const API_URL = 'http://localhost:8090/user/login';

const LoginPage = ({ setUser }) => {
  // State to manage user credentials
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const navigate = useNavigate();

  // Handle input field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => ({ ...prevCredentials, [name]: value }));
  };

  // Handle user login
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
      });

      if (response.ok) {
        const userId = await response.json();
        console.log('ID del usuario:', userId); // Debugging: log user ID
        setUser({ id: userId, email: credentials.email });
        alert('Inicio de sesión exitoso!');
        navigate('/recetas');
      } else {
        alert('Error al verificar las credenciales, por favor intenta de nuevo.');
      }
    } catch (error) {
      console.error('Error al iniciar sesión:', error);
      alert('Hubo un problema al iniciar sesión, intenta nuevamente.');
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h1 style={{ textAlign: 'center', marginBottom: '20px' }}>Iniciar Sesión</h1>
      <div className="login-container">
        <div className="login-box">
          <form className="login-form" onSubmit={handleLogin}>
            <input
              type="text"
              name="email"
              placeholder="Correo electrónico"
              value={credentials.email}
              onChange={handleChange}
              required
            />
            <input
              type="password"
              name="password"
              placeholder="Contraseña"
              value={credentials.password}
              onChange={handleChange}
              required
            />
            <button type="submit" className="btn-login">
              Ingresar
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
