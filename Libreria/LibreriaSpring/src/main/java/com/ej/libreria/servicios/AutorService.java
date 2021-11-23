package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Autor;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.repositorios.AutorRepositorio;
import java.util.List;
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
    public void darBaja(Integer id) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Autor aut = ar.buscarAutorId(id);

        if (aut == null) {
            throw new ErrorServicio("No se encontro al autor");
        }

        aut.setAlta(false);
        ar.save(aut);
    }

    @Transactional
    public void darAlta(Integer id) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Autor aut = ar.buscarAutorId(id);

        if (aut == null) {
            throw new ErrorServicio("No se encontro al autor");
        }

        aut.setAlta(true);
        ar.save(aut);

    }

    @Transactional
    public void editarAut(Integer id, String nombre) throws ErrorServicio {
        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        Autor aut = ar.buscarAutorId(id);

        if (aut == null) {
            throw new ErrorServicio("No se encontro al autor");
        }

        aut.setNombre(nombre);
        ar.save(aut);

    }
    
    @Transactional
    public List<Autor> listarAutores(){
        return ar.verAutores();
    }
    
    @Transactional
    public Autor traerAutor(Integer id){
        return ar.buscarAutorId(id);
    }

}
