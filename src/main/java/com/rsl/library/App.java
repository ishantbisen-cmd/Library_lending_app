package com.rsl.library;

import com.rsl.library.model.Book;
import com.rsl.library.model.Loan;
import com.rsl.library.model.Member;
import com.rsl.library.model.MembershipTier;
import com.rsl.library.repository.BookRepository;
import com.rsl.library.repository.LoanRepository;
import com.rsl.library.repository.MemberRepository;
import com.rsl.library.service.CatalogService;
import com.rsl.library.service.FineService;
import com.rsl.library.service.LoanService;
import com.rsl.library.service.MembershipService;
import com.rsl.library.util.AppLogger;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Runs a few lending scenarios end to end so the application produces real
 * console output and log lines in <code>logs/app.log</code>.
 */
public class App {

    private static final Logger log = AppLogger.get(App.class);

    public static void main(String[] args) {
        log.info("=== Library Lending starting ===");

        // ---- Wire the application together ----
        BookRepository books = new BookRepository();
        MemberRepository members = new MemberRepository();
        LoanRepository loans = new LoanRepository();

        CatalogService catalog = new CatalogService();
        MembershipService membership = new MembershipService();
        FineService fines = new FineService();
        LoanService lending = new LoanService(books, members, loans, catalog, membership, fines);

        // ---- Seed reference data ----
        books.save(new Book("978-0132350884", "Clean Code", "Robert C. Martin"));
        books.save(new Book("978-0201633610", "Design Patterns", "Erich Gamma et al."));
        books.save(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));

        members.save(new Member("M-1", "Alice", MembershipTier.STANDARD, true));
        members.save(new Member("M-2", "Bob", MembershipTier.PREMIUM, true));
        members.save(new Member("M-3", "Carol", MembershipTier.STANDARD, false)); // lapsed

        LocalDate today = LocalDate.of(2026, 8, 1);

        // Scenario 1: a straightforward borrow.
        Loan loan = lending.borrow("M-1", "978-0132350884", today);
        System.out.println("Borrowed: " + loan);

        // Scenario 2: return the book two days late and see the fine.
        int fine = lending.returnBook(loan.getId(), loan.getDueOn().plusDays(2));
        System.out.println("Returned late, fine = " + fine + "c");

        // Scenario 3: return a book on time (no fine).
        Loan loan2 = lending.borrow("M-2", "978-0201633610", today);
        int fine2 = lending.returnBook(loan2.getId(), loan2.getDueOn());
        System.out.println("Returned on time, fine = " + fine2 + "c");

        // Scenario 4: a lapsed member is refused.
        try {
            lending.borrow("M-3", "978-0134685991", today);
        } catch (RuntimeException e) {
            System.out.println("Refused as expected: " + e.getMessage());
        }

        log.info("=== Library Lending finished ===");
    }
}
