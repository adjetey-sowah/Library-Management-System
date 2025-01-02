package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.LinkedList;

public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;

    public AuthenticationService () throws SQLException, ClassNotFoundException {
        this.userRepository = new UserRepository(new DatabaseConfig());
    }

    public boolean isAuthenticated(String email, String password) throws SQLException {
        User user = this.userRepository.getUserbyEmail(email);
        System.out.println(user);

            if(user.getRole().toString().equalsIgnoreCase("librarian")){

                return userRepository.passwordEncoder().matches(password,userRepository.getLibrarianPassword(user.getUserId()));
                }


        return false;
    }

}
