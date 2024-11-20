package com.juls.firstapp.librarymanagementsystem.util.helper;


import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author  Julius Adjetey Sowah
 * This class contains all the helper methods that will be needed by the application to
 * run smoothly.
 */
public class Mappers {

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
        transactionDTO.setTransactionId(resultSet.getLong("transaction_id"));
        transactionDTO.setResourceName(resultSet.getString("title"));
        transactionDTO.setPatronName(resultSet.getString("name"));
        transactionDTO.setPhone(resultSet.getString("phone"));
        transactionDTO.setBorrowedDate(Date.valueOf(resultSet.getDate("borrowed_date").toLocalDate()).toLocalDate());
        transactionDTO.setDueDate(Date.valueOf(resultSet.getDate("borrowed_date").toLocalDate()).toLocalDate());
//        transactionDTO.setReturnedDate(resultSet.getDate("returned_date").toLocalDate());
        transactionDTO.setFine(resultSet.getDouble("fine"));

        return transactionDTO;
    }

}
