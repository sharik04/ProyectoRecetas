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

    //get all
    public List<IngredienteXReceta> getAllIngredienteXReceta() {
        return repoIngredienteXReceta.findAll();
    }

    public Optional<List<IngredienteXReceta>> getIngredienteXRecetaById(String receta) {
        Receta recetaBuscada = repoReceta.findByNombre(receta).orElse(null);
        if (recetaBuscada != null) {
            return repoIngredienteXReceta.findByReceta(recetaBuscada);
        } else {
            return null;
        }

    }

    public Optional<List<Receta>> getRecetasByIngrediente(String ingrediente) {
        Ingrediente ingredienteBuscado = repoIngrediente.findByNombre(ingrediente).orElse(null);
        if (ingredienteBuscado != null) {
            Optional<List<IngredienteXReceta>> lista = repoIngredienteXReceta.findByIngrediente(ingredienteBuscado);
            if (lista.isPresent()) {
                List<Receta> recetas = new ArrayList<>();
                for (IngredienteXReceta ingredienteXReceta : lista.get()) {
                    Receta receta = repoReceta.findById(ingredienteXReceta.getReceta().getId()).orElse(null);
                    if (receta != null) {
                        recetas.add(receta);
                    }
                }
                return Optional.of(recetas);
            }
        }
        return Optional.empty();
    }

    public IngredienteXReceta saveIngredienteXReceta(IngredienteXReceta ingredienteXReceta) {
        return repoIngredienteXReceta.save(ingredienteXReceta);
    }

    public void deleteIngredienteXReceta(Long id) {
        repoIngredienteXReceta.deleteById(id);
    }

}
