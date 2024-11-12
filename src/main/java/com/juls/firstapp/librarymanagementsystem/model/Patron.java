package com.juls.firstapp.librarymanagementsystem.model;

import java.util.LinkedList;
import java.util.Random;

public class Patron {

    private Long patronId;
    private String name;
    private MembershipType membershipType;
    private String email;
    private String phone;
    private LinkedList<LibraryResource> borrowedResources;
    private LibraryResource resource;

    public Patron(String name, MembershipType membershipType,
                  String email, String phone) {

        this.setPatronId();
        this.name = name;
        this.membershipType = membershipType;
        this.email = email;
        this.phone = phone;
        this.borrowedResources = new LinkedList<>();
    }

    public Patron(){
        this.setPatronId();;

    }

    protected Long generatePatronId(){
        return new Random(1000).nextLong();
    }

    public void setPatronId(){
        this.patronId = generatePatronId();
    }

    public Long getPatronId(){
        return patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LinkedList<LibraryResource> getBorrowedResources() {
        return borrowedResources;
    }

    public void borrowResource(LibraryResource resource){
        borrowedResources.add(resource);
    }

    public void returnResource(LibraryResource resource){
        borrowedResources.remove(resource);
    }

    public void renewResource(LibraryResource resource){
        // Implement a function to check the status of the resource.
        // If the resource is not reserved? Extend the dueDate
    }

}
