package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Editorial;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepositorio er;

    
    @Transactional
    public void crearEditorial(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorial.setAlta(true);

        er.save(editorial);
    }
    
    @Transactional
    public void darBaja(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Optional<Editorial> respuesta = er.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            if (!editorial.isAlta()) {
                throw new ErrorServicio("Esta editorial esta de baja actualmente.");
            }

            editorial.setAlta(false);
            er.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro la editorial buscada.");
        }

    }
    
    @Transactional
    public void darAlta(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Optional<Editorial> respuesta = er.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            if (editorial.isAlta()) {
                throw new ErrorServicio("Esta editorial esta de alta actualmente.");
            }

            editorial.setAlta(true);
            er.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro la editorial buscada.");
        }

    }

}
