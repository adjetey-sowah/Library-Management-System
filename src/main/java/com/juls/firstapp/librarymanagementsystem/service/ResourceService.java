package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.util.LinkedList;
import java.util.Optional;

public class ResourceService implements ResourceServiceImpl {



    @Override
    public void addResource(LibraryResource resource) {

    }

    @Override
    public void removeResource(LibraryResource resource) {

    }

    @Override
    public LibraryResource getResourceStatus(LibraryResource resource) {
        return null;
    }

    @Override
    public LinkedList<LibraryResource> getAllResources() {
        return null;
    }

    @Override
    public Optional<LibraryResource> searchLibraryResource(String... args) {
        return Optional.empty();
    }
}
