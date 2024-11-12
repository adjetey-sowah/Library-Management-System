package com.juls.firstapp.librarymanagementsystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book extends LibraryResource{

    private String author;
    private String isbn;
    private Genre genre;
    private LocalDate publicationDate;

    public Book(String title, String author, String isbn, Genre genre, LocalDate publicationDate){
        super(title);
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationDate = publicationDate;
    }

    public Book(String title, String author){
        super(title);
        this.author = author;
    }

    public Book(String title){
        super(title);
    }

    @Override
    public String getResourcePrefix() {
        return "B";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
