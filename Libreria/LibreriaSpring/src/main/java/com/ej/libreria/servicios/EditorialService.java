package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Editorial;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.repositorios.EditorialRepositorio;
import java.util.List;
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
    public void darBaja(Long id) throws ErrorServicio {

        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Editorial edt = er.buscarEditorialId(id);

        if (edt == null) {
            throw new ErrorServicio("No se encontro la editorial.");
        }

        edt.setAlta(false);
        er.save(edt);

    }

    @Transactional
    public void darAlta(Long id) throws ErrorServicio {

        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        Editorial edt = er.buscarEditorialId(id);

        if (edt == null) {
            throw new ErrorServicio("No se encontro la editorial.");
        }

        edt.setAlta(true);
        er.save(edt);
    }

    @Transactional
    public void editarEdt(Long id, String nombre) throws ErrorServicio {

        if (id == null) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        Editorial edt = er.buscarEditorialId(id);

        if (edt == null) {
            throw new ErrorServicio("No se encontro la editorial.");
        }

        edt.setNombre(nombre);
        er.save(edt);
    }
    
    
    @Transactional
    public List<Editorial> listarEditoriales (){
        return er.verEditoriales();
    }
    
     @Transactional
    public Editorial traerEditorial (Long id){
        return er.buscarEditorialId(id);
    }

}
