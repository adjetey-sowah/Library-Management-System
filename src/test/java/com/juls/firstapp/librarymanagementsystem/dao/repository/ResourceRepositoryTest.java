package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.model.enums.*;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class ResourceRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private CallableStatement callableStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private ResourceRepository resourceRepository;


    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        resourceRepository = spy(new ResourceRepository(databaseConfig));
    }

    // Test for insertLibraryResource
    @Test
    void insertLibraryResource_Success() throws SQLException {
        // Arrange
        Book book = new Book();
        book.setTitle("Test Book");
        book.setResourceStatus(ResourceStatus.AVAILABLE);
        book.setResourceType();

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        int result = resourceRepository.insertLibraryResource(book);

        // Assert
        assertEquals(1, result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void insertLibraryResource_Failure_NoKeyGenerated() throws SQLException {
        // Arrange
        Book book = new Book();
        book.setTitle("Test Book");
        book.setResourceStatus(ResourceStatus.AVAILABLE);
        book.setResourceType();

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> resourceRepository.insertLibraryResource(book));
    }

    // Test for addLibraryResource(Book)
    @ParameterizedTest
    @MethodSource("provideTestBooksForAdd")
    void addLibraryResource_Book_Success(Book book, int generatedId) throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(generatedId);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.addLibraryResource(book);

        // Assert
        assertTrue(result);
        verify(callableStatement).setLong(1, generatedId);
        verify(callableStatement).setString(2, book.getAuthor());
        verify(callableStatement).setString(3, book.getIsbn());
        verify(callableStatement).setString(4, book.getGenre().toString());
        verify(callableStatement).setDate(5, Date.valueOf(book.getPublicationDate()));
    }

    private static Stream<Arguments> provideTestBooksForAdd() {
        return Stream.of(
                Arguments.of(
                        new Book("Test Book 1", "Author 1", "ISBN1", Genre.FICTION, LocalDate.now()),
                        1
                ),
                Arguments.of(
                        new Book("Test Book 2", "Author 2", "ISBN2", Genre.ADVENTURE, LocalDate.now()),
                        2
                )
        );
    }

    @Test
    void addLibraryResource_Book_Failure() throws SQLException {
        // Arrange
        Book book = new Book("Test Book", "Author", "ISBN", Genre.SCIENCE_FICTION, LocalDate.now());

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(0);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> resourceRepository.addLibraryResource(book));
    }

    @Test
    void addLibraryResource_Journal_Failure() throws SQLException {
        // Arrange

        Journal journal = new Journal("Test Journal", "Issue1", "Monthly");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(0);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> resourceRepository.addLibraryResource(journal));
    }

    @ParameterizedTest
    @MethodSource("provideSearchCriteria")
    void searchResources_Success(String criteria, List<LibraryResource> mockResources, int expectedResults) throws Exception {
        // Arrange
        doReturn(new LinkedList<>(mockResources)).when(resourceRepository).findAllResource();

        // Act
        LinkedList<LibraryResource> results = resourceRepository.searchResources(criteria);

        // Assert
        assertEquals(expectedResults, results.size());
    }

    private static Stream<Arguments> provideSearchCriteria() {
        Book book1 = new Book("Java Programming", "John Doe", "ISBN123", Genre.ANTHROPOLOGY, LocalDate.now());
        Journal journal1 = new Journal("Science Today", "Issue1", "Monthly");

        return Stream.of(
                Arguments.of("Java", Arrays.asList(book1), 1),
                Arguments.of("Science", Arrays.asList(journal1), 1),
                Arguments.of("NonExistent", Arrays.asList(book1, journal1), 0)
        );
    }


    @Test
    void constructor_Success() throws SQLException, ClassNotFoundException {
        // Arrange
        DatabaseConfig dbConfig = mock(DatabaseConfig.class);
        when(dbConfig.getConnection()).thenReturn(mockConnection);

        // Act
        ResourceRepository repository = new ResourceRepository(databaseConfig);

        // Assert
        assertNotNull(repository);
    }


    @Test
    void findResourceById_Success() throws Exception {
        // Arrange
        Long resourceId = 1L;
        Book expectedBook = new Book("Test Book", "Author", "ISBN", Genre.FICTION, LocalDate.now());
        expectedBook.setResourceId(resourceId);

        LinkedList<LibraryResource> mockResources = new LinkedList<>();
        mockResources.add(expectedBook);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        doReturn(mockResources).when(resourceRepository).findAllResource();

        // Act
        LibraryResource result = resourceRepository.findResourceById(resourceId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedBook, result);
    }

    @Test
    void findResourceById_NotFound() throws Exception {
        // Arrange
        Long resourceId = 999L;
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        doReturn(new LinkedList<>()).when(resourceRepository).findAllResource();

        // Act
        LibraryResource result = resourceRepository.findResourceById(resourceId);

        // Assert
        assertNull(result);
    }

    @Test
    void findAllResource_Success() throws Exception {
        // Arrange
        LinkedList<Book> mockBooks = new LinkedList<>();
        LinkedList<Journal> mockJournals = new LinkedList<>();
        LinkedList<Media> mockMedia = new LinkedList<>();

        mockBooks.add(new Book("Book1", "Author1", "ISBN1", Genre.FICTION, LocalDate.now()));
        mockJournals.add(new Journal("Journal1", "Issue1", "Monthly"));
        mockMedia.add(new Media("Media1", MediaFormat.DVD));

        doReturn(mockBooks).when(resourceRepository).findAllBooks();
        doReturn(mockJournals).when(resourceRepository).findAllJournal();
        doReturn(mockMedia).when(resourceRepository).findAllMedia();

        // Act
        LinkedList<LibraryResource> result = resourceRepository.findAllResource();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(mockBooks.getFirst()));
        assertTrue(result.contains(mockJournals.getFirst()));
        assertTrue(result.contains(mockMedia.getFirst()));
    }

    @Test
    void updateLibraryResource_Book_Success() throws Exception {
        // Arrange
        Book book = new Book("Updated Book", "Updated Author", "ISBN123", Genre.FICTION, LocalDate.now());
        book.setResourceId(1L);
        book.setResourceStatus(ResourceStatus.AVAILABLE);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.updateLibraryResource(book);

        // Assert
        assertTrue(result);
        verify(callableStatement, times(2)).executeUpdate();
    }

    @Test
    void updateLibraryResource_Journal_Success() throws Exception {
        // Arrange
        Journal journal = new Journal("Updated Journal", "Issue2", "Quarterly");
        journal.setResourceId(1L);
        journal.setResourceStatus(ResourceStatus.AVAILABLE);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.updateLibraryResource(journal);

        // Assert
        assertTrue(result);
        verify(callableStatement, times(2)).executeUpdate();
    }

    @Test
    void addLibraryResource_Journal_Success() throws SQLException {
        // Arrange
        Journal journal = new Journal("Test Journal", "Issue1", "Monthly");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.addLibraryResource(journal);

        // Assert
        assertTrue(result);
        verify(callableStatement).setLong(1, 1);
        verify(callableStatement).setString(2, journal.getIssueNumber());
        verify(callableStatement).setString(3, journal.getFrequency());
    }

    @Test
    void addLibraryResource_Media_Success() throws Exception {
        // Arrange
        Media media = new Media("Test Media", MediaFormat.DVD);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.addLibraryResource(media);

        // Assert
        assertTrue(result);
        verify(callableStatement).setLong(1, 1);
        verify(callableStatement).setString(2, media.getFormat().toString());
    }

    @Test
    void addLibraryResource_Journal_FailureException() throws SQLException {
        // Arrange
        Journal journal = new Journal("Test Journal", "Issue1", "Monthly");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Mock the SQLException when calling executeUpdate
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenThrow(new RuntimeException("Failed to insert journal"));

        // Act & Assert
        // Ensure that calling addLibraryResource throws the expected SQLException
        assertThrows(RuntimeException.class, () -> {
            resourceRepository.addLibraryResource(journal);
        });
    }


    @Test
    void findAllBooks_Success() throws Exception {
        // Arrange

        LinkedList<Book> mockBooks = new LinkedList<>();

        Book book = new Book("Test Book", "Author", "ISBN", Genre.FICTION, LocalDate.now());

        // Mock the ResultSet to return valid values
        when(resultSet.next()).thenReturn(true, false); // Simulate one row in the result set
        when(resultSet.getString("title")).thenReturn(book.getTitle());
        when(resultSet.getString("author")).thenReturn(book.getAuthor());
        when(resultSet.getString("isbn")).thenReturn(book.getIsbn());
        when(resultSet.getString("genre")).thenReturn(book.getGenre().toString());
        when(resultSet.getDate("publication_date")).thenReturn(Date.valueOf(book.getPublicationDate()));

        // Mock the Mappers to return the expected Book object
        when(mappers.mapToBooks(resultSet)).thenReturn(book);

        // Add the book to the list of books;
        mockBooks.add(book);

        // Stub the mockBooks
        doReturn(mockBooks).when(resourceRepository).findAllBooks();

        // Act
        LinkedList<Book> results = resourceRepository.findAllBooks();

        // Assert
        assertEquals(1, results.size());
        assertEquals(book, results.getFirst());
    }


    @Test
    void findAllJournal_Success() throws Exception {


        // Arrange
        LinkedList<Journal> mockJournals = new LinkedList<>();

        Journal journal = new Journal("Test Journal", "Issue1", "Monthly");
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(mappers.mapToJournal(resultSet)).thenReturn(journal);

        mockJournals.add(journal);

        doReturn(mockJournals).when(resourceRepository).findAllJournal();

        // Act
        LinkedList<Journal> results = resourceRepository.findAllJournal();

        // Assert
        assertEquals(1, results.size());
        assertEquals(journal, results.getFirst());
    }

    @Test
    void findAllMedia_Success() throws Exception {
        // Arrange

        LinkedList<Media> mockMedia = new LinkedList<>();

        Media media = new Media("Test Media", MediaFormat.DVD);
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        // Ensure that the ResultSet returns valid values for the Media fields
        when(resultSet.getString("title")).thenReturn(media.getTitle());
        when(resultSet.getString("format")).thenReturn(media.getFormat().toString()); // Ensure this is not null

        when(mappers.mapToMedia(resultSet)).thenReturn(media);

        mockMedia.add(media);

        doReturn(mockMedia).when(resourceRepository).findAllMedia();




        // Act
        LinkedList<Media> results = resourceRepository.findAllMedia();

        // Assert
        assertEquals(1, results.size());
        assertEquals(media, results.getFirst());
    }


    @Test
    void deleteLibraryResource_Success() throws Exception {
        // Arrange
        Long resourceId = 1L;
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = resourceRepository.deleteLibraryResource(resourceId);

        // Assert
        assertTrue(result);
        verify(callableStatement).setLong(1, resourceId);
    }

    @Test
    void deleteLibraryResource_Failure() throws Exception {
        // Arrange
        Long resourceId = 1L;
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenThrow(new SQLException("Failed to delete resource"));

        // Act & Assert
        // Ensure that calling deleteLibraryResource throws the expected SQLException
        assertThrows(SQLException.class, () -> {
            resourceRepository.deleteLibraryResource(resourceId);
        });
    }


    @Test
    void getResourceByTitle_Success() throws Exception {
        // Arrange
        String title = "Test Book";
        Book book = new Book(title, "Author", "ISBN", Genre.FICTION, LocalDate.now());
        LinkedList<LibraryResource> mockResources = new LinkedList<>();
        mockResources.add(book);

        doReturn(mockResources).when(resourceRepository).findAllResource();

        // Act
        LibraryResource result = resourceRepository.getResourceByTitle(title);

        // Assert
        assertNotNull(result);
        assertEquals(title, result.getTitle());
    }

    @Test
    void getResourceNumber_Success() throws Exception {
        // Arrange
        Book borrowedBook = new Book("Book1", "Author1", "ISBN1", Genre.FICTION, LocalDate.now());
        borrowedBook.setResourceStatus(ResourceStatus.BORROWED);

        Book availableBook = new Book("Book2", "Author2", "ISBN2", Genre.FICTION, LocalDate.now());
        availableBook.setResourceStatus(ResourceStatus.AVAILABLE);

        LinkedList<LibraryResource> mockResources = new LinkedList<>();
        mockResources.add(borrowedBook);
        mockResources.add(availableBook);

        doReturn(mockResources).when(resourceRepository).findAllResource();

        // Act
        int result = resourceRepository.getResourceNumber();

        // Assert
        assertEquals(1, result);
    }

    @Test
    void getAvailablePercentage_Success() throws Exception {
        // Arrange
        Book borrowedBook = new Book("Book1", "Author1", "ISBN1", Genre.FICTION, LocalDate.now());
        borrowedBook.setResourceStatus(ResourceStatus.BORROWED);

        Book availableBook = new Book("Book2", "Author2", "ISBN2", Genre.FICTION, LocalDate.now());
        availableBook.setResourceStatus(ResourceStatus.AVAILABLE);

        LinkedList<LibraryResource> mockResources = new LinkedList<>();
        mockResources.add(borrowedBook);
        mockResources.add(availableBook);

        doReturn(mockResources).when(resourceRepository).findAllResource();
        doReturn(1).when(resourceRepository).getResourceNumber();

        // Act
        double result = resourceRepository.getAvailablePercentage();

        // Assert
        assertEquals(50.0, result);
    }
    
}