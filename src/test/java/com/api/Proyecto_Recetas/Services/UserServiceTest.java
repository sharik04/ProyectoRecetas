package com.api.Proyecto_Recetas.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Repositories.UsuarioRepository;


class UserServiceTest {

    private UsuarioService userService;
    private UsuarioRepository userRepository;

    @BeforeEach
    void setUp() {
        // Inicializamos el mock del repositorio y el servicio
        userRepository = mock(UsuarioRepository.class);
        userService = new UsuarioService();
        userService.userRepo = userRepository;  // Injectamos el mock del repositorio
    }

    @Test
    void getUsers() {
        // Configuramos el mock para devolver un ArrayList de usuarios
        ArrayList<Usuario> users = new ArrayList<>();
        Usuario user = new Usuario();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);  // Devolvemos un ArrayList

        // Llamamos al método a probar
        ArrayList<Usuario> result = userService.getUsers();  // Esto ahora coincidirá con el tipo esperado

        // Verificamos los resultados
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser() {
        Usuario user = new Usuario();
        user.setUsername("jane_doe");
        user.setPassword("password123");

        // Simulamos el guardado del usuario
        Usuario savedUser = new Usuario();
        savedUser.setId(2L);
        savedUser.setUsername("jane_doe");
        savedUser.setPassword("password123");

        when(userRepository.save(user)).thenReturn(savedUser);

        // Llamamos al método a probar
        Usuario result = userService.saveUser(user);

        // Verificamos que el usuario se haya guardado correctamente
        assertNotNull(result.getId());
        assertEquals("jane_doe", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        Usuario user = new Usuario();
        user.setId(userId);
        user.setUsername("john_doe");
        user.setPassword("password123");

        // Simulamos que el usuario existe en la base de datos
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Llamamos al método a probar
        Optional<Usuario> result = userService.getUserById(userId);

        // Verificamos los resultados
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository, times(1)).findById(userId);
    }
    @Test
    void updateByID() {
        Long userId = 1L;
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);
        existingUser.setUsername("john_doe");
        existingUser.setPassword("password123");

        Usuario updatedUser = new Usuario();
        updatedUser.setUsername("john_updated");
        updatedUser.setPassword("new_password");

        // Simulamos que el usuario existe
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Simulamos que no se guarda el usuario porque no se llama a save() en el UserService
        // (Aquí el mock no necesita hacer nada para save() porque nunca se llama en tu UserService).

        // Llamamos al método a probar
        Usuario result = userService.updateByID(updatedUser, userId);

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
        Usuario user = new Usuario();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password123");

        // Simulamos la búsqueda del usuario por nombre de usuario
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Llamamos al método a probar
        Optional<Usuario> result = userService.getUserByUsername(username);

        // Verificamos que se haya encontrado el usuario
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }
}