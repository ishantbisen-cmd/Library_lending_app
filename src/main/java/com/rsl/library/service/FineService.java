package com.rsl.library.service;

import com.rsl.library.model.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Works out the late fine for a returned book. Fines are charged per day the
 * book is overdue; a book returned on or before its due date is free.
 */
public class FineService {

    /** Fine charged for each day a book is overdue, in cents. */
    public static final int FINE_PER_DAY_CENTS = 25;

    /**
     * The fine, in cents, for returning a loan on the given date.
     *
     * @param loan       the loan being returned
     * @param returnDate the date the book is handed back
     * @return the fine in cents (0 if returned on time)
     */
    public int fineCents(Loan loan, LocalDate returnDate) {
        if (!returnDate.isAfter(loan.getDueOn())) {
            return 0;
        }
        long daysLate = ChronoUnit.DAYS.between(loan.getDueOn(), returnDate);
        return (int) daysLate * FINE_PER_DAY_CENTS;
    }
}
