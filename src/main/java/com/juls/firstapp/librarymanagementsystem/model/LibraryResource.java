package com.juls.firstapp.librarymanagementsystem.model;

public abstract class LibraryResource {
    protected static int counter =  1000;
    protected String resourceId;
    protected String title;
    protected boolean isBorrowed;
    protected boolean isReserved;
    protected boolean isAvailable;

    public LibraryResource(String title){
        this.title = title;
        this.resourceId = generateResourceId();
        this.isBorrowed = false;
        this.isReserved = false;
        this.isAvailable = true;
    }

    public String generateResourceId(){
        return getResourcePrefix() + counter;
    }

    public abstract String getResourcePrefix();



    public String getTitle(){
        return title;
    }

    public String getResourceId(){
        return  resourceId;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public boolean isReserved(){
        return isReserved;
    }

    public void reserveResource(String resourceId){
        this.isAvailable = false;
        this.isReserved = true;
    }

    public void borrowResource(String resourceId, Long patronId){
        this.isAvailable = false;
        this.isBorrowed = true;
    }
}
