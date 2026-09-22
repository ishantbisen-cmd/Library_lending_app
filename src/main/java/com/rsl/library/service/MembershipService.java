package com.rsl.library.service;

import com.rsl.library.model.Member;

/**
 * Answers membership questions used when a member tries to borrow: is the
 * membership active, and are they under their concurrent-loan limit?
 */
public class MembershipService {

    /** A member may borrow only while their membership is active. */
    public boolean isActive(Member member) {
        return member.isActive();
    }

    /**
     * Whether the member may take out one more book right now.
     *
     * @param member            the member borrowing
     * @param currentLoanCount  how many books they already have on loan
     */
    public boolean canBorrow(Member member, int currentLoanCount) {
        if (!isActive(member)) {
            return false;
        }
        return currentLoanCount < member.getTier().getMaxConcurrentLoans();
    }
}
