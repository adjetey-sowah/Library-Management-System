package com.juls.firstapp.librarymanagementsystem.model.resource;

import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;

import java.time.LocalDate;

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

    public Book(){
        super("",ResourceType.BOOK);
    }


    public Book(String title,String author){
        super(title,ResourceType.BOOK);
        this.author = author;
    }

    @Override
    public void setResourceType(){
        super.resourceType = ResourceType.BOOK;
    }

    public Book(String title){
        super(title);
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

    @Override
    public String toString() {
        return "Book{" +
                "resourceType=" + resourceType +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", resourceId=" + resourceId +
                ", publicationDate=" + publicationDate +
                ", genre=" + genre +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
