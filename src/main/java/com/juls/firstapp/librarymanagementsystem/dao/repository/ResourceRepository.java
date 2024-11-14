package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.ResourceDAO;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;

import java.sql.Connection;
import java.sql.SQLException;

public class ResourceRepository implements ResourceDAO {

    private final Connection connection;

    public ResourceRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
    }

    public int addLibraryResource(Book book){
        return -1;
    }

    public int addLibraryResource(Media media){
        return -1;
    }

    public int addLibraryResource(Journal journal){
        return -1;
    }





}
