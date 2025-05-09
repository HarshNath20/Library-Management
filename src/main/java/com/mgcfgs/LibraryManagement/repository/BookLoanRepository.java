package com.mgcfgs.LibraryManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mgcfgs.LibraryManagement.model.BookLoan;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findByMemberEmailAndStatus(String email, BookLoan.LoanStatus status);

    @Query("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId AND bl.status = 'ACTIVE'")
    List<BookLoan> findActiveLoansByBookId(@Param("bookId") Long bookId);

    @Query("SELECT COUNT(bl) FROM BookLoan bl WHERE bl.member.id = :memberId AND bl.status = 'ACTIVE'")
    int countActiveLoansByMember(@Param("memberId") Long memberId);
}