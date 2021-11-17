
package com.ej.libreria.controladores;

import com.ej.libreria.servicios.AutorService;
import com.ej.libreria.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorService as;
    
    @GetMapping("/menu")
    public String libro(){
        return "autores.html";
    }
    
     @GetMapping("/menu/add-autor")
    public String agregarLibro(){
        return "agregar-autor";
    }
    
    @GetMapping("/menu/lista")
    public String verLibros(){
        return "ver-autores";
    }
    
    @PostMapping("/menu/add-autor/registro")
    public String agregarAutor(ModelMap modelo,@RequestParam String nombre){
        try {
            as.agregarAutor(nombre);
            modelo.put("exito", "exito");
            return "agregar-autor";
        } catch (Exception e) {
            modelo.put("error", "error");
            return "agregar-autor";
        }
    }
}