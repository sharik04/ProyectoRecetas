package com.api.Proyecto_Recetas.Services;

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
public class RecetaService {

    @Autowired
    private RecetaRepository repoReceta;
    @Autowired
    private IngredienteRepository repoIngrediente;
    @Autowired
    private IngredienteXRecetaRepository repoIngredienteXReceta;

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
            // Usuario usser = receta.getUser();
            // repo
            // receta.setUser(usser);
            repoReceta.save(receta);
            for (IngredienteXReceta ingredienteXReceta : ingredientesXReceta) {
                Ingrediente resultIngrediente = repoIngrediente
                        .findByNombre(ingredienteXReceta.getIngrediente().getNombre()).orElse(null);
                if (resultIngrediente != null) {
                    repoIngrediente.save(new Ingrediente(ingredienteXReceta.getIngrediente().getNombre()));
                }
                ingredienteXReceta.setIngrediente(resultIngrediente);
                ingredienteXReceta.setReceta(receta);
                repoIngredienteXReceta.save(ingredienteXReceta);
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
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

}
