package com.mvicente.literalura.model;

import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private String title;
    private String languages;
    private Integer download_count;
    @ManyToOne
   private Author authors;

    public Book() {}

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.languages = bookData.languages().get(0);
        this.download_count = Integer.valueOf(bookData.download_count());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Books{" +
               "title='" + title + '\'' +
               ", languages='" + languages + '\'' +
               ", download_count=" + download_count +
               ", author='" + authors + '\'' +
               '}';
    }


}
