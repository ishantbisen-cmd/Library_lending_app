package com.rsl.library;

import com.rsl.library.model.Loan;
import com.rsl.library.service.FineService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FineServiceTest {

    private final FineService fines = new FineService();

    private Loan loanDue(LocalDate dueOn) {
        return new Loan("L-1", "isbn", "M-1", dueOn.minusDays(14), dueOn);
    }

    @Test
    void noFineWhenReturnedOnTime() {
        LocalDate due = LocalDate.of(2026, 8, 15);
        assertEquals(0, fines.fineCents(loanDue(due), due));
    }

    @Test
    void noFineWhenReturnedEarly() {
        LocalDate due = LocalDate.of(2026, 8, 15);
        assertEquals(0, fines.fineCents(loanDue(due), due.minusDays(3)));
    }

    @Test
    void chargesPerDayWhenLate() {
        LocalDate due = LocalDate.of(2026, 8, 15);
        // 4 days late * 25c
        assertEquals(100, fines.fineCents(loanDue(due), due.plusDays(4)));
    }
}
