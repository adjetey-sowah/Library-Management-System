package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.ResourceDAO;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;

import java.sql.*;
import java.util.LinkedList;
<<<<<<< HEAD

=======
import java.util.List;

public class ResourceRepository implements ResourceDAO
{
>>>>>>> 36bcf26f3a368ebe348d3816f9b3a97bb94c2dee

public class ResourceRepository implements ResourceDAO
{
    private final Connection connection;
    private final Mappers mappers;

    public ResourceRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
        this.mappers = new Mappers();
    }

    @Override
    public int insertLibraryResource(LibraryResource resource){
        String sql = "INSERT INTO library_resource(title,status,resource_type) " +
                "VALUES (?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,resource.getTitle());
            preparedStatement.setString(2,resource.getResourceStatus().toString());
            preparedStatement.setString(3,resource.getResourceType().toString());
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Could not add resource, no key generated");
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException("Could not add user :"+e.getMessage());
        }
    }

    @Override
    public boolean addLibraryResource(Book book){
        int id = this.insertLibraryResource(book);
        String sql = "{call insertBook(?,?,?,?,?)}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,id);
            callableStatement.setString(2, book.getAuthor());
            callableStatement.setString(3,book.getIsbn());
            callableStatement.setString(4,book.getGenre().toString());
            callableStatement.setDate(5,Date.valueOf(book.getPublicationDate()));

            int row = callableStatement.executeUpdate();

                if (row > 0){
                    return true;
            }
                else throw new RuntimeException("Could not add book");

        }
        catch (SQLException e){
            throw new RuntimeException("Could not add book"+e.getMessage());
        }
    }

    @Override
    public boolean addLibraryResource(Journal journal){
        String sql = "call  insertJournal(?,?,?)";
        int id = insertLibraryResource(journal);

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,id);
            callableStatement.setString(2,journal.getIssueNumber());
            callableStatement.setString(3,journal.getFrequency());

            int row = callableStatement.executeUpdate();

            if (row > 0){
                return true;
            }
            else throw new RuntimeException("Could not add Journal");
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public boolean addLibraryResource(Media media) throws Exception {

        String sql = "{call insertMedia(?,?)}";
        int id = insertLibraryResource(media);

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,id);
            callableStatement.setString(2,media.getFormat().toString());

            int row = callableStatement.executeUpdate();

            return row > 0;
        }

    }

    public LibraryResource findResourceById(Long id) throws Exception {
        LibraryResource resource = null;
        for (LibraryResource res : findAllResource()){
            if(res.getResourceId() == id){
                resource = res;
            }
        }
        return resource;
<<<<<<< HEAD
    }

    @Override
    public boolean deleteLibraryResource(Long id) throws Exception {
        String sql = "{call deleteResource(?)}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,id);
            return 0< callableStatement.executeUpdate();
        }
        catch (RuntimeException e){
            throw new Exception("Could not delete resource");
        }
    }

    @Override
    public boolean updateLibraryResource(LibraryResource resource) throws Exception{
        String sql = "";
        boolean isUpdated = false;
        boolean itemUpdated = false;

        if (resource instanceof Book){
            sql = "{call updateBook(?,?,?,?,?)}";
            try(CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,((Book) resource).getAuthor());
                callableStatement.setString(3,((Book) resource).getIsbn());
                callableStatement.setString(4,((Book) resource).getGenre().toString());
                callableStatement.setDate(5,Date.valueOf(((Book) resource).getPublicationDate().toString()));

                itemUpdated =  0 <= callableStatement.executeUpdate();
            }
        }
        else if(resource instanceof Journal){
            sql = "{cal updateJournal(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,((Journal) resource).getIssueNumber());
                callableStatement.setString(3,((Journal) resource).getFrequency());

                itemUpdated= callableStatement.executeUpdate() > 0;
            }

            sql = "{call updateResource(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,resource.getTitle());
                callableStatement.setString(3,resource.getResourceStatus().toString());

                isUpdated= callableStatement.executeUpdate() > 0;

            }
        }

        return isUpdated&&itemUpdated;

    }

    @Override
    public LinkedList<LibraryResource> findAllResource() throws Exception {
        LinkedList<LibraryResource> allResources = new LinkedList<>();
        allResources.addAll(findAllBooks());
        allResources.addAll(findAllJournal());
        allResources.addAll(findAllMedia());
        return allResources;
    }

    @Override
    public LinkedList<Book> findAllBooks() throws Exception {
        String sql = "{call findAllBooks()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet =  callableStatement.executeQuery();
            LinkedList<Book> bookList = new LinkedList<>();
            while (resultSet.next()){
                bookList.add(mappers.mapToBooks(resultSet));
            }

            return bookList;
        }
    }

    @Override
    public LinkedList<Journal> findAllJournal() throws Exception {
        String sql = "{call findAllJournal()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet =  callableStatement.executeQuery();
            LinkedList<Journal> journalList = new LinkedList<>();
            while (resultSet.next()){
                journalList.add(mappers.mapToJournal(resultSet));
            }

            return journalList;
        }
    }

    @Override
    public LinkedList<Media> findAllMedia() throws Exception {
        String sql = "{call findAllMedia()}";
        LinkedList<Media>  mediaList = new LinkedList<>();

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()){
                mediaList.add(mappers.mapToMedia(resultSet));
            }

        }
        return mediaList;
    }

    @Override
    public LibraryResource getResourceByTitle(String title) throws Exception {
        for(LibraryResource res : this.findAllResource()){
            if(res.getTitle().toLowerCase().contains(title.toLowerCase())){
                return res;
            }
        }
        return null;
    }

    @Override
    public LinkedList<LibraryResource> searchResources(String criteria) throws Exception {
        LinkedList<LibraryResource> searchResults = new LinkedList<>();


        for(LibraryResource resource : findAllResource()){
            if (resource.getTitle()
                    .equalsIgnoreCase(criteria) ||
                    String.valueOf(resource
                            .getTitle())
                            .toLowerCase()
                            .contains(criteria.toLowerCase())){
                searchResults.add(resource);
            }

            else if (resource instanceof Book) {
                if (((Book) resource).getAuthor().equalsIgnoreCase(criteria)
                || ((Book) resource).getAuthor().toLowerCase().contains(criteria.toLowerCase())){
                    searchResults.add(resource);
                }
                else if (((Book) resource).getIsbn().toLowerCase().contains(criteria.toLowerCase())) {
                    searchResults.add(resource);
                }
                else if (((Book) resource)
                            .getGenre().toString()
                            .toLowerCase()
                            .contains(criteria.toLowerCase())){

                    searchResults.add(resource);
                    }
            }

            else if (resource instanceof Journal) {
                if(((Journal) resource)
                        .getIssueNumber()
                        .toLowerCase()
                        .contains(criteria.toLowerCase())){
                    searchResults.add(resource);
                }

                else if (((Journal) resource)
                        .getFrequency()
                        .toLowerCase()
                        .contains(criteria.toLowerCase())) {
                    searchResults.add(resource);
                }
            }

        }

        return searchResults;
=======
>>>>>>> 36bcf26f3a368ebe348d3816f9b3a97bb94c2dee
    }


    @Override
    public boolean deleteLibraryResource(Long id) throws Exception {
        String sql = "{call deleteResource(?)}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,id);
            return 0< callableStatement.executeUpdate();
        }
        catch (RuntimeException e){
            throw new Exception("Could not delete resource");
        }
    }

    @Override
    public boolean updateLibraryResource(LibraryResource resource) throws Exception{
        String sql = "";
        boolean isUpdated = false;
        boolean itemUpdated = false;

        if (resource instanceof Book){
            sql = "{call updateBook(?,?,?,?,?)}";
            try(CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,((Book) resource).getAuthor());
                callableStatement.setString(3,((Book) resource).getIsbn());
                callableStatement.setString(4,((Book) resource).getGenre().toString());
                callableStatement.setDate(5,Date.valueOf(((Book) resource).getPublicationDate().toString()));

                itemUpdated =  0 <= callableStatement.executeUpdate();
            }
        }
        else if(resource instanceof Journal){
            sql = "{cal updateJournal(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,((Journal) resource).getIssueNumber());
                callableStatement.setString(3,((Journal) resource).getFrequency());

                itemUpdated= callableStatement.executeUpdate() > 0;
            }

            sql = "{call updateResource(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)){
                callableStatement.setLong(1,resource.getResourceId());
                callableStatement.setString(2,resource.getTitle());
                callableStatement.setString(3,resource.getResourceStatus().toString());

                isUpdated= callableStatement.executeUpdate() > 0;

            }
        }

        return isUpdated&&itemUpdated;

    }

    @Override
    public LinkedList<LibraryResource> findAllResource() throws Exception {
        LinkedList<LibraryResource> allResources = new LinkedList<>();
        allResources.addAll(findAllBooks());
        allResources.addAll(findAllJournal());
        allResources.addAll(findAllMedia());
        return allResources;
    }

    @Override
    public LinkedList<Book> findAllBooks() throws Exception {
        String sql = "{call findAllBooks()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet =  callableStatement.executeQuery();
            LinkedList<Book> bookList = new LinkedList<>();
            while (resultSet.next()){
                bookList.add(mappers.mapToBooks(resultSet));
            }

            return bookList;
        }
    }

    @Override
    public LinkedList<Journal> findAllJournal() throws Exception {
        String sql = "{call findAllJournal()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet =  callableStatement.executeQuery();
            LinkedList<Journal> journalList = new LinkedList<>();
            while (resultSet.next()){
                journalList.add(mappers.mapToJournal(resultSet));
            }

            return journalList;
        }
    }

    @Override
    public LinkedList<Media> findAllMedia() throws Exception {
        String sql = "{call findAllMedia()}";
        LinkedList<Media>  mediaList = new LinkedList<>();

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()){
                mediaList.add(mappers.mapToMedia(resultSet));
            }

        }
        return mediaList;
    }

    @Override
    public LibraryResource getResourceByTitle(String title) throws Exception {
        LibraryResource resource = null;
        for(LibraryResource res : this.findAllResource()){
            if(res.getTitle().equalsIgnoreCase(title)){
                resource = res;
                break;
            }
            else throw new Exception("Cannot find resource");
        }
        return resource;
    }

    @Override
    public LinkedList<LibraryResource> searchResources(String criteria) throws Exception {
        LinkedList<LibraryResource> searchResults = new LinkedList<>();


        for(LibraryResource resource : findAllResource()){
            if (resource.getTitle()
                    .equalsIgnoreCase(criteria) ||
                    String.valueOf(resource
                            .getTitle())
                            .toLowerCase()
                            .contains(criteria.toLowerCase())){
                searchResults.add(resource);
            }

            else if (resource instanceof Book) {
                if (((Book) resource).getAuthor().equalsIgnoreCase(criteria)
                || ((Book) resource).getAuthor().toLowerCase().contains(criteria.toLowerCase())){
                    searchResults.add(resource);
                }
                else if (((Book) resource).getIsbn().toLowerCase().contains(criteria.toLowerCase())) {
                    searchResults.add(resource);
                }
                else if (((Book) resource)
                            .getGenre().toString()
                            .toLowerCase()
                            .contains(criteria.toLowerCase())){

                    searchResults.add(resource);
                    }
            }

            else if (resource instanceof Journal) {
                if(((Journal) resource)
                        .getIssueNumber()
                        .toLowerCase()
                        .contains(criteria.toLowerCase())){
                    searchResults.add(resource);
                }

                else if (((Journal) resource)
                        .getFrequency()
                        .toLowerCase()
                        .contains(criteria.toLowerCase())) {
                    searchResults.add(resource);
                }
            }

        }

        return searchResults;
    }


    public static void main(String[] args) throws Exception {
        ResourceRepository repository = new ResourceRepository();

        LinkedList<LibraryResource> resource = repository.searchResources("THE");
        resource.forEach(System.out::println);

    }

    public static void main(String[] args) throws Exception {
        ResourceRepository repository = new ResourceRepository();




        LibraryResource resource = repository.getResourceByTitle("The Catcher");
        System.out.println(resource);

    }


}
