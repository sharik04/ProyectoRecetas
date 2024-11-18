import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './UpdateIngredienteForm.css';
const UpdateIngredienteForm = () => {
    const { id } = useParams();
    const [ingrediente, setIngrediente] = useState({ name: '', quantity: '', unit: '' });

    useEffect(() => {
        const fetchIngrediente = async () => {
            try {
                const response = await axios.get(`http://localhost:8090/ingredientes/${id}`);
                setIngrediente(response.data);
            } catch (error) {
                console.error('Error fetching ingrediente:', error);
            }
        };
        fetchIngrediente();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setIngrediente({ ...ingrediente, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8090/ingredientes/${id}`, ingrediente);
            alert('Ingrediente actualizado con éxito');
        } catch (error) {
            console.error('Error actualizando ingrediente:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="name" value={ingrediente.name} onChange={handleChange} placeholder="Nombre" required />
            <input type="number" name="quantity" value={ingrediente.quantity} onChange={handleChange} placeholder="Cantidad" required />
            <input type="text" name="unit" value={ingrediente.unit} onChange={handleChange} placeholder="Unidad" required />
            <button type="submit">Actualizar Ingrediente</button>
        </form>
    );
};

export default UpdateIngredienteForm;