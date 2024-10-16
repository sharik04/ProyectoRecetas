package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> getAllRecetas() {
        return recetaRepository.findAll();
    }

    public Receta saveReceta(Receta receta) {
        for (Ingrediente ingrediente : receta.getIngredientes()) {
            ingrediente.setReceta(receta); // Asegúrate de que cada ingrediente esté vinculado a la receta
        }
        return recetaRepository.save(receta);
    }

    public Receta getRecetaById(Long id) {
        return recetaRepository.findById(id).orElse(null);
    }

    public void deleteReceta(Long id) {
        recetaRepository.deleteById(id);
    }

    public Receta updateReceta(Long id, Receta receta) {
        Receta existingReceta = recetaRepository.findById(id).orElse(null);
        if (existingReceta != null) {
            existingReceta.setNombre(receta.getNombre());
            existingReceta.setPasos(receta.getPasos());
            existingReceta.setImagen(receta.getImagen());
            existingReceta.setFavorita(receta.isFavorita());

            // Actualiza los ingredientes
            existingReceta.getIngredientes().clear(); // Limpia la lista actual
            for (Ingrediente ingrediente : receta.getIngredientes()) {
                ingrediente.setReceta(existingReceta); // Asocia el ingrediente a la receta
                existingReceta.getIngredientes().add(ingrediente); // Añade el nuevo ingrediente
            }

            return recetaRepository.save(existingReceta);
        }
        return null; // O maneja el caso donde no se encontró la receta
    }


}
