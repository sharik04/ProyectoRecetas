package com.api.Proyecto_Recetas.Repositories;

import com.api.Proyecto_Recetas.Models.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByUserId(Long userId);
}
