package com.rsl.library.repository;

import com.rsl.library.model.Member;

import java.util.HashMap;
import java.util.Map;

/** In-memory store of members, keyed by member id. */
public class MemberRepository {

    private final Map<String, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getId(), member);
    }

    /** Returns the member, or {@code null} if no member has that id. */
    public Member findById(String memberId) {
        return members.get(memberId);
    }
}
