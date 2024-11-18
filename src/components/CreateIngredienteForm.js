import React, { useState } from 'react';
import axios from 'axios';
import './CreateIngredienteForm.css';

const CreateIngredienteForm = () => {
    const [ingrediente, setIngrediente] = useState({ name: '', quantity: '', unit: '' });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setIngrediente({ ...ingrediente, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8090/ingredientes', ingrediente);
            alert('Ingrediente creado con éxito');
        } catch (error) {
            console.error('Error creando ingrediente:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="name" value={ingrediente.name} onChange={handleChange} placeholder="Nombre" required />
            <input type="number" name="quantity" value={ingrediente.quantity} onChange={handleChange} placeholder="Cantidad" required />
            <input type="text" name="unit" value={ingrediente.unit} onChange={handleChange} placeholder="Unidad" required />
            <button type="submit">Crear Ingrediente</button>
        </form>
    );
};

export default CreateIngredienteForm;