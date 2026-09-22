package com.rsl.library.repository;

import com.rsl.library.model.Loan;
import com.rsl.library.model.LoanStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** In-memory store of loans, keyed by loan id. */
public class LoanRepository {

    private final Map<String, Loan> loans = new HashMap<>();

    public void save(Loan loan) {
        loans.put(loan.getId(), loan);
    }

    /** Returns the loan, or {@code null} if no loan has that id. */
    public Loan findById(String loanId) {
        return loans.get(loanId);
    }

    /** How many books a member currently has out (loans that are still ACTIVE). */
    public int countActiveByMember(String memberId) {
        int count = 0;
        for (Loan loan : loans.values()) {
            if (loan.getMemberId().equals(memberId) && loan.getStatus() == LoanStatus.ACTIVE) {
                count++;
            }
        }
        return count;
    }

    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }
}
