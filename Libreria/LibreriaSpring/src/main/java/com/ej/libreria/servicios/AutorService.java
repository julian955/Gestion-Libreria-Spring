package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Autor;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio ar;

    
    @Transactional
    public void agregarAutor(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        ar.save(autor);
    }
    
    @Transactional
    public void darBaja(String id) throws ErrorServicio {
        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Optional<Autor> respuesta = ar.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            if (!autor.isAlta()) {
                throw new ErrorServicio("El actor esta de baja actualmente.");
            }

            autor.setAlta(false);
            ar.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor buscado.");
        }
    }
    
    @Transactional
    public void darAlta(String id) throws ErrorServicio {
        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Optional<Autor> respuesta = ar.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            if (autor.isAlta()) {
                throw new ErrorServicio("El actor esta de alta actualmente.");
            }

            autor.setAlta(true);
            ar.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor buscado.");
        }
    }
}
