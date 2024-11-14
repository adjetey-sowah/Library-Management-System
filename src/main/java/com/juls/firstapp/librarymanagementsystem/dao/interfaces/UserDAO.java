package com.juls.firstapp.librarymanagementsystem.dao.interfaces;

import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;

import java.sql.SQLException;
import java.util.LinkedList;

public interface UserDAO {

    boolean updatePatron(Patron patron);

    public int insertUser(User user) throws SQLException;

    public void insertPatron(int userId, MembershipType membershipType) throws SQLException;

    public void insertLibrarian(int userId, String password) throws SQLException;

    public LinkedList<User> getAllUsers() throws SQLException;

    public boolean updateLibrarian(Librarian librarian) throws SQLException;

    public boolean deleteUser(Long id) throws SQLException;

}

