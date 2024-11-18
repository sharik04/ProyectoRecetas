package com.api.Proyecto_Recetas.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;

public interface IngredienteXRecetaRepository  extends JpaRepository<IngredienteXReceta, Long> {
    Optional<List<IngredienteXReceta>> findByReceta(Receta receta);
    Optional<List<IngredienteXReceta>> findByIngrediente(Ingrediente ingrediente);

}
