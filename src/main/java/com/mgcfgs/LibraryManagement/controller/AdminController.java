package com.mgcfgs.LibraryManagement.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mgcfgs.LibraryManagement.model.Book;
import com.mgcfgs.LibraryManagement.model.BookLoan;
import com.mgcfgs.LibraryManagement.model.RegisterUser;
import com.mgcfgs.LibraryManagement.services.BookLoanService;
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

    @Autowired
    private BookLoanService bookLoanService;

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
    public String IssuedBooks(Model model) {
        // This method retrieves all issued books from the database and adds them to the
        // model
        List<Book> issuedBooks = booksServices.getAllBooks();
        List<RegisterUser> users = userService.getAllUsers();
        List<BookLoan> loans = bookLoanService.getAllLoans();
        model.addAttribute("Books", issuedBooks);
        model.addAttribute("members", users);
        model.addAttribute("loans", loans);
        return "admin/issue-books";
    }

    @PostMapping("/issue")
    public String issueBook(
            @RequestParam Long memberId,
            @RequestParam Long bookId,
            RedirectAttributes redirectAttributes) {

        RegisterUser user = userService.getUserById(memberId);
        Book book = booksServices.getBookById(bookId);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/admin/issue";
        }

        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Book not found.");
            return "redirect:/admin/issue";
        }

        if (!book.isAvailable()) {
            redirectAttributes.addFlashAttribute("error", "Book is not available.");
            return "redirect:/admin/issue";
        }

        BookLoan loan = new BookLoan(book, user, LocalDate.now().plusDays(14));
        // loan.setBook(book);
        // loan.setMember(user);
        loan.setIssueDate(LocalDate.now());
        // loan.setDueDate(LocalDate.now().plusDays(14)); // or any due date policy
        // loan.setStatus(BookLoan.LoanStatus.ACTIVE);

        bookLoanService.saveLoan(loan);

        // Update book's available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookLoanService.saveBook(book);

        redirectAttributes.addFlashAttribute("success", "Book issued successfully to " + user.getName());
        return "redirect:/admin/issue";
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
        book.setAvailableCopies(book.getTotalCopies());
        // This method saves the new book to the database
        booksServices.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/return")
    public String ReturnBook() {
        return "admin/return-book";
    }

}
