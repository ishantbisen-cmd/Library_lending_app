package com.rsl.library.model;

/**
 * A membership tier. Each tier sets how many books a member may hold at once
 * and how long they may keep each one.
 */
public enum MembershipTier {

    STANDARD(3, 14),
    PREMIUM(10, 28);

    private final int maxConcurrentLoans;
    private final int loanDays;

    MembershipTier(int maxConcurrentLoans, int loanDays) {
        this.maxConcurrentLoans = maxConcurrentLoans;
        this.loanDays = loanDays;
    }

    /** How many books this tier may have on loan at the same time. */
    public int getMaxConcurrentLoans() {
        return maxConcurrentLoans;
    }

    /** The loan period, in days, granted to this tier. */
    public int getLoanDays() {
        return loanDays;
    }
}
