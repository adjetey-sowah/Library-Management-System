package com.juls.firstapp.librarymanagementsystem.service;


import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import com.juls.firstapp.librarymanagementsystem.util.exception.UserNotAddedException;

public class UserService {

    private  UserRepository userRepository;

    public User addLibrarian(Librarian librarian){
        try {
        int librarianId = userRepository.insertUser(librarian);
        userRepository.insertLibrarian(librarian);

        return librarian;
        } catch (RuntimeException e) {
            throw new UserNotAddedException("User was not added."+e.getMessage());
        }

    }

    public User addPatron(Patron patron){
        try {
        int user_id = userRepository.insertUser(patron);
        userRepository.insertPatron(patron);
        return patron;
        }
        catch (Exception e){
            throw new UserNotAddedException("Patron can not be added.\n"+e.getMessage());
        }
    }

    public void deletUser(User user){
        try{
        Long userId = user.getUserId();
        userRepository.deleteUser(userId);
        }
        catch (Exception e){
            throw new RuntimeException("Could not delete user.\n"+e.getMessage());
        }
    }

    public User updateLibrarian(Librarian librarian){
        return null;
    }

    public User updatePatron(Patron patron){
       return null;
    }



}
