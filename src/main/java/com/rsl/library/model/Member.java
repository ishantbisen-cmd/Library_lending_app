package com.rsl.library.model;

/** A library member who can borrow books. */
public class Member {

    private final String id;
    private final String name;
    private final MembershipTier tier;
    private boolean active;

    public Member(String id, String name, MembershipTier tier, boolean active) {
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MembershipTier getTier() {
        return tier;
    }

    /** Only active members may borrow. A lapsed membership is inactive. */
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Member{" + id + ", " + name + ", " + tier + (active ? "" : ", INACTIVE") + "}";
    }
}
