package com.ej.libreria.controladores;

import com.ej.libreria.entidades.Libro;
import com.ej.libreria.errores.ErrorServicio;
import com.ej.libreria.servicios.LibroService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private LibroService ls;

    @GetMapping("/libro/{id}")
    public ResponseEntity<byte[]> fotoLibro(@PathVariable Long id) {
        try {
            Libro libro = ls.traerLibro(id);
            
            if(libro.getFoto() == null){
                throw new ErrorServicio ("El libro no tiene foto");
            }
            byte[] foto = libro.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
    }
}
