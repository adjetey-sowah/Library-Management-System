package com.juls.firstapp.librarymanagementsystem.dao.interfaces;

import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;

import java.util.LinkedList;
import java.util.List;

public interface ResourceDAO {

    int insertLibraryResource(LibraryResource resource) throws Exception;

    boolean addLibraryResource(Book book) throws Exception;

    boolean addLibraryResource(Journal journal) throws Exception;

    boolean addLibraryResource(Media media) throws Exception;

    boolean deleteLibraryResource(Long id) throws Exception;

    boolean updateLibraryResource(LibraryResource resource) throws Exception;

    LinkedList<LibraryResource> findAllResource() throws Exception;

    LinkedList<Book> findAllBooks() throws Exception;

    LinkedList<Journal> findAllJournal() throws Exception;

    LinkedList<Media> findAllMedia() throws Exception;

    LibraryResource getResourceByTitle(String title) throws Exception;

    List<LibraryResource> searchResources(String criteria) throws Exception;





}
