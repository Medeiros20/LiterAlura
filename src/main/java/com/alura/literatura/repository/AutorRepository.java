package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);


    @Query("SELECT a FROM Autor a WHERE a.dataNascimento <= :ano AND (a.dataFalecimento >= :ano OR a.dataFalecimento IS NULL)")
    List<Autor> autoresVivosNoAno(int ano);

}
