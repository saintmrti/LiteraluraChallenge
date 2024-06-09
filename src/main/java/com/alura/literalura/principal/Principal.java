package com.alura.literalura.principal;

import com.alura.literalura.model.ApiResult;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.DataBook;
import com.alura.literalura.model.Author;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner monitor;
    private ConsumoAPI consumoApi;
    private ConvierteDatos conversor;
    private final String URL_BASE;
    private BookRepository repositoryBook;
    private AuthorRepository repositoryAuthor;
    List<Author> authors;
    List<Book> books;

    public Principal(BookRepository repositoryBook, AuthorRepository repositoryAuthor) {
        this.monitor = new Scanner(System.in);
        this.consumoApi = new ConsumoAPI();
        this.conversor = new ConvierteDatos();
        this.URL_BASE = "https://gutendex.com/books/";
        this.repositoryBook = repositoryBook;
        this.repositoryAuthor = repositoryAuthor;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while(opcion != 0) {
            String menu = """
                    1 - Buscar libro
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar libros por lenguaje
                    5 - Autores vivos en determinado año
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = this.monitor.nextInt();
            this.monitor.nextLine();
            switch (opcion) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    showBooksByAuthors();
                    break;
                case 4:
                    showAllBooksByLanguage();
                    break;
                case 5:
                    showAuthorsAlive();
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private List<DataBook> getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        String titulo = this.monitor.nextLine();
        String json = this.consumoApi.obtenerDatos(this.URL_BASE + "?search=" + titulo.replace(" ", "%20"));
        ApiResult datos = this.conversor.obtenerDatos(json, ApiResult.class);
        return datos.results();
    }

    private void searchBook() {
        List<DataBook> dataBooks = this.getDatosLibro();
        if (!dataBooks.isEmpty()) {
            var bookFound = dataBooks.get(0);
            Book book = new Book(bookFound.title(), bookFound.languages().get(0), bookFound.download_count());
            System.out.println(book);
            Optional<Book> bookExist = this.repositoryBook.findByTitle(book.getTitle());
            if (bookExist.isPresent()) {
                System.out.println("\n El libro ya esta registrado\n");
            } else {
                if (!bookFound.authors().isEmpty()) {
                    var firstAuthor = bookFound.authors().get(0);
                    Author author = new Author(firstAuthor.getName(), firstAuthor.getBirth_year(), firstAuthor.getDeath_year());
                    Optional<Author> authorExist = this.repositoryAuthor.findByName(author.getName());
                    if (authorExist.isPresent()) {
                        Author authorFound = authorExist.get();
                        book.setAuthor(authorFound);
                        this.repositoryBook.save(book);
                    } else {
                        Author authorNew = this.repositoryAuthor.save(author);
                        book.setAuthor(authorNew);
                        this.repositoryBook.save(book);
                    }
                } else {
                    System.out.println("\n Sin autor disponible\n");
                }
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void showAllBooks() {
        books = this.repositoryBook.findAll();
        books.forEach(System.out::println);
    }

    private void showBooksByAuthors() {
        authors = this.repositoryAuthor.findAll();
        authors.forEach(System.out::println);
    }

    private void showAllBooksByLanguage() {
        System.out.println("Elija el idioma que desea buscar: ");
        String options = """
                    es - Español
                    en - Ingles
                    fr - Frances
                    """;
        System.out.println(options);
        String idioma = this.monitor.nextLine();
        List<Book> booksFound = this.repositoryBook.findByLanguageContainsIgnoreCase(idioma);
        booksFound.forEach(System.out::println);
    }

    private void showAuthorsAlive() {
        System.out.println("Ingresa el año vivo de autores que desea buscar: ");
        var anio = this.monitor.nextInt();
        authors = this.repositoryAuthor.findByBirthYearAndDeathYear(anio);
        authors.forEach(System.out::println);
    }
}
