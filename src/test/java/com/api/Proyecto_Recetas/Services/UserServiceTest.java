package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Inicializamos el mock del repositorio y el servicio
        userRepository = mock(UserRepository.class);
        userService = new UserService();
        userService.userRepository = userRepository;  // Injectamos el mock del repositorio
    }

    @Test
    void getUsers() {
        // Configuramos el mock para devolver un ArrayList de usuarios
        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);  // Devolvemos un ArrayList

        // Llamamos al método a probar
        ArrayList<User> result = userService.getUsers();  // Esto ahora coincidirá con el tipo esperado

        // Verificamos los resultados
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("jane_doe");
        user.setPassword("password123");

        // Simulamos el guardado del usuario
        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setUsername("jane_doe");
        savedUser.setPassword("password123");

        when(userRepository.save(user)).thenReturn(savedUser);

        // Llamamos al método a probar
        User result = userService.saveUser(user);

        // Verificamos que el usuario se haya guardado correctamente
        assertNotNull(result.getId());
        assertEquals("jane_doe", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("john_doe");
        user.setPassword("password123");

        // Simulamos que el usuario existe en la base de datos
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Llamamos al método a probar
        Optional<User> result = userService.getUserById(userId);

        // Verificamos los resultados
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository, times(1)).findById(userId);
    }
    @Test
    void updateByID() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("john_doe");
        existingUser.setPassword("password123");

        User updatedUser = new User();
        updatedUser.setUsername("john_updated");
        updatedUser.setPassword("new_password");

        // Simulamos que el usuario existe
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Simulamos que no se guarda el usuario porque no se llama a save() en el UserService
        // (Aquí el mock no necesita hacer nada para save() porque nunca se llama en tu UserService).

        // Llamamos al método a probar
        User result = userService.updateByID(updatedUser, userId);

        // Verificamos que el usuario fue actualizado en memoria, pero no se llama a save()
        assertEquals("john_updated", result.getUsername());
        assertEquals("new_password", result.getPassword());

        // Verificamos que findById fue llamado
        verify(userRepository, times(1)).findById(userId);

        // No verificamos save() porque nunca se llama en el servicio
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        // Simulamos la eliminación del usuario
        doNothing().when(userRepository).deleteById(userId);

        // Llamamos al método a probar
        boolean result = userService.deleteUser(userId);

        // Verificamos que se haya eliminado correctamente
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUserByUsername() {
        String username = "john_doe";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password123");

        // Simulamos la búsqueda del usuario por nombre de usuario
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Llamamos al método a probar
        Optional<User> result = userService.getUserByUsername(username);

        // Verificamos que se haya encontrado el usuario
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }
}