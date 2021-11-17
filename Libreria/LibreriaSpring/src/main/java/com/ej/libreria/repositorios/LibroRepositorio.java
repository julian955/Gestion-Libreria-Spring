package com.ej.libreria.repositorios;

import com.ej.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT c FROM Libro c ORDER BY c.titulo")
    public List<Libro> verLibros();

    @Query("SELECT c FROM Libro c WHERE c.titulo = :nombre")
    public Libro buscarLibro(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Libro c WHERE c.isbn = :isbn")
    public Libro buscarLibroId(@Param("isbn") Long isbn);

}
