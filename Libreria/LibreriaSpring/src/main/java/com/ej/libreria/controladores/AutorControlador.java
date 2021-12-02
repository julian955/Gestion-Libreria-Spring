package com.ej.libreria.controladores;

import com.ej.libreria.entidades.Autor;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.servicios.AutorService;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorService as;

    @GetMapping("/menu")
    public String libro() {
        return "autores.html";
    }

    @GetMapping("/menu/add-autor")
    public String agregarLibro() {
        return "agregar-autor";
    }

    @GetMapping("/menu/lista")
    public String verLibros(ModelMap modelo) {
        List<Autor> autores = as.listarAutores();
        modelo.addAttribute("autores", autores);
        return "ver-autores";
    }

    @PostMapping("/menu/add-autor/registro")
    public String agregarAutor(ModelMap modelo, @RequestParam String nombre) {
        try {
            as.agregarAutor(nombre);
            modelo.put("exito", "exito");
            return "agregar-autor";
        } catch (Exception e) {
            modelo.put("error", "error");
            return "agregar-autor";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("autor", as.traerAutor(id));
        return "modificar-autor";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable Integer id, @RequestParam String titulo) throws ErrorServicio, IOException {
        try {
            as.editarAut(id, titulo);
            return "redirect:/autor/menu/lista";
        } catch (Exception e) {
            modelo.put("autor", as.traerAutor(id));
            modelo.put("error", "Fallo el registro");
            return "modificar-autor";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable Integer id) {

        try {
            as.darBaja(id);
            return "redirect:/autor/menu/lista";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable Integer id) {

        try {
            as.darAlta(id);
            return "redirect:/autor/menu/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
