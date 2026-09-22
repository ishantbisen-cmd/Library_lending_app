package com.rsl.library.service;

import com.rsl.library.model.Book;
import com.rsl.library.model.Loan;
import com.rsl.library.model.Member;
import com.rsl.library.repository.BookRepository;
import com.rsl.library.repository.LoanRepository;
import com.rsl.library.repository.MemberRepository;
import com.rsl.library.util.AppLogger;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * The main entry point for lending. It coordinates the other services to
 * borrow and return books.
 *
 * <p>Execution flow for {@link #borrow}:</p>
 * <pre>
 *   borrow
 *     -> MemberRepository.findById        (who is borrowing?)
 *     -> BookRepository.findByIsbn         (which book?)
 *     -> LoanRepository.countActiveByMember
 *     -> MembershipService.canBorrow       (active and under their limit?)
 *     -> CatalogService.isAvailable        (is a copy on the shelf?)
 *     -> new Loan(...) with a due date from the member's tier
 *     -> CatalogService.markBorrowed
 *     -> LoanRepository.save
 * </pre>
 */
public class LoanService {

    private static final Logger log = AppLogger.get(LoanService.class);

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final CatalogService catalogService;
    private final MembershipService membershipService;
    private final FineService fineService;

    private int loanSequence = 0;

    public LoanService(BookRepository bookRepository,
                       MemberRepository memberRepository,
                       LoanRepository loanRepository,
                       CatalogService catalogService,
                       MembershipService membershipService,
                       FineService fineService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.catalogService = catalogService;
        this.membershipService = membershipService;
        this.fineService = fineService;
    }

    /**
     * Borrow a book for a member.
     *
     * @param memberId the borrowing member
     * @param isbn     the book to borrow
     * @param today    the date of the borrow (drives the due date)
     * @return the created loan
     * @throws IllegalArgumentException if the member or book is unknown
     * @throws IllegalStateException    if the member cannot borrow or the book is out
     */
    public Loan borrow(String memberId, String isbn, LocalDate today) {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Unknown member: " + memberId);
        }
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Unknown book: " + isbn);
        }

        int active = loanRepository.countActiveByMember(memberId);
        if (!membershipService.canBorrow(member, active)) {
            throw new IllegalStateException(
                    member.getName() + " cannot borrow (inactive, or at their loan limit)");
        }
        if (!catalogService.isAvailable(book)) {
            throw new IllegalStateException("Book is not available: " + book.getTitle());
        }

        LocalDate dueOn = today.plusDays(member.getTier().getLoanDays());
        Loan loan = new Loan(nextLoanId(), isbn, memberId, today, dueOn);

        catalogService.markBorrowed(book);
        loanRepository.save(loan);

        log.info(member.getName() + " borrowed '" + book.getTitle() + "', due " + dueOn);
        return loan;
    }

    /**
     * Return a borrowed book and calculate any late fine.
     *
     * @param loanId     the loan being returned
     * @param returnDate the date the book is handed back
     * @return the fine charged, in cents (0 if on time)
     * @throws IllegalArgumentException if the loan is unknown
     */
    public int returnBook(String loanId, LocalDate returnDate) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Unknown loan: " + loanId);
        }
        Book book = bookRepository.findByIsbn(loan.getIsbn());

        int fine = fineService.fineCents(loan, returnDate);
        loan.markReturned(returnDate);
        catalogService.markReturned(book);
        loanRepository.save(loan);

        log.info("Returned loan " + loanId + " on " + returnDate + ", fine = " + fine + "c");
        return fine;
    }

    private String nextLoanId() {
        return "L-" + (++loanSequence);
    }
}
