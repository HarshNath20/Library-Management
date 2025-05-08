package com.mgcfgs.LibraryManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mgcfgs.LibraryManagement.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {



}
