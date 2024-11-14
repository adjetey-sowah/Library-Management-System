package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;

public class User {

    private Long userId;
    private String name;
    private String email;
    private String phoneNum;
    private UserRole role;



    public User (String name, String email, String phoneNum,UserRole role){
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.role = role;
    }

    public User(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", Role='" + role.toString() + '\'' +
                '}';
    }
}
