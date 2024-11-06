package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class IngredienteXRecetaServiceTest {

    @Mock
    private IngredienteXRecetaRepository ingredienteXRecetaRepository;

    @InjectMocks
    private IngredienteXRecetaService ingredienteXRecetaService;

    private IngredienteXReceta ingredienteXReceta;
    private Ingrediente ingrediente;
    private Receta receta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar los objetos de prueba
        ingrediente = new Ingrediente();
        ingrediente.setNombre("Tomate");

        receta = new Receta();
        receta.setNombre("Receta de prueba");

        ingredienteXReceta = new IngredienteXReceta();
        ingredienteXReceta.setIngrediente(ingrediente);
        ingredienteXReceta.setReceta(receta);
        ingredienteXReceta.setCantidad(3);
    }

    @Test
    public void testSaveIngredienteXReceta() {
        // Configuración del mock para guardar el IngredienteXReceta
        when(ingredienteXRecetaRepository.save(any(IngredienteXReceta.class))).thenReturn(ingredienteXReceta);

        // Ejecutar el método
        IngredienteXReceta savedIngredienteXReceta = ingredienteXRecetaService.saveIngredienteXReceta(ingredienteXReceta);

        // Verificar las interacciones
        verify(ingredienteXRecetaRepository).save(any(IngredienteXReceta.class));

        // Verificar que el objeto fue guardado correctamente
        assertNotNull(savedIngredienteXReceta);
        assertEquals(ingredienteXReceta.getIngrediente().getNombre(), savedIngredienteXReceta.getIngrediente().getNombre());
        assertEquals(ingredienteXReceta.getReceta().getNombre(), savedIngredienteXReceta.getReceta().getNombre());
        assertEquals(ingredienteXReceta.getCantidad(), savedIngredienteXReceta.getCantidad());
    }


    @Test
    public void testGetIngredienteXRecetaByIdNotFound() {
        // Configuración del mock para no encontrar un ingredienteXReceta por ID
        when(ingredienteXRecetaRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método
        IngredienteXReceta foundIngredienteXReceta = ingredienteXRecetaService.getIngredienteXRecetaById(1L);

        // Verificar las interacciones
        verify(ingredienteXRecetaRepository).findById(1L);

        // Verificar que no se encontró el objeto
        assertNull(foundIngredienteXReceta);
    }

    @Test
    public void testDeleteIngredienteXReceta() {
        // Ejecutar el método
        ingredienteXRecetaService.deleteIngredienteXReceta(1L);

        // Verificar que el método deleteById fue llamado correctamente
        verify(ingredienteXRecetaRepository).deleteById(1L);
    }


    @Test
    public void testUpdateIngredienteXRecetaNotFound() {
        // Configuración del mock para no encontrar el ingredienteXReceta por ID
        when(ingredienteXRecetaRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método
        IngredienteXReceta result = ingredienteXRecetaService.updateIngredienteXReceta(1L, ingredienteXReceta);

        // Verificar las interacciones
        verify(ingredienteXRecetaRepository).findById(1L);

        // Verificar que no se encontró el objeto
        assertNull(result);
    }
}
