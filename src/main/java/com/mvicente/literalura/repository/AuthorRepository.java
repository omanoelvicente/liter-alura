package com.mvicente.literalura.repository;

import com.mvicente.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThan(int year, int year1);
}

