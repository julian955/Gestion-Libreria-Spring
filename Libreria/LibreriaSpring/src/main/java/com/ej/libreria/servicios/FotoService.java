package com.ej.libreria.servicios;

import com.ej.libreria.entidades.Foto;
import com.ej.libreria.repositorios.FotoRepositorio;
import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

    @Autowired
    private FotoRepositorio fr;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws IOException {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fr.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}
