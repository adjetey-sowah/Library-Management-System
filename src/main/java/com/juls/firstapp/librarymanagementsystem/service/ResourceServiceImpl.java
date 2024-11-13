package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.model.LibraryResource;

import java.util.LinkedList;
import java.util.Optional;

public interface ResourceServiceImpl {

    public void addResource(LibraryResource resource);

    public void removeResource(LibraryResource resource);

    public LibraryResource getResourceStatus(LibraryResource resource);

    public LinkedList<LibraryResource> getAllResources();

    public Optional<LibraryResource> searchLibraryResource(String...args);
}
