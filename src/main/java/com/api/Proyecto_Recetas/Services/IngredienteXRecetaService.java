package com.api.Proyecto_Recetas.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
@Service
public class IngredienteXRecetaService {

    @Autowired
    private IngredienteXRecetaRepository repoIngredienteXReceta;

    @Autowired
    private RecetaRepository repoReceta;

    @Autowired
    private IngredienteRepository repoIngrediente;

    // Obtener todos los IngredienteXReceta
    public List<IngredienteXReceta> getAllIngredienteXReceta() {
        return repoIngredienteXReceta.findAll();
    }

    // Obtener IngredienteXReceta por el nombre de la receta
    public Optional<List<IngredienteXReceta>> getIngredienteXRecetaByNombreReceta(String receta) {
        return repoReceta.findByNombre(receta)
                .map(repoIngredienteXReceta::findByReceta)
                .orElse(Optional.empty());
    }

    // Obtener todas las recetas que contienen un ingrediente específico
    public Optional<List<Receta>> getRecetasByIngrediente(String ingrediente) {
        return repoIngrediente.findByNombre(ingrediente)
                .map(ing -> {
                    List<IngredienteXReceta> ingredienteXRecetas = repoIngredienteXReceta.findByIngrediente(ing).orElse(new ArrayList<>());
                    List<Receta> recetas = new ArrayList<>();
                    ingredienteXRecetas.forEach(ixr -> recetas.add(ixr.getReceta()));
                    return Optional.of(recetas);
                }).orElse(Optional.empty());
    }

    // Guardar un IngredienteXReceta
    public IngredienteXReceta saveIngredienteXReceta(IngredienteXReceta ingredienteXReceta) {
        return repoIngredienteXReceta.save(ingredienteXReceta);
    }

    // Eliminar un IngredienteXReceta por ID
    public void deleteIngredienteXReceta(Long id) {
        repoIngredienteXReceta.deleteById(id);
    }
}

