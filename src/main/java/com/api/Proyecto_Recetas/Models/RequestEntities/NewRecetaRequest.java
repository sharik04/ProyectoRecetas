package com.api.Proyecto_Recetas.Models.RequestEntities;

import java.util.List;

import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRecetaRequest {
    private Receta receta;
    private List<IngredienteXReceta> ingredientesXReceta;
}
