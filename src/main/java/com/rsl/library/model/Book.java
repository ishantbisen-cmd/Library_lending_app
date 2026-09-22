package com.rsl.library.model;

/** A book in the catalog. A single physical copy is modelled per ISBN. */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    /** True when the copy is on the shelf and can be borrowed. */
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" + isbn + ", " + title + (available ? ", available" : ", on loan") + "}";
    }
}
