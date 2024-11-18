package com.api.Proyecto_Recetas.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private RecetaService recetaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void getAllRecetas() {
        // Datos simulados
        Receta receta1 = new Receta();
        receta1.setNombre("Arroz con huevo");
        Receta receta2 = new Receta();
        receta2.setNombre("Arroz con carne");

        when(recetaRepository.findAll()).thenReturn(Arrays.asList(receta1, receta2));

        // Prueba
        List<Receta> recetas = recetaService.getAllRecetas();
        assertNotNull(recetas);
        assertEquals(2, recetas.size());
        assertEquals("Arroz con huevo", recetas.get(0).getNombre());
    }

    @Test
void saveReceta() {
    // Datos simulados
    Usuario user = new Usuario("Nombre Completo", "username", "password", "email@example.com");
    Receta receta = new Receta("Arroz con huevo", "Pasos de preparación", "url_imagen", false, "30 minutos", user);

    Ingrediente ingrediente1 = new Ingrediente("Arroz");
    Ingrediente ingrediente2 = new Ingrediente("Huevo");

    IngredienteXReceta ixr1 = new IngredienteXReceta(ingrediente1, receta, 200, "gramos");
    IngredienteXReceta ixr2 = new IngredienteXReceta(ingrediente2, receta, 2, "unidades");

    List<IngredienteXReceta> ingredientes = Arrays.asList(ixr1, ixr2);
    receta.setIngredientes(ingredientes);

    when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

    // Prueba
    Receta savedReceta = recetaService.saveReceta(receta);

    // Verificaciones
    assertNotNull(savedReceta);
    assertEquals("Arroz con huevo", savedReceta.getNombre());
    assertEquals(2, savedReceta.getIngredientes().size());
    assertEquals("Arroz", savedReceta.getIngredientes().get(0).getIngrediente().getNombre());
    assertEquals("Huevo", savedReceta.getIngredientes().get(1).getIngrediente().getNombre());
    assertEquals(user, savedReceta.getUser());

    // Verificación de la llamada al repositorio
    verify(recetaRepository, times(1)).save(any(Receta.class));
}

    @Test
    void getRecetaById() {
        // Datos simulados
        Receta receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Arroz con huevo");

        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        // Prueba
        Receta foundReceta = recetaService.getRecetaById(1L);
        assertNotNull(foundReceta);
        assertEquals(1L, foundReceta.getId());
        assertEquals("Arroz con huevo", foundReceta.getNombre());
    }

    @Test
    void deleteReceta() {
        // No retorna valor, simplemente verificamos la interacción
        doNothing().when(recetaRepository).deleteById(1L);

        // Prueba
        recetaService.deleteReceta(1L);

        // Verificación
        verify(recetaRepository, times(1)).deleteById(1L);
    }

    @Test
void updateReceta() {
    // Datos simulados
    Usuario user = new Usuario("Nombre Completo", "username", "password", "email@example.com");
    
    Receta existingReceta = new Receta("Arroz con huevo", "Pasos originales", "url_imagen_original", false, "30 minutos", user);
    existingReceta.setId(1L);

    Ingrediente ingrediente1 = new Ingrediente("Arroz");
    Ingrediente ingrediente2 = new Ingrediente("Huevo");
    IngredienteXReceta ixr1 = new IngredienteXReceta(ingrediente1, existingReceta, 200, "gramos");
    IngredienteXReceta ixr2 = new IngredienteXReceta(ingrediente2, existingReceta, 2, "unidades");
    existingReceta.setIngredientes(Arrays.asList(ixr1, ixr2));

    Receta updatedReceta = new Receta("Arroz con pollo", "Nuevos pasos", "nueva_url_imagen", true, "45 minutos", user);
    
    Ingrediente ingrediente3 = new Ingrediente("Pollo");
    IngredienteXReceta ixr3 = new IngredienteXReceta(ingrediente1, updatedReceta, 200, "gramos");
    IngredienteXReceta ixr4 = new IngredienteXReceta(ingrediente3, updatedReceta, 300, "gramos");
    updatedReceta.setIngredientes(Arrays.asList(ixr3, ixr4));

    when(recetaRepository.findById(1L)).thenReturn(Optional.of(existingReceta));
    when(recetaRepository.save(any(Receta.class))).thenReturn(existingReceta);

    // Prueba
    Receta result = recetaService.updateReceta(updatedReceta);

    // Verificaciones
    assertNotNull(result);
    assertEquals("Arroz con huevo", result.getNombre());
    assertEquals("Pasos originales", result.getPasos());
    assertEquals("url_imagen_original", result.getImagenUrl());
    assertEquals(false, result.isFavorita());
    assertEquals("30 minutos", result.getTiempo());
    assertEquals(2, result.getIngredientes().size());
    assertEquals("Arroz", result.getIngredientes().get(0).getIngrediente().getNombre());
    assertEquals("Huevo", result.getIngredientes().get(1).getIngrediente().getNombre());
    assertEquals(user, result.getUser());

}
}