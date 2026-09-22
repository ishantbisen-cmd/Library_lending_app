package com.rsl.library;

import com.rsl.library.model.Book;
import com.rsl.library.model.Loan;
import com.rsl.library.model.LoanStatus;
import com.rsl.library.model.Member;
import com.rsl.library.model.MembershipTier;
import com.rsl.library.repository.BookRepository;
import com.rsl.library.repository.LoanRepository;
import com.rsl.library.repository.MemberRepository;
import com.rsl.library.service.CatalogService;
import com.rsl.library.service.FineService;
import com.rsl.library.service.LoanService;
import com.rsl.library.service.MembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoanServiceTest {

    private BookRepository books;
    private LoanService lending;
    private final LocalDate today = LocalDate.of(2026, 8, 1);

    @BeforeEach
    void setUp() {
        books = new BookRepository();
        MemberRepository members = new MemberRepository();
        LoanRepository loans = new LoanRepository();

        books.save(new Book("isbn-1", "Clean Code", "Martin"));
        members.save(new Member("M-1", "Alice", MembershipTier.STANDARD, true));
        members.save(new Member("M-3", "Carol", MembershipTier.STANDARD, false));

        lending = new LoanService(books, members, loans,
                new CatalogService(), new MembershipService(), new FineService());
    }

    @Test
    void borrowMarksBookUnavailableAndSetsDueDate() {
        Loan loan = lending.borrow("M-1", "isbn-1", today);

        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        assertEquals(today.plusDays(14), loan.getDueOn());
        assertFalse(books.findByIsbn("isbn-1").isAvailable());
    }

    @Test
    void lapsedMemberCannotBorrow() {
        assertThrows(IllegalStateException.class,
                () -> lending.borrow("M-3", "isbn-1", today));
    }

    @Test
    void unknownBookIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> lending.borrow("M-1", "no-such-isbn", today));
    }
}
