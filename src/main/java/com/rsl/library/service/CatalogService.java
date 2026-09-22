package com.rsl.library.service;

import com.rsl.library.model.Book;

/**
 * Looks after the physical availability of books: whether a copy is on the
 * shelf, and flipping that state when a book is borrowed or returned.
 */
public class CatalogService {

    public boolean isAvailable(Book book) {
        return book.isAvailable();
    }

    public void markBorrowed(Book book) {
        book.setAvailable(false);
    }

    public void markReturned(Book book) {
        book.setAvailable(true);
    }
}
