package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.util.LinkedList;

public class Patron extends User {


    private MembershipType membershipType;
    private LinkedList<LibraryResource> borrowedResources;
    private LibraryResource resource;

    public Patron(String name, MembershipType membershipType,
                  String email, String phone) {

        this.membershipType = membershipType;
        this.borrowedResources = new LinkedList<>();
    }

    public Patron(){

    }

    public void setMembershipType(MembershipType type){
        this.membershipType = type;
    }

    public MembershipType getMembershipType(){
        return this.membershipType;
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
