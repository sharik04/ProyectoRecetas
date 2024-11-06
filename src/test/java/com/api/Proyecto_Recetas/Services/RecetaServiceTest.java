package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private IngredienteXRecetaRepository ingredienteXRecetaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecetaService recetaService;

    private User user;
    private Receta receta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");

        receta = new Receta();
        receta.setNombre("Receta de prueba");
        receta.setPasos("Pasos de la receta");
        receta.setImagen("imagen.jpg");
        receta.setFavorita(false);
    }

    @Test
    public void testSaveReceta() {
        // Datos de prueba
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Tomate");

        IngredienteXReceta ingredienteXReceta = new IngredienteXReceta();
        ingredienteXReceta.setIngrediente(ingrediente);

        receta.setIngredienteXRecetas(List.of(ingredienteXReceta));

        // Mock de las interacciones
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ingredienteRepository.findByNombre("Tomate")).thenReturn(Optional.empty());  // Ingrediente no existe en la base de datos
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        // Ejecutar el método
        Receta savedReceta = recetaService.saveReceta(receta, 1L);

        // Verificar que se haya llamado correctamente a los métodos
        verify(userRepository).findById(1L);
        verify(ingredienteRepository).findByNombre("Tomate");
        verify(recetaRepository).save(any(Receta.class));

        // Verificar que la receta fue guardada correctamente
        assertNotNull(savedReceta);
        assertEquals("Receta de prueba", savedReceta.getNombre());
        assertEquals("Tomate", savedReceta.getIngredienteXRecetas().get(0).getIngrediente().getNombre());
    }

    @Test
    public void testSaveRecetaWithExistingIngredient() {
        // Ingrediente ya existe en la base de datos
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Tomate");

        IngredienteXReceta ingredienteXReceta = new IngredienteXReceta();
        ingredienteXReceta.setIngrediente(ingrediente);
        receta.setIngredienteXRecetas(List.of(ingredienteXReceta));

        // Mock de las interacciones
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ingredienteRepository.findByNombre("Tomate")).thenReturn(Optional.of(ingrediente));  // Ingrediente ya existe
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        // Ejecutar el método
        Receta savedReceta = recetaService.saveReceta(receta, 1L);

        // Verificar que se haya llamado correctamente a los métodos
        verify(userRepository).findById(1L);
        verify(ingredienteRepository).findByNombre("Tomate");
        verify(recetaRepository).save(any(Receta.class));

        // Verificar que la receta fue guardada correctamente
        assertNotNull(savedReceta);
        assertEquals("Receta de prueba", savedReceta.getNombre());
        assertEquals("Tomate", savedReceta.getIngredienteXRecetas().get(0).getIngrediente().getNombre());
    }
}
