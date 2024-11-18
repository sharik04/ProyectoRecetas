import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Importar useNavigate
import './DashboardPage.css';
import { Bar } from 'react-chartjs-2';
import Chart from 'chart.js/auto';

function DashboardPage() {
  const navigate = useNavigate(); // Inicializa el hook de navegación
  const [favoritesData, setFavoritesData] = useState(null);

  useEffect(() => {
    // Llamada a la API para obtener los datos de las recetas
    fetch('http://localhost:8090/recetas')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
        // Calcular el número de recetas favoritas y no favoritas
        const favorites = data.filter((receta) => receta.favorita === true).length;
        const nonFavorites = data.filter((receta) => receta.favorita === false).length;
        setFavoritesData({ favorites, nonFavorites });
      })
      .catch((error) => {
        console.error('Error fetching recetas:', error);
      });
  }, []);

  if (!favoritesData) {
    return <div>Cargando datos...</div>;
  }

  // Asegurarse de que ambas barras se muestren correctamente incluso si alguna tiene valor 0
  const data = {
    labels: ['Favoritas', 'No Favoritas'],
    datasets: [
      {
        label: 'Recetas',
        data: [favoritesData.favorites || 0, favoritesData.nonFavorites || 0],
        backgroundColor: ['#76c893', '#f94144'],
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
      title: {
        display: true,
        text: 'Gráfico de Recetas Favoritas vs No Favoritas',
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        min: 0, // Nivel mínimo en el eje vertical
        suggestedMax: Math.max(favoritesData.favorites, favoritesData.nonFavorites) + 1, // Asegura espacio para ambas barras
      },
    },
  };

  return (
    <div className="dashboard-container">
      <main className="dashboard-main centered">
        <h1>Usuario</h1>
        <div className="dashboard-content">
          <div className="info-section">
            <div className="info-header">
            </div>
            <div className="chart-container smaller-chart">
              <Bar data={data} options={options} />
            </div>
          </div>
        </div>
        <button className="back-button" onClick={() => navigate('/recetas')}>
          Volver a Mis Recetas
        </button>
      </main>
    </div>
  );
}

export default DashboardPage;
