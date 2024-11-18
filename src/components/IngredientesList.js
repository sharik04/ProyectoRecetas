import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './IngredientesList.css';

const IngredientesList = () => {
    const [ingredientes, setIngredientes] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchIngredientes = async () => {
            try {
                const response = await axios.get('http://localhost:8090/ingredientes');
                setIngredientes(response.data);
            } catch (error) {
                console.error('Error fetching ingredientes:', error);
            }
        };
        fetchIngredientes();
    }, []);

    const deleteIngrediente = async (id) => {
        try {
            await axios.delete(`http://localhost:8090/ingredientes/${id}`);
            setIngredientes(ingredientes.filter(ingrediente => ingrediente.id !== id));
        } catch (error) {
            console.error('Error deleting ingrediente:', error);
        }
    };

    return (
        <div className="ingredientes-list-container">
            <button className="back-button" onClick={() => navigate('/user-recent')}>Volver a UserRecentPage</button>
            <h1 className="title">Lista de Ingredientes</h1>
            <ul className="ingredientes-list">
                {ingredientes.map((item, index) => (
                    <li key={item.id} className="ingrediente-item">
                        <span className="ingrediente-name">{item.nombre}</span>
                        <div className="ingrediente-actions">
                            <Link to={`/update/${item.id}`} className="edit-link">Editar</Link>
                            <button className="delete-button" onClick={() => deleteIngrediente(item.id)}>Eliminar</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default IngredientesList;