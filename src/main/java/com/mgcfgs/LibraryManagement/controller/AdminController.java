package com.mgcfgs.LibraryManagement.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mgcfgs.LibraryManagement.model.Book;
import com.mgcfgs.LibraryManagement.model.RegisterUser;
import com.mgcfgs.LibraryManagement.services.BooksServices;
import com.mgcfgs.LibraryManagement.services.UserServices;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userService;

    @Autowired
    private BooksServices booksServices;

    // Predefined categories
    private final List<String> categories = Arrays.asList(
            "Fiction", "Non-Fiction", "Science", "Literature",
            "History", "Biography", "Technology", "Art");

    @GetMapping("/dashboard")
    public String Admin() {
        return "admin/dashboard";
    }

    @GetMapping("/books")
    public String Books(Model model) {
        // This method retrieves all books from the database and adds them to the model
        List<Book> books = booksServices.getAllBooks();
        model.addAttribute("books", books);
        return "admin/books";
    }

    @GetMapping("/members")
    public String Members(Model model) {
        // This method retrieves all users from the database and adds them to the model
        List<RegisterUser> users = userService.getAllUsers();
        model.addAttribute("members", users);
        return "admin/members";
    }

    @GetMapping("/issue")
    public String IssuedBooks() {
        return "admin/issue-books";
    }

    @GetMapping("/books/add-book")
    public String AddBook(Model model) {
        // This method adds a new book to the model
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categories);
        return "admin/addbook";
    }

    @PostMapping("/books/add-book")
    public String AddBook(@ModelAttribute("book") Book book) {
        // This method saves the new book to the database
        booksServices.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/return")
    public String ReturnBook() {
        return "admin/return-book";
    }

}
