package com.mvicente.literalura.repository;

import com.mvicente.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByLanguages(String language);
}
