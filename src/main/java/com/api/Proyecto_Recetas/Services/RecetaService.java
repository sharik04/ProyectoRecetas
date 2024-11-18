package com.api.Proyecto_Recetas.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.UsuarioRepository;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository repoReceta;

    @Autowired
    private IngredienteRepository repoIngrediente;

    @Autowired
    private IngredienteXRecetaRepository repoIngredienteXReceta;

    @Autowired
    private UsuarioRepository userRepo;  // Inyección del UsuarioRepository

    public List<Receta> getAllRecetas() {
        return repoReceta.findAll();
    }

    public Receta saveReceta(Receta receta) {
        return repoReceta.save(receta);
    }

    public Receta getRecetaById(Long id) {
        return repoReceta.findById(id).orElse(null);
    }

    public void deleteReceta(Long id) {
        repoReceta.deleteById(id);
    }

    public int crearReceta(Receta receta, List<IngredienteXReceta> ingredientesXReceta) {
        try {
            // Verificar si el usuario existe y asignarlo a la receta
            Optional<Usuario> optionalUser = userRepo.findById(receta.getUser().getId());
            if (optionalUser.isPresent()) {
                receta.setUser(optionalUser.get());
            } else {
                return -1; // Usuario no encontrado
            }

            // Guardar la receta
            Receta savedReceta = repoReceta.save(receta);

            // Guardar ingredientes relacionados con la receta
            for (IngredienteXReceta ingredienteXReceta : ingredientesXReceta) {
                Ingrediente resultIngrediente = repoIngrediente
                        .findByNombre(ingredienteXReceta.getIngrediente().getNombre()).orElse(null);

                if (resultIngrediente == null) {
                    // Si el ingrediente no existe, crear uno nuevo
                    resultIngrediente = repoIngrediente.save(new Ingrediente(ingredienteXReceta.getIngrediente().getNombre()));
                }

                // Asignar el ingrediente y la receta al IngredienteXReceta y guardarlo
                ingredienteXReceta.setIngrediente(resultIngrediente);
                ingredienteXReceta.setReceta(savedReceta);
                repoIngredienteXReceta.save(ingredienteXReceta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Ocurrió un error al crear la receta
        }
        return 1; // Receta creada correctamente
    }

    public Receta updateReceta(Receta receta) {
        return repoReceta.save(receta);
    }

    public List<Receta> getRecetasByUserId(Long userId) {
        return repoReceta.findByUserId(userId);
    }

    public Receta getRecetaByNombre(String nombre) {
        Optional<Receta> receta = repoReceta.findByNombre(nombre);
        return receta.orElse(null);
    }

    public Receta toggleFavorita(Long recetaId) {
        Optional<Receta> optionalReceta = repoReceta.findById(recetaId);
        if (optionalReceta.isPresent()) {
            Receta receta = optionalReceta.get();
            receta.setFavorita(!receta.isFavorita());
            return repoReceta.save(receta);
        }
        return null; // Si no se encontró la receta
    }

    // Método para actualizar una receta existente
    public Receta updateRecetaById(Long id, Receta recetaActualizada) {
        Optional<Receta> optionalReceta = repoReceta.findById(id);
        if (optionalReceta.isPresent()) {
            Receta receta = optionalReceta.get();
            receta.setNombre(recetaActualizada.getNombre());
            receta.setPasos(recetaActualizada.getPasos());
            receta.setImagenUrl(recetaActualizada.getImagenUrl());
            receta.setFavorita(recetaActualizada.isFavorita());
            receta.setTiempo(recetaActualizada.getTiempo());
            // Otros atributos que deseas actualizar
            return repoReceta.save(receta);
        }
        return null; // Si no se encontró la receta
    }
}
