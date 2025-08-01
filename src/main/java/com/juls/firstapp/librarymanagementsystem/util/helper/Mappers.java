package com.juls.firstapp.librarymanagementsystem.util.helper;


import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.TransactionRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author  Julius Adjetey Sowah
 * This class contains all the helper methods that will be needed by the application to
 * run smoothly.
 */
public class Mappers {

    private static final Logger log = LoggerFactory.getLogger(Mappers.class);
    private ResourceRepository resourceRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;



    public Optional<User> mapToUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getLong("user_id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNum(resultSet.getString("phone"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));

        return Optional.of(user);
    }

    public Book mapToBooks(ResultSet resultSet) throws SQLException {


            Book book = new Book();
            book.setResourceId(resultSet.getLong("resource_id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setGenre(Genre.valueOf(resultSet.getString("genre")));
            book.setResourceStatus(ResourceStatus.valueOf(resultSet.getString("status")));
            book.setPublicationDate(Date.valueOf(resultSet.getString("publication_date")).toLocalDate());


            return book;
    }

    public Journal mapToJournal(ResultSet resultSet) throws SQLException{

        Journal journal = new Journal();

        journal.setResourceId(resultSet.getLong("journal_id"));
        journal.setTitle(resultSet.getString("title"));
        journal.setIssueNumber(resultSet.getString("issue_no"));
        journal.setFrequency(resultSet.getString("frequency"));
        journal.setResourceStatus(ResourceStatus.valueOf(resultSet.getString("status")));

        return journal;
    }

    public Media mapToMedia(ResultSet resultSet) throws SQLException{
        Media media = new Media();

        media.setResourceId(resultSet.getLong("media_id"));
        media.setTitle(resultSet.getString("title"));
        media.setFormat(MediaFormat.valueOf(resultSet.getString("format")));
        media.setResourceStatus(ResourceStatus.valueOf(resultSet.getString("status")));

        return media;
    }

    public TransactionDTO mapToTransaction(ResultSet resultSet) throws SQLException {
        TransactionDTO transactionDTO = new TransactionDTO();
        Date returnedDate = resultSet.getDate("returned_date");
        if (returnedDate != null){
            transactionDTO.setReturnedDate(returnedDate.toLocalDate());
        }
        else {
            transactionDTO.setReturnedDate(null);
        }
        transactionDTO.setTransactionId(resultSet.getLong("transaction_id"));
        transactionDTO.setResourceName(resultSet.getString("title"));
        transactionDTO.setPatronName(resultSet.getString("name"));
        transactionDTO.setPhone(resultSet.getString("phone"));
        transactionDTO.setBorrowedDate(Date.valueOf(resultSet.getDate("borrowed_date").toLocalDate()).toLocalDate());
        transactionDTO.setDueDate(Date.valueOf(resultSet.getDate("due_date").toLocalDate()).toLocalDate());
//        transactionDTO.setReturnedDate(resultSet.getDate("returned_date").toLocalDate());
        transactionDTO.setFine(resultSet.getDouble("fine"));

        return transactionDTO;
    }

    public ReservationDTO maptoReservation(Reservations reservations) throws Exception {
//        var user = userRepository.findUserById(reservations.getUserId());
        LocalDate dueDate = null;
//        if (user.isEmpty()){
//            log.info("No user found");
//        }
//        String username = user.get().getName();
//        String resourceName = resourceRepository.findResourceById(reservations.getResourceId()).getTitle();




        ReservationDTO reservation = new ReservationDTO();
        reservation.setUsername("Hello");
        reservation.setResourceName("World");
        reservation.setReservationDate(reservations.getReservationDate());
        reservation.setStatus(reservations.getStatus());
//        reservation.setExceptionDate(dueDate);

        return reservation;


    }

}
