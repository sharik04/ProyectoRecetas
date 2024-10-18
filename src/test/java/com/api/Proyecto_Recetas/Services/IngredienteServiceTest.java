package com.api.Proyecto_Recetas.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;

class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteService ingredienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void getAllIngredientes() {
        // Datos simulados
        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setNombre("Arroz");
        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setNombre("Huevo");

        when(ingredienteRepository.findAll()).thenReturn(Arrays.asList(ingrediente1, ingrediente2));

        // Prueba
        List<Ingrediente> ingredientes = ingredienteService.getAllIngredientes();
        assertNotNull(ingredientes);
        assertEquals(2, ingredientes.size());
        assertEquals("Arroz", ingredientes.get(0).getNombre());
    }

    @Test
    void saveIngrediente() {
        // Datos simulados
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Arroz");

        when(ingredienteRepository.save(ingrediente)).thenReturn(ingrediente);

        // Prueba
        Ingrediente savedIngrediente = ingredienteService.saveIngrediente(ingrediente);
        assertNotNull(savedIngrediente);
        assertEquals("Arroz", savedIngrediente.getNombre());

        // Verificación
        verify(ingredienteRepository, times(1)).save(ingrediente);
    }

    @Test
    void getIngredienteById() {
        // Datos simulados
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNombre("Arroz");

        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente));

        // Prueba
        Ingrediente foundIngrediente = ingredienteService.getIngredienteById(1L);
        assertNotNull(foundIngrediente);
        assertEquals(1L, foundIngrediente.getId());
        assertEquals("Arroz", foundIngrediente.getNombre());
    }

    @Test
    void deleteIngrediente() {
        // No retorna valor, simplemente verificamos la interacción
        doNothing().when(ingredienteRepository).deleteById(1L);

        // Prueba
        ingredienteService.deleteIngrediente(1L);

        // Verificación
        verify(ingredienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateIngrediente() {
        // Datos simulados
        Ingrediente existingIngrediente = new Ingrediente();
        existingIngrediente.setId(1L);
        existingIngrediente.setNombre("Arroz");

        Ingrediente updatedIngrediente = new Ingrediente();
        updatedIngrediente.setNombre("Arroz Integral");

        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(existingIngrediente));
        when(ingredienteRepository.save(existingIngrediente)).thenReturn(existingIngrediente);

        // Prueba
        Ingrediente result = ingredienteService.updateIngrediente(1L, updatedIngrediente);

        assertNotNull(result);
        assertEquals("Arroz Integral", result.getNombre());

        // Verificación
        verify(ingredienteRepository, times(1)).findById(1L);
        verify(ingredienteRepository, times(1)).save(existingIngrediente);
    }
}