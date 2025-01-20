package com.mvicente.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birthYear = OptionalInt.of(Integer.parseInt(authorData.birth_year())).orElse(0);
        this.deathYear = OptionalInt.of(Integer.parseInt(authorData.death_year())).orElse(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeath_year() {
        return deathYear;
    }

    public void setDeath_year(Integer death_year) {
        this.deathYear = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthors(this);
    }

    @Override
    public String toString() {
        return "Author{" +
               "name='" + name + '\'' +
               ", birth_year=" + birthYear +
               ", death_year=" + deathYear +
               '}';
    }
}
