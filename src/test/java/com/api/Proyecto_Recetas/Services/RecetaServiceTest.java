package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Receta receta = new Receta();
        receta.setNombre("Arroz con huevo");

        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setNombre("Arroz");
        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setNombre("Huevo");
        receta.setIngredientes(Arrays.asList(ingrediente1, ingrediente2));

        when(recetaRepository.save(receta)).thenReturn(receta);

        // Prueba
        Receta savedReceta = recetaService.saveReceta(receta);
        assertNotNull(savedReceta);
        assertEquals("Arroz con huevo", savedReceta.getNombre());
        assertEquals(2, savedReceta.getIngredientes().size());

        // Verificación
        verify(recetaRepository, times(1)).save(receta);
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
        Receta existingReceta = new Receta();
        existingReceta.setId(1L);
        existingReceta.setNombre("Arroz con huevo");

        Receta updatedReceta = new Receta();
        updatedReceta.setNombre("Arroz con pollo");

        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setNombre("Arroz");
        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setNombre("Pollo");
        updatedReceta.setIngredientes(Arrays.asList(ingrediente1, ingrediente2));

        when(recetaRepository.findById(1L)).thenReturn(Optional.of(existingReceta));
        when(recetaRepository.save(existingReceta)).thenReturn(existingReceta);

        // Prueba
        Receta result = recetaService.updateReceta(1L, updatedReceta);

        assertNotNull(result);
        assertEquals("Arroz con pollo", result.getNombre());
        assertEquals(2, result.getIngredientes().size());

        // Verificación
        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(existingReceta);
    }

    @SpringBootTest
    @AutoConfigureMockMvc
    static
    class UserRecetaIntegrationTest {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RecetaRepository recetaRepository;

        @Autowired
        private MockMvc mockMvc;

        private User user;

        @BeforeEach
        void setUp() {
            // Limpiar la base de datos antes de cada prueba
            userRepository.deleteAll();

            // Crear un usuario único
            user = new User();
            user.setUsername("sofia");
            user.setPassword("password123");

            // Crear recetas y asociarlas al usuario
            Receta receta1 = new Receta();
            receta1.setNombre("Arroz con huevo");
            receta1.setPasos("1. Cocinar el arroz. 2. Freír el huevo.");
            receta1.setUser(user);

            Receta receta2 = new Receta();
            receta2.setNombre("Pollo con arroz");
            receta2.setPasos("1. Cocinar el arroz. 2. Cocinar el pollo.");
            receta2.setUser(user);

            user.setRecetas(Arrays.asList(receta1, receta2));

            // Guardar el usuario y las recetas
            user = userRepository.save(user);
        }

        @Test
        void testSaveAndRetrieveUserWithRecetas() {
            // Recuperar el usuario por su nombre de usuario
            Optional<User> foundUser = userRepository.findByUsername("sofia");
            assertTrue(foundUser.isPresent());

            User retrievedUser = foundUser.get();

            // Inicializar manualmente la colección de recetas antes de acceder a ella
            retrievedUser.getRecetas().size(); // Esto carga las recetas si son Lazy

            // Verificar que el usuario tenga dos recetas asociadas
            assertEquals(2, retrievedUser.getRecetas().size());

            // Verificar los nombres de las recetas
            assertEquals("Arroz con huevo", retrievedUser.getRecetas().get(0).getNombre());
            assertEquals("Pollo con arroz", retrievedUser.getRecetas().get(1).getNombre());
        }

        @Test
        void testGetRecetasFromUserController() throws Exception {
            // Simular una solicitud HTTP GET para obtener las recetas del usuario
            mockMvc.perform(get("/recetas/user/" + user.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nombre").value("Arroz con huevo"))
                    .andExpect(jsonPath("$[1].nombre").value("Pollo con arroz"));
        }
    }
}