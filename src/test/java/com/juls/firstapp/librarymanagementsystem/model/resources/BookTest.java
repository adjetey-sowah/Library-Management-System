package com.juls.firstapp.librarymanagementsystem.model.resources;

import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;
    private final String TEST_TITLE = "Test Book";
    private final String TEST_AUTHOR = "Test Author";
    private final String TEST_ISBN = "1234567890";
    private final Genre TEST_GENRE = Genre.FICTION;
    private final LocalDate TEST_DATE = LocalDate.of(2023, 1, 1);

    @BeforeEach
    void setUp(){
        book = new Book(TEST_TITLE,TEST_AUTHOR,TEST_ISBN,TEST_GENRE,TEST_DATE);

    }

    @Test
    void testBookConstructorWithAllParameters() {
        assertNotNull(book);
        assertEquals(TEST_TITLE, book.getTitle());
        assertEquals(TEST_AUTHOR, book.getAuthor());
        assertEquals(TEST_ISBN, book.getIsbn());
        assertEquals(TEST_GENRE, book.getGenre());
        assertEquals(TEST_DATE, book.getPublicationDate());
        assertEquals(ResourceType.BOOK, book.getResourceType());
        assertEquals(ResourceStatus.AVAILABLE, book.getResourceStatus());
    }

    @Test
    void testDefaultConstructor() {
        Book defaultBook = new Book();
        assertNotNull(defaultBook);
        assertEquals("", defaultBook.getTitle());
        assertEquals(ResourceType.BOOK, defaultBook.getResourceType());
    }

    @Test
    void testConstructorWithTitleAndAuthor() {
        Book titleAuthorBook = new Book(TEST_TITLE, TEST_AUTHOR);
        assertEquals(TEST_TITLE, titleAuthorBook.getTitle());
        assertEquals(TEST_AUTHOR, titleAuthorBook.getAuthor());
        assertEquals(ResourceType.BOOK, titleAuthorBook.getResourceType());
    }

    @Test
    void testSettersAndGetters() {
        Long resourceId = 1L;
        book.setResourceId(resourceId);
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setIsbn("0987654321");
        book.setGenre(Genre.SCIENCE_FICTION);
        LocalDate newDate = LocalDate.of(2024, 1, 1);
        book.setPublicationDate(newDate);
        book.setResourceStatus(ResourceStatus.BORROWED);

        assertEquals(resourceId, book.getResourceId());
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("0987654321", book.getIsbn());
        assertEquals(Genre.SCIENCE_FICTION, book.getGenre());
        assertEquals(newDate, book.getPublicationDate());
        assertEquals(ResourceStatus.BORROWED, book.getResourceStatus());
    }

    @Test
    void testToString() {
        String bookString = book.toString();
        assertTrue(bookString.contains(TEST_TITLE));
        assertTrue(bookString.contains(TEST_AUTHOR));
        assertTrue(bookString.contains(TEST_ISBN));
        assertTrue(bookString.contains(TEST_GENRE.toString()));
        assertTrue(bookString.contains(TEST_DATE.toString()));
    }

}
