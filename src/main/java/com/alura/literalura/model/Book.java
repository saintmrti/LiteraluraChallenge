package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "libros"
)
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            unique = true
    )
    private String title;
    private String language;
    private Integer downloads;
    @ManyToOne
    private Author author;

    public Book(String title, String language, Integer downloads) {
        this.title = title;
        this.language = language;
        this.downloads = downloads;
    }

    public Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", authors=" + author +
                ", language='" + language + '\'' +
                ", downloads=" + downloads;
    }
}
