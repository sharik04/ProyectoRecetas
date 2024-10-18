package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
        // Crear un usuario único solo si no existe
        Optional<User> existingUser = userRepository.findByUsername("sofia");
        if (existingUser.isEmpty()) {
            user = new User();
            user.setUsername("sofia");
            user.setPassword("password123");
    
            Receta receta1 = new Receta();
            receta1.setNombre("Arroz con huevo");
            receta1.setPasos("1. Cocinar el arroz. 2. Freír el huevo.");
            receta1.setUser(user);
    
            Receta receta2 = new Receta();
            receta2.setNombre("Pollo con arroz");
            receta2.setPasos("1. Cocinar el arroz. 2. Cocinar el pollo.");
            receta2.setUser(user);
    
            user.setRecetas(Arrays.asList(receta1, receta2));
            user = userRepository.save(user);
        } else {
            user = existingUser.get();
        }
    }
    


    @Test
    void testSaveAndRetrieveUserWithRecetas() {
        // Recuperar el usuario por su nombre de usuario
        Optional<User> foundUser = userRepository.findByUsername("sofia");
        assertTrue(foundUser.isPresent());

        User retrievedUser = foundUser.get();

        // Verificar que el usuario tenga dos recetas asociadas
        assertEquals(2, retrievedUser.getRecetas().size());

        // Verificar los nombres de las recetas
        assertEquals("Arroz con huevo", retrievedUser.getRecetas().get(0).getNombre());
        assertEquals("Pollo con arroz", retrievedUser.getRecetas().get(1).getNombre());
    }

    
    @Test
    @Transactional
    void testGetRecetasFromUserController() throws Exception {
        // Simular una solicitud HTTP GET para obtener las recetas del usuario
        mockMvc.perform(get("/user/" + user.getId() + "/recetas"))
                .andExpect(status().isOk());

    }
}