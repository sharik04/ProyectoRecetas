package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienteXRecetaService {

    @Autowired
    private IngredienteXRecetaRepository ingredienteXRecetaRepository;

    // Obtener todos los registros de IngredienteXReceta
    public List<IngredienteXReceta> getAllIngredienteXRecetas() {
        return ingredienteXRecetaRepository.findAll();
    }

    // Obtener un registro por su ID
    public IngredienteXReceta getIngredienteXRecetaById(Long id) {
        return ingredienteXRecetaRepository.findById(id).orElse(null);
    }

    // Guardar un nuevo registro de IngredienteXReceta
    public IngredienteXReceta saveIngredienteXReceta(IngredienteXReceta ingredienteXReceta) {
        return ingredienteXRecetaRepository.save(ingredienteXReceta);
    }

    // Eliminar un registro de IngredienteXReceta
    public void deleteIngredienteXReceta(Long id) {
        ingredienteXRecetaRepository.deleteById(id);
    }

    // Actualizar un registro de IngredienteXReceta
    public IngredienteXReceta updateIngredienteXReceta(Long id, IngredienteXReceta ingredienteXReceta) {
        Optional<IngredienteXReceta> existingIngredienteXReceta = ingredienteXRecetaRepository.findById(id);
        if (existingIngredienteXReceta.isPresent()) {
            IngredienteXReceta updatedIngredienteXReceta = existingIngredienteXReceta.get();
            updatedIngredienteXReceta.setIngrediente(ingredienteXReceta.getIngrediente());
            updatedIngredienteXReceta.setReceta(ingredienteXReceta.getReceta());
            updatedIngredienteXReceta.setCantidad(ingredienteXReceta.getCantidad());
            return ingredienteXRecetaRepository.save(updatedIngredienteXReceta);
        }
        return null;
    }
}

