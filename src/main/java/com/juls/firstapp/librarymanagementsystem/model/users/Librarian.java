package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;

public class Librarian extends User {

    private String password;

    public Librarian(String name, String email, String phoneNum, String password){
        super(name, email,phoneNum, UserRole.LIBRARIAN);
        this.password = password;
    }

    public Librarian(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
