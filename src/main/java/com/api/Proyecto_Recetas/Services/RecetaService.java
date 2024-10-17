package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Receta> getAllRecetas() {
        return recetaRepository.findAll();
    }

    public Receta saveReceta(Receta receta) {
        for (int i = 0; i < receta.getIngredientes().size(); i++) {
            Ingrediente ingrediente = receta.getIngredientes().get(i);

            // Buscar si el ingrediente ya existe en la base de datos por su nombre
            Optional<Ingrediente> existingIngrediente = ingredienteRepository.findByNombre(ingrediente.getNombre());

            if (existingIngrediente.isPresent()) {
                // Si el ingrediente ya existe, reutilízalo
                receta.getIngredientes().set(i, existingIngrediente.get());
            } else {
                // Si no existe, crea uno nuevo y asócialo a la receta
                ingrediente.setReceta(receta);
            }
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
            existingReceta.getIngredientes().clear(); // Limpia la lista actual de ingredientes
            for (Ingrediente ingrediente : receta.getIngredientes()) {
                Optional<Ingrediente> existingIngrediente = ingredienteRepository.findByNombre(ingrediente.getNombre());

                if (existingIngrediente.isPresent()) {
                    // Reutiliza el ingrediente existente
                    Ingrediente ingredienteExistente = existingIngrediente.get();

                    // Actualiza los detalles si han cambiado
                    ingredienteExistente.setNombre(ingrediente.getNombre()); // Aquí puedes actualizar otros campos si es necesario

                    existingReceta.getIngredientes().add(ingredienteExistente);
                } else {
                    // Si no existe, crea uno nuevo
                    ingrediente.setReceta(existingReceta);
                    existingReceta.getIngredientes().add(ingrediente);
                }
            }

            return recetaRepository.save(existingReceta);
        }
        return null;
    }

    public List<Receta> getRecetasByUserId(Long userId) {
        return recetaRepository.findByUserId(userId);
    }


}
