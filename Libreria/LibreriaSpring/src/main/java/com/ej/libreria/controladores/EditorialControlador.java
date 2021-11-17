
package com.ej.libreria.controladores;

import com.ej.libreria.servicios.EditorialService;
import com.ej.libreria.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialService es;
    
    @GetMapping("/menu")
    public String libro(){
        return "editoriales.html";
    }
    
      
     @GetMapping("/menu/add-editorial")
    public String agregarLibro(){
        return "agregar-editorial";
    }
    
    @GetMapping("/menu/lista")
    public String verLibros(){
        return "ver-editoriales";
    }
    
    @PostMapping("/menu/add-editorial/registro")
    public String agregarEditorial(ModelMap modelo,@RequestParam String nombre){        
        try {
            es.crearEditorial(nombre);
            modelo.put("exito", "exito");
            return "agregar-editorial";
        } catch (Exception e) {
            modelo.put("error", "error");
            return "agregar-editorial";
        }
    }
}