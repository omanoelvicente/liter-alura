package com.mvicente.literalura.repository;

import com.mvicente.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookREpository extends JpaRepository<Book, Long> {
}
