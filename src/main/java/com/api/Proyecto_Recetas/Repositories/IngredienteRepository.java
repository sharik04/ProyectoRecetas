package com.api.Proyecto_Recetas.Repositories;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente,Long> {
    Optional<Ingrediente> findByNombre(String nombre);

}
