import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Importar useNavigate
import axios from 'axios';
import './UserRecentPage.css';
import { FaPen, FaTrash } from 'react-icons/fa';
import { AiOutlinePlus } from 'react-icons/ai';

const UserRecetasPage = ({ user }) => {
  const [recetas, setRecetas] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedReceta, setSelectedReceta] = useState(null);
  const [editReceta, setEditReceta] = useState(null);
  const [newReceta, setNewReceta] = useState(null);

  const navigate = useNavigate(); // Inicializa el hook de navegación

  useEffect(() => {
    const fetchRecetas = async () => {
      try {
        const response = await axios.get(`http://localhost:8090/recetas/user/${user.id}`);
        setRecetas(response.data);
      } catch (error) {
        console.error('Error fetching recetas:', error);
      }
    };
    fetchRecetas();
  }, [user.id]);

  const handleSearch = async (e) => {
    e.preventDefault();
    if (searchTerm.trim() === '') {
      alert('Por favor ingresa un nombre para buscar.');
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8090/recetas/buscarPorNombre?nombre=${searchTerm}`);
      setRecetas(response.data ? [response.data] : []);
    } catch (error) {
      console.error('Error searching for receta:', error);
      alert('No se encontró ninguna receta con ese nombre.');
    }
  };

  const handleListView = async () => {
    try {
      const response = await axios.get(`http://localhost:8090/recetas/user/${user.id}`);
      setRecetas(response.data);
    } catch (error) {
      console.error('Error fetching all recetas:', error);
    }
  };

  const handleToggleFavorita = async (id) => {
    try {
      const response = await axios.post(`http://localhost:8090/recetas/toggleFavorita/${id}`);
      if (response.status === 200) {
        setRecetas((prevRecetas) =>
          prevRecetas.map((receta) =>
            receta.id === id ? { ...receta, favorita: !receta.favorita } : receta
          )
        );
      }
    } catch (error) {
      console.error('Error toggling favorita:', error);
      alert('Hubo un error al cambiar el estado de favorita.');
    }
  };

  const handleDeleteReceta = async (id) => {
    if (window.confirm('¿Estás seguro de que deseas eliminar esta receta?')) {
      try {
        await axios.delete(`http://localhost:8090/recetas/${id}`);
        setRecetas((prevRecetas) => prevRecetas.filter((receta) => receta.id !== id));
        alert('Receta eliminada con éxito.');
      } catch (error) {
        console.error('Error deleting receta:', error);
        alert('Hubo un error al eliminar la receta.');
      }
    }
  };

  const handleViewSteps = (receta) => {
    setSelectedReceta(receta);
  };

  const handleEditReceta = (receta) => {
    setEditReceta({ ...receta });
  };

  const handleCloseModal = () => {
    setSelectedReceta(null);
    setEditReceta(null);
    setNewReceta(null);
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditReceta((prev) => ({ ...prev, [name]: value }));
  };

  const handleSaveChanges = async () => {
    try {
      const response = await axios.put(`http://localhost:8090/recetas/editar/${editReceta.id}`, editReceta);
      if (response.status === 200) {
        setRecetas((prevRecetas) =>
          prevRecetas.map((receta) => (receta.id === editReceta.id ? editReceta : receta))
        );
        handleCloseModal();
      }
    } catch (error) {
      console.error('Error updating receta:', error);
      alert('Hubo un error al actualizar la receta.');
    }
  };

  const handleCreateNewReceta = () => {
    setNewReceta({
      nombre: '',
      pasos: '',
      imagenUrl: '',
      favorita: false,
      tiempo: '',
      user: { id: user.id },
      ingredientesXReceta: []
    });
  };

  const handleNewRecetaChange = (e) => {
    const { name, value } = e.target;
    setNewReceta((prev) => ({ ...prev, [name]: value }));
  };

  const handleSaveNewReceta = async () => {
    try {
      const response = await axios.post('http://localhost:8090/recetas/crearReceta', {
        receta: newReceta,
        ingredientesXReceta: []
      });
      if (response.status === 200) {
        setRecetas((prevRecetas) => [...prevRecetas, newReceta]);
        handleCloseModal();
      }
    } catch (error) {
      console.error('Error creating new receta:', error);
      alert('Hubo un error al crear la receta.');
    }
  };

  return (
    <div className="recetas-container">
      <header className="header">
        <h1 className="header-title">Bienvenido</h1>
        <nav className="navbar">
          <button className="profile-button" onClick={() => navigate('/graficas')}>Analiticas</button> {/* Navegar al Dashboard */}
        </nav>
      </header>
      
      {/* Barra de búsqueda */}
      <div className="recetas-header">
        <h2 className="recetas-title">Mis Recetas</h2>
        <div className="recetas-actions">
          <form onSubmit={handleSearch} className="search-form">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Buscar receta por nombre"
              className="search-input"
            />
            <button type="submit" className="action-button">Buscar</button>
          </form>
          <button className="action-button" onClick={handleListView}>List view</button>
          <button className="add-button" onClick={handleCreateNewReceta}><AiOutlinePlus size={24} /></button>
        </div>
      </div>

      <div className="recetas-grid">
        {recetas.length > 0 ? (
          recetas.map((receta) => (
            <div key={receta.id} className="receta-item">
              <img
                src={receta.imagenUrl}
                alt={receta.nombre}
                className="receta-img"
                crossOrigin="anonymous"
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = 'https://th.bing.com/th/id/OIP.E0ikSvsAttLxWa2DDU8pNAHaFD?rs=1&pid=ImgDetMain';
                }}
              />
              <span
                className="favorita-icon"
                onClick={() => handleToggleFavorita(receta.id)}
              >
                {receta.favorita ? '💚' : '🤍'}
              </span>
              <h3 className="card-title">{receta.nombre}</h3>
              <p className="card-description">Tiempo: {receta.tiempo}</p>
              <p className="card-description">Favorita: {receta.favorita ? 'Sí' : 'No'}</p>
              <div className="receta-actions">
                <button className="edit-button" onClick={() => handleEditReceta(receta)}>
                  <FaPen className="receta-icon" />
                </button>
                <button className="delete-button" onClick={() => handleDeleteReceta(receta.id)}>
                  <FaTrash className="receta-icon" />
                </button>
              </div>
              <button className="view-steps-button" onClick={() => handleViewSteps(receta)}>Ver pasos</button>
            </div>
          ))
        ) : (
          <p>No se encontraron recetas.</p>
        )}
      </div>

      {/* Modal para crear nueva receta */}
      {newReceta && (
        <div className="modal">
          <div className="modal-content">
            <span className="close-button" onClick={handleCloseModal}>
              &times;
            </span>
            <h2>Crear Nueva Receta</h2>
            <form>
              <label>Nombre:</label>
              <input
                type="text"
                name="nombre"
                value={newReceta.nombre}
                onChange={handleNewRecetaChange}
              />
              <label>Pasos:</label>
              <input
                name="pasos"
                value={newReceta.pasos}
                onChange={handleNewRecetaChange}
              />
              <label>Imagen URL:</label>
              <input
                type="text"
                name="imagenUrl"
                value={newReceta.imagenUrl}
                onChange={handleNewRecetaChange}
              />
              <label>Tiempo:</label>
              <input
                type="text"
                name="tiempo"
                value={newReceta.tiempo}
                onChange={handleNewRecetaChange}
              />
              <button type="button" onClick={handleSaveNewReceta} className="action-button">Guardar Receta</button>
            </form>
          </div>
        </div>
      )}

      {/* Modal para editar receta */}
      {editReceta && (
        <div className="modal">
          <div className="modal-content">
            <span className="close-button" onClick={handleCloseModal}>
              &times;
            </span>
            <h2>Editar Receta</h2>
            <form>
              <label>Nombre:</label>
              <input
                type="text"
                name="nombre"
                value={editReceta.nombre}
                onChange={handleEditChange}
              />
              <label>Pasos:</label>
              <input
                name="pasos"
                value={editReceta.pasos}
                onChange={handleEditChange}
              />
              <label>Imagen URL:</label>
              <input
                type="text"
                name="imagenUrl"
                value={editReceta.imagenUrl}
                onChange={handleEditChange}
              />
              <label>Tiempo:</label>
              <input
                type="text"
                name="tiempo"
                value={editReceta.tiempo}
                onChange={handleEditChange}
              />
              <button type="button" onClick={handleSaveChanges} className="action-button">Guardar Cambios</button>
            </form>
          </div>
        </div>
      )}

      {/* Modal para ver los pasos de la receta */}
      {selectedReceta && (
        <div className="modal">
          <div className="modal-content">
            <span className="close-button" onClick={handleCloseModal}>
              &times;
            </span>
            <h2>Pasos de la Receta: {selectedReceta.nombre}</h2>
            <p>{selectedReceta.pasos}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserRecetasPage;
