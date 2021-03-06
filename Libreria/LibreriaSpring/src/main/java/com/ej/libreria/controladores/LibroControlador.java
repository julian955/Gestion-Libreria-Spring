package com.ej.libreria.controladores;

import com.ej.libreria.entidades.Autor;
import com.ej.libreria.entidades.Editorial;
import com.ej.libreria.entidades.Libro;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.servicios.AutorService;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroService ls;
    @Autowired
    private EditorialService es;
    @Autowired
    private AutorService as;

    @GetMapping("/menu")
    public String libro() {
        return "libros.html";
    }

    @GetMapping("/menu/add-book")
    public String agregarLibro(ModelMap modelo) {
        List<Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        List<Autor> autores = as.listarAutores();
        modelo.addAttribute("autores", autores);
        return "agregar-libro";
    }

    @GetMapping("/menu/lista-libro")
    public String verLibros(ModelMap modelo) {
        List<Libro> libros = ls.listarLibros();
        modelo.addAttribute("libros", libros);
        return "ver-libros";
    }

    @PostMapping("/menu/add-book/registro")
    public String guardar(ModelMap modelo, Long isbn, @RequestParam String titulo, Integer ano, Integer ejemplares, @RequestParam String autor, @RequestParam String editorial, MultipartFile foto) throws Exception {
        try {
            ls.crearLibro(foto, isbn, titulo, ano, ejemplares, autor, editorial);
            modelo.put("exito", "Registro Exitoso");
            return "agregar-libro";
        } catch (Exception e) {
            modelo.put("error", "Fallo el registro");
            return "agregar-libro";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        List<Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        List<Autor> autores = as.listarAutores();
        modelo.addAttribute("autores", autores);

        modelo.put("libro", ls.traerLibro(id));
        return "modificar-libro";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, MultipartFile archivo, @PathVariable Long id, @RequestParam String titulo, Integer ano, Integer ejemplares, @RequestParam String nombreAutor, @RequestParam String nombreEditorial) throws ErrorServicio, IOException {
        try {
            ls.editarLibro(archivo, id, titulo, ano, ejemplares, nombreAutor, nombreEditorial);
            return "redirect:/libro/menu/lista-libro";
        } catch (Exception e) {
            List<Editorial> editoriales = es.listarEditoriales();
            modelo.addAttribute("editoriales", editoriales);
            List<Autor> autores = as.listarAutores();
            modelo.addAttribute("autores", autores);
            modelo.put("libro", ls.traerLibro(id));
            modelo.put("error", "Fallo el registro");
            return "modificar-libro";
        }

    }

    @GetMapping("/baja/{isbn}")
    public String baja(@PathVariable Long isbn) {

        try {
            ls.Baja(isbn);
            return "redirect:/libro/menu/lista-libro";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{isbn}")
    public String alta(@PathVariable Long isbn) {

        try {
            ls.Alta(isbn);
            return "redirect:/libro/menu/lista-libro";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
