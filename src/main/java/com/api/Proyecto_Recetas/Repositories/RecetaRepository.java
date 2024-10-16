package com.api.Proyecto_Recetas.Repositories;

import com.api.Proyecto_Recetas.Models.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}
