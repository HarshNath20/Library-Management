package com.mgcfgs.LibraryManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.LibraryManagement.model.Book;
import com.mgcfgs.LibraryManagement.repository.BookRepository;

@Service
public class BooksServices {

    @Autowired
    BookRepository bookRepository;

    public void saveBook(Book book) {
        // This method saves the book to the database.
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
       return bookRepository.findAll();
        // This method retrieves all books from the database.
    }



}
