package com.ej.libreria.controladores;

import com.ej.libreria.entidades.Editorial;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.servicios.EditorialService;
import com.ej.libreria.servicios.LibroService;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialService es;

    @GetMapping("/menu")
    public String libro() {
        return "editoriales.html";
    }

    @GetMapping("/menu/add-editorial")
    public String agregarLibro() {
        return "agregar-editorial";
    }

    @GetMapping("/menu/lista")
    public String verLibros(ModelMap modelo) {
        List<Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "ver-editoriales";
    }

    @PostMapping("/menu/add-editorial/registro")
    public String agregarEditorial(ModelMap modelo, @RequestParam String nombre) {
        try {
            es.crearEditorial(nombre);
            modelo.put("exito", "exito");
            return "agregar-editorial";
        } catch (Exception e) {
            modelo.put("error", "error");
            return "agregar-editorial";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        modelo.put("editorial", es.traerEditorial(id));
        return "modificar-editorial";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable Long id,@RequestParam String titulo) throws ErrorServicio, IOException {
        try {
            es.editarEdt(id, titulo);            
            return "redirect:/editorial/menu/lista";
        } catch (Exception e) {
            modelo.put("editorial", es.traerEditorial(id));
            modelo.put("error", "Fallo el registro");
            return "modificar-editorial";
        }

    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable Long id) {

        try {
            es.darBaja(id);
            return "redirect:/editorial/menu/lista";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable Long id) {

        try {
            es.darAlta(id);
            return "redirect:/editorial/menu/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
