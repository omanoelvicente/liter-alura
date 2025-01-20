package com.mvicente.literalura.mainmenu;


import com.mvicente.literalura.model.Author;
import com.mvicente.literalura.model.Book;
import com.mvicente.literalura.model.BookResults;
import com.mvicente.literalura.repository.AuthorRepository;
import com.mvicente.literalura.repository.BookRepository;
import com.mvicente.literalura.service.ApiConsumer;
import com.mvicente.literalura.service.JsonConverter;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainMenu {

    ApiConsumer consumer = new ApiConsumer();
    JsonConverter converter = new JsonConverter();

    Scanner scanner = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books?search=";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public MainMenu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            String menu =
                    """
                            
                             1- Buscar livro pelo título
                             2- Listar livros registrados
                             3- Listar autores registrados
                             4- Listar autores vivos em um determinado ano
                             5- Listar livros em um determinado idioma
                             0- Sair
                            
                            """;
            try {
                System.out.println(menu);
                System.out.println(" Escolha um número de sua opção:");
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada de dados inválida!");
            }
            switch (option) {
                case 1:
                    findBookByTitle();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAuthorsByYear();
                    break;
                case 5:
                    listBookByLanguage();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        }
    }

    private void findBookByTitle() {
        System.out.println("Digite o nome do Livro");
        var bookName = URLEncoder.encode(scanner.nextLine(), StandardCharsets.UTF_8);

        var json = consumer.requestData(URL_BASE + bookName.toLowerCase());
        var bookResults = converter.getData(json, BookResults.class).results();

        if (bookResults.isEmpty()) {
            System.out.println("Livro não encontrado");
        } else {
            var bookData = bookResults.get(0);
            var book = new Book(bookData);
            var author = new Author(bookData.authors().get(0));

            saveBook(book, author);
        }
    }

    private void saveBook(Book book, Author author) {
        Optional<Book> foundBook = bookRepository.findByTitleContainingIgnoreCase(book.getTitle());
        Optional<Author> foundAuthor = authorRepository.findByName(author.getName());

        if (foundBook.isPresent()) {
            System.out.println("livro já cadastrado!");
        } else if (foundAuthor.isPresent()) {
            Author existingAuthor = foundAuthor.get();
            book.setAuthors(existingAuthor);
            bookRepository.save(book);
        }else {
            book.setAuthors(author);
            author.addBook(book);
            authorRepository.save(author);
            System.out.println("Livro cadastrado com sucesso! \n");
        }
        System.out.println("::::::::::LIVRO::::::::::");
        System.out.println("Título: " + book.getTitle());
        System.out.println("Autor: " + author.getName());
        System.out.println("Idioma: " + book.getLanguages());
        System.out.println("Numero de Downloads: " + book.getDownload_count());
        System.out.println("--------------------------");
    }

    private void listAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        showBook(bookList);

    }

    private void listAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        showAuthor(authorList);
    }

    private void listAuthorsByYear() {
        System.out.println("Informe o ano para pesquisa");
        var year = scanner.nextInt();
        List<Author> authorList = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThan(year, year);
        if (authorList.isEmpty()) {
            System.out.println("Nenhum autor encontrado!");
        } else {
           showAuthor(authorList);
        }
    }
    private void listBookByLanguage() {
        System.out.println( """
                es - Espanhol
                en - Ingês
                fr - francês
                pt - Português
                
                Informe o Idioma para busca
                """);
        var language = scanner.nextLine();
        List<Book> bookList = bookRepository.findByLanguages(language);
        if (bookList.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma escolhido.");
        } else {
            showBook(bookList);
        }
    }

    private void showBook(List<Book> bookList) {
        System.out.println("::::::::::LIVRO::::::::::");
        bookList.forEach(b -> {
            System.out.println("Título: " + b.getTitle());
            System.out.println("Autor: " +  b.getAuthors().getName());
            System.out.println("Idioma: " + b.getLanguages());
            System.out.println("Numero de Downloads: " + b.getDownload_count() + "\n");
            System.out.println("--------------------------");
        });

    }

    private void showAuthor(List<Author> authorList) {
        System.out.println("::::::::::AUTOR::::::::::");
        authorList.forEach(a -> {
            System.out.println("Autor: " + a.getName());
            System.out.println("Data de nascimemnto: " + a.getBirthYear());
            System.out.println("Data de falecimento: " + a.getDeath_year());
            System.out.println("Livros: " + a.getBooks().stream().map(Book::getTitle).collect(Collectors.joining(", ")) + "\n");
            System.out.println("--------------------------");
        });

    }
}



