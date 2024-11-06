package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private IngredienteXRecetaRepository ingredienteXRecetaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente saveIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente getIngredienteById(Long id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

    public void deleteIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }

    public Ingrediente updateIngrediente(Long id, Ingrediente ingrediente) {
        Ingrediente existingIngrediente = ingredienteRepository.findById(id).orElse(null);
        if (existingIngrediente != null) {
            existingIngrediente.setNombre(ingrediente.getNombre());
            return ingredienteRepository.save(existingIngrediente);
        }
        return null;
    }

    // Método para asociar ingredientes con recetas
    public void asociarIngredienteAReceta(Long ingredienteId, Long recetaId) {
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElse(null);
        Receta receta = recetaRepository.findById(recetaId).orElse(null); // Usar recetaRepository en lugar de RecetaRepository

        if (ingrediente != null && receta != null) {
            IngredienteXReceta ingredienteXReceta = new IngredienteXReceta();
            ingredienteXReceta.setIngrediente(ingrediente);
            ingredienteXReceta.setReceta(receta);
            ingredienteXRecetaRepository.save(ingredienteXReceta);
        }
    }

}

