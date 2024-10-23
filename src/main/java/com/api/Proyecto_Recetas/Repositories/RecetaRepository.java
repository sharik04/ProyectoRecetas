package com.api.Proyecto_Recetas.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.Proyecto_Recetas.Models.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByUserId(Long userId);
    Optional<Receta>findByNombre(String nombre);
    @Query("SELECT r FROM Receta r WHERE r.nombre LIKE %?1%")
    List<Receta> getSimilarNombre(String nombre);
}
