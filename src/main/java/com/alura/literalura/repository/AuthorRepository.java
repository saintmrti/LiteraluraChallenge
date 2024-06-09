package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String firstName);
    @Query("SELECT a FROM Author a WHERE a.birth_year <= :anio AND a.death_year >= :anio")
    List<Author> findByBirthYearAndDeathYear(int anio);
}
