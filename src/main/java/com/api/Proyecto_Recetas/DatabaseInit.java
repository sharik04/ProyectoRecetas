package com.api.Proyecto_Recetas;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;
import com.api.Proyecto_Recetas.Repositories.IngredienteXRecetaRepository;
import com.api.Proyecto_Recetas.Repositories.RecetaRepository;
import com.api.Proyecto_Recetas.Repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    private RecetaRepository repoReceta;

    @Autowired
    private IngredienteRepository repoIngrediente;

    @Autowired
    private IngredienteXRecetaRepository repoIngredienteXReceta;

    @Autowired
    private UsuarioRepository repoUsuario;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Usuario user1 = new Usuario("Juan Pérez", "juanp", "password123", "juanp@gmail.com");
        Usuario user2 = new Usuario("María López", "marial", "password456", "marial@gmail.com");
        Usuario user3 = new Usuario("Carlos Gómez", "carlosg", "password789", "carlosg@gmail.com");

        repoUsuario.saveAll(Arrays.asList(user1, user2, user3));

        Ingrediente tomate = new Ingrediente("Tomate");
        Ingrediente quesoMozzarella = new Ingrediente("Queso Mozzarella");
        Ingrediente masaPizza = new Ingrediente("Masa de Pizza");
        Ingrediente aceiteOliva = new Ingrediente("Aceite de oliva");
        Ingrediente oregano = new Ingrediente("Orégano");
        Ingrediente carne = new Ingrediente("Carne de hamburguesa");
        Ingrediente panHamburguesa = new Ingrediente("Pan de hamburguesa");
        Ingrediente lechuga = new Ingrediente("Lechuga");
        Ingrediente tomateRodajas = new Ingrediente("Tomate en rodajas");
        Ingrediente quesoCheddar = new Ingrediente("Queso Cheddar");
        Ingrediente cebolla = new Ingrediente("Cebolla");
        Ingrediente ketchup = new Ingrediente("Ketchup");
        Ingrediente mostaza = new Ingrediente("Mostaza");
        Ingrediente panBlanco = new Ingrediente("Pan blanco");
        Ingrediente jamon = new Ingrediente("Jamón");
        Ingrediente queso = new Ingrediente("Queso");
        Ingrediente mantequilla = new Ingrediente("Mantequilla");

        // Guardar ingredientes en la base de datos
        repoIngrediente.saveAll(Arrays.asList(
                tomate, quesoMozzarella, masaPizza, aceiteOliva, oregano,
                carne, panHamburguesa, lechuga, tomateRodajas, quesoCheddar, cebolla,
                ketchup, mostaza, panBlanco, jamon, queso, mantequilla));
        // Crear recetas y sus ingredientes

        // Receta 1: Pizza (para usuario 1)
        Receta pizza = new Receta(
                "Pizza",
                "1. Precalentar el horno...\n2. Extender la masa...",
                "https://www.foodandwine.com/thmb/Wd4lBRZz3X_8qBr69UOu2m7I2iw=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/classic-cheese-pizza-FT-RECIPE0422-31a2c938fc2546c9a07b7011658cfd05.jpg",
                true,
                "30 minutos",
                user1);

        List<IngredienteXReceta> ingredientesPizza = Arrays.asList(
                new IngredienteXReceta(tomate, pizza, 200, "gramos"),
                new IngredienteXReceta(quesoMozzarella, pizza, 150, "gramos"),
                new IngredienteXReceta(masaPizza, pizza, 1, "unidad"),
                new IngredienteXReceta(aceiteOliva, pizza, 2, "cucharadas"),
                new IngredienteXReceta(oregano, pizza, 1, "cucharadita"));

        repoReceta.save(pizza);
        repoIngredienteXReceta.saveAll(ingredientesPizza);

        // Receta 2: Hamburguesa (para usuario 2)
        Receta hamburguesa = new Receta(
    "Hamburguesa", 
    "1. Cocinar la carne...\n2. Armar la hamburguesa...",
    "https://www.recetasnestle.com.ec/sites/default/files/srh_recipes/4e4293857c03d819e4ae51de1e86d66a.jpg", 
    false, 
    "20 minutos", 
    user1
);

        List<IngredienteXReceta> ingredientesHamburguesa = Arrays.asList(
                new IngredienteXReceta(carne, hamburguesa, 1, "unidad"),
                new IngredienteXReceta(panHamburguesa, hamburguesa, 2, "rebanadas"),
                new IngredienteXReceta(lechuga, hamburguesa, 1, "hoja"),
                new IngredienteXReceta(tomateRodajas, hamburguesa, 2, "rodajas"),
                new IngredienteXReceta(quesoCheddar, hamburguesa, 1, "rebanada"),
                new IngredienteXReceta(cebolla, hamburguesa, 2, "rodajas"),
                new IngredienteXReceta(ketchup, hamburguesa, 1, "cucharada"),
                new IngredienteXReceta(mostaza, hamburguesa, 1, "cucharadita"));

        repoReceta.save(hamburguesa);
        repoIngredienteXReceta.saveAll(ingredientesHamburguesa);

        // Receta 3: Sándwich de jamón y queso (para usuario 3)
        Receta sandwich = new Receta(
    "Sándwich de Jamón y Queso", 
    "1. Untar la mantequilla...\n2. Colocar el jamón y el queso...",
    "https://t1.uc.ltmcdn.com/es/posts/0/6/9/como_hacer_un_sandwich_mixto_33960_600.jpg", 
    true, 
    "10 minutos", 
    user1
);

        List<IngredienteXReceta> ingredientesSandwich = Arrays.asList(
                new IngredienteXReceta(panBlanco, sandwich, 2, "rebanadas"),
                new IngredienteXReceta(jamon, sandwich, 2, "rebanadas"),
                new IngredienteXReceta(queso, sandwich, 1, "rebanada"),
                new IngredienteXReceta(mantequilla, sandwich, 1, "cucharada"));

        repoReceta.save(sandwich);
        repoIngredienteXReceta.saveAll(ingredientesSandwich);
    }

}
