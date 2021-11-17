package com.ej.libreria.repositorios;

import com.ej.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT c FROM Editorial c ORDER BY c.nombre")
    public List<Editorial> verEditorial();

    @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public Editorial buscarEditorial(@Param("nombre") String nombre);

}
