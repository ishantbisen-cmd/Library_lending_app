package com.rsl.library.model;

import java.time.LocalDate;

/**
 * A record that a member has borrowed a book. It carries the date the book was
 * borrowed, the date it is due back, and (once returned) the date it came back.
 */
public class Loan {

    private final String id;
    private final String isbn;
    private final String memberId;
    private final LocalDate borrowedOn;
    private final LocalDate dueOn;

    private LocalDate returnedOn;
    private LoanStatus status;

    public Loan(String id, String isbn, String memberId, LocalDate borrowedOn, LocalDate dueOn) {
        this.id = id;
        this.isbn = isbn;
        this.memberId = memberId;
        this.borrowedOn = borrowedOn;
        this.dueOn = dueOn;
        this.status = LoanStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public LoanStatus getStatus() {
        return status;
    }

    /** Mark this loan returned on the given date. */
    public void markReturned(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
        this.status = LoanStatus.RETURNED;
    }

    @Override
    public String toString() {
        return "Loan{" + id + ", " + isbn + ", member=" + memberId
                + ", due=" + dueOn + ", " + status + "}";
    }
}
