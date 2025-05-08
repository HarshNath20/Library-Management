package com.mgcfgs.LibraryManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author name cannot exceed 100 characters")
    @Column(nullable = false)
    private String author;
    
    @NotBlank(message = "ISBN is required")
    // @ISBN(message = "Invalid ISBN format")
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Size(max = 100, message = "Publisher name cannot exceed 100 characters")
    private String publisher;
    
    @PastOrPresent(message = "Publication date must be in the past or present")
    private LocalDate publicationDate;
    
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;
    
    @NotBlank(message = "Language is required")
    @Size(max = 30, message = "Language cannot exceed 30 characters")
    private String language = "English";
    
    @PositiveOrZero(message = "Total copies cannot be negative")
    @Column(nullable = false)
    private int totalCopies = 1;
    
    @PositiveOrZero(message = "Available copies cannot be negative")
    @Column(nullable = false)
    private int availableCopies = 1;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    // @URL(message = "Invalid URL format for cover image")
    // private String coverImage;
    
    // Constructors
    public Book() {
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        // Ensure available copies never exceed total copies
        if (this.availableCopies > totalCopies) {
            this.availableCopies = totalCopies;
        }
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = Math.min(availableCopies, this.totalCopies);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public String getCoverImage() {
    //     return coverImage;
    // }

    // public void setCoverImage(String coverImage) {
    //     this.coverImage = coverImage;
    // }

    // Helper methods
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void incrementCopies(int amount) {
        this.totalCopies += amount;
        this.availableCopies += amount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}