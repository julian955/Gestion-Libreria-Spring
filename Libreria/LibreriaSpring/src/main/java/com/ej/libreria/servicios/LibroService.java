package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Autor;
import com.ej.libreria.entidades.Editorial;
import com.ej.libreria.entidades.Foto;
import com.ej.libreria.entidades.Libro;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.repositorios.AutorRepositorio;
import com.ej.libreria.repositorios.EditorialRepositorio;
import com.ej.libreria.repositorios.FotoRepositorio;
import com.ej.libreria.repositorios.LibroRepositorio;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio lr;

    @Autowired
    private AutorRepositorio ar;

    @Autowired
    private EditorialRepositorio er;

    @Autowired
    private FotoService fs;

    @Transactional
    public void crearLibro(MultipartFile archivo, Long isbn, String titulo, Integer ano, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio, IOException {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }

        if (nombreAutor == null) {
            throw new ErrorServicio("El autor no puede estar vacio");
        }

        if (nombreEditorial == null) {
            throw new ErrorServicio("La editorial no puede estar vacia");
        }

        if (isbn == null || isbn.longValue() == 0L) {
            throw new ErrorServicio("El ISBN no puede estar vacia");
        }
        if (ano == null || ano.intValue() == 0) {
            throw new ErrorServicio("El año no puede estar vacio");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no pueden estar vacios");
        }

        Editorial editorial = er.buscarEditorial(nombreEditorial);
        Autor autor = ar.buscarAutor(nombreAutor);
        Foto foto = fs.guardar(archivo);

        if (editorial == null) {
            throw new ErrorServicio("No se encontro la editorial buscada.");
        }

        if (autor == null) {
            throw new ErrorServicio("No se encontro el autor buscado.");
        }

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAno(ano);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setDisponibles(ejemplares);
        libro.setPrestados(0);
        libro.setAlta(true);
        libro.setFoto(foto);

        lr.save(libro);
    }

//    @Transactional
//    public void editarLibro(MultipartFile archivo, String isbn, String titulo, Integer ano, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio, IOException {
//
//        if (isbn == null) {
//            throw new ErrorServicio("El isbn no puede estar vacio");
//        }
//
//        if (titulo == null) {
//            throw new ErrorServicio("El titulo no puede estar vacio");
//        }
//
//         if (ano == null || ano.intValue() == 0) {
//            throw new ErrorServicio("El año no puede estar vacio");
//        }
//
//        if (ejemplares == null) {
//            throw new ErrorServicio("Los ejemplares no pueden estar vacios");
//        }
//        
//        Editorial editorial = er.buscarEditorial(nombreEditorial);
//        Autor autor = ar.buscarAutor(nombreAutor);
//        Foto foto = fs.guardar(archivo);
//
//        if (editorial == null) {
//            throw new ErrorServicio("No se encontro la editorial buscada.");
//        }
//
//        if (autor == null) {
//            throw new ErrorServicio("No se encontro el autor buscado.");
//        }
//
//        Optional<Libro> respuesta = lr.findById(isbn);
//
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            libro.setAno(ano);
//            libro.setTitulo(titulo);
//            libro.setEjemplares(ejemplares);
//            int num = ejemplares - libro.getPrestados();
//            libro.setDisponibles(num);
//            libro.setAutor(autor);
//            libro.setEditorial(editorial);
//            libro.setFoto(foto);
//
//            lr.save(libro);
//        } else {
//            throw new ErrorServicio("No se encontro el libro buscado.");
//        }
//
//    }
//    @Transactional
//    public void darBaja(String isbn) throws ErrorServicio {
//        if (isbn == null) {
//            throw new ErrorServicio("El isbn no puede estar vacio");
//        }
//
//        Optional<Libro> respuesta = lr.findById(isbn);
//
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            if (!libro.isAlta()) {
//                throw new ErrorServicio("El libro esta de baja actualmente.");
//            }
//            libro.setAlta(false);
//            lr.save(libro);
//        } else {
//            throw new ErrorServicio("No se encontro el libro buscado.");
//        }
//    }

    @Transactional
    public void Baja(Long id) {
        Libro libro = lr.buscarLibroId(id);
        libro.setAlta(false);
        lr.save(libro);
    }

    @Transactional
    public void Alta(Long id) {
        Libro libro = lr.buscarLibroId(id);
        libro.setAlta(true);
        lr.save(libro);
    }
//
//    @Transactional
//    public void darAlta(String isbn) throws ErrorServicio {
//        if (isbn == null) {
//            throw new ErrorServicio("El isbn no puede estar vacio");
//        }
//
//        Optional<Libro> respuesta = lr.findById(isbn);
//
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            if (libro.isAlta()) {
//                throw new ErrorServicio("El libro esta de alta actualmente.");
//            }
//            libro.setAlta(true);
//            lr.save(libro);
//        } else {
//            throw new ErrorServicio("No se encontro el libro buscado.");
//        }
//    }

    @Transactional
    public List<Libro> listarLibros() {
        return lr.verLibros();
    }

    @Transactional
    public Libro traerLibro(Long id) {
        return lr.buscarLibroId(id);
    }

    @Transactional
    public void editarLibro(MultipartFile archivo, Long isbn, String titulo, Integer ano, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio, IOException {

        if (isbn == null) {
            throw new ErrorServicio("El isbn no puede estar vacio");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }

        if (ano == null || ano.intValue() == 0) {
            throw new ErrorServicio("El año no puede estar vacio");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no pueden estar vacios");
        }

        Editorial editorial = er.buscarEditorial(nombreEditorial);
        Autor autor = ar.buscarAutor(nombreAutor);
        Foto foto = fs.guardar(archivo);

        if (editorial == null) {
            throw new ErrorServicio("No se encontro la editorial buscada.");
        }

        if (autor == null) {
            throw new ErrorServicio("No se encontro el autor buscado.");
        }

        Libro libro = lr.buscarLibroId(isbn);
        if (libro == null) {
            throw new ErrorServicio("No se encontro el libro buscado.");
        }
        libro.setAno(ano);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        int num = ejemplares - libro.getPrestados();
        libro.setDisponibles(num);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setFoto(foto);

        lr.save(libro);

    }

}

/*
  private String id;
    private long isbn;
    private String titulo;
    private int ano;
    private int ejemplares;
    private int prestados;
    private int restantes;
    private boolean alta;
    private Autor autor;
    private Editorial editorial;
 */
