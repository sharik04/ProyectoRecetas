package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.UserRepository;
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
    @Autowired
    private IngredienteXRecetaRepository ingredienteXRecetaRepository;
    @Autowired
    private UserRepository userRepository;

    // Este método se utilizará para obtener las recetas creadas por un usuario específico
    public List<Receta> getRecetasByUserId(Long userId) {
        return recetaRepository.findByUserId(userId);
    }

    public Receta saveReceta(Receta receta, Long userId) {
        // Buscar al usuario en la base de datos por su ID
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        User user = userOptional.get();

        // Asignar el usuario a la receta
        receta.setUser(user);

        // Asociar los ingredientes a la receta
        for (IngredienteXReceta ingredienteXReceta : receta.getIngredienteXRecetas()) {
            Ingrediente ingrediente = ingredienteXReceta.getIngrediente();

            // Buscar si el ingrediente ya existe en la base de datos por su nombre
            Optional<Ingrediente> existingIngrediente = ingredienteRepository.findByNombre(ingrediente.getNombre());

            if (existingIngrediente.isPresent()) {
                // Si el ingrediente ya existe, reutilízalo
                ingredienteXReceta.setIngrediente(existingIngrediente.get());
            } else {
                // Si no existe, crea uno nuevo y asócialo a la receta
                ingredienteXReceta.setIngrediente(ingrediente);
            }

            ingredienteXReceta.setReceta(receta);
        }

        // Guardar la receta en la base de datos
        return recetaRepository.save(receta);
    }

    // Obtener una receta por su ID
    public Receta getRecetaById(Long id) {
        return recetaRepository.findById(id).orElse(null);
    }

    // Eliminar receta, solo si pertenece al usuario
    public void deleteReceta(Long id, Long userId) {
        Receta receta = recetaRepository.findById(id).orElse(null);

        if (receta != null && receta.getUser().getId().equals(userId)) {
            recetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No tienes permisos para eliminar esta receta.");
        }
    }

    // Actualizar receta, solo si pertenece al usuario
    public Receta updateReceta(Long id, Receta receta, Long userId) {
        Receta existingReceta = recetaRepository.findById(id).orElse(null);
        if (existingReceta != null && existingReceta.getUser().getId().equals(userId)) {
            existingReceta.setNombre(receta.getNombre());
            existingReceta.setPasos(receta.getPasos());
            existingReceta.setImagen(receta.getImagen());
            existingReceta.setFavorita(receta.isFavorita());

            // Actualiza los ingredientes a través de IngredienteXReceta
            existingReceta.getIngredienteXRecetas().clear(); // Limpia la lista actual de ingredientes
            for (IngredienteXReceta ingredienteXReceta : receta.getIngredienteXRecetas()) {
                existingReceta.getIngredienteXRecetas().add(ingredienteXReceta);
            }

            return recetaRepository.save(existingReceta);
        }
        return null; // O lanzar una excepción si no se encuentra la receta o el usuario no tiene permiso
    }
}
