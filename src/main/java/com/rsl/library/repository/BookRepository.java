package com.rsl.library.repository;

import com.rsl.library.model.Book;

import java.util.HashMap;
import java.util.Map;

/** In-memory store of books, keyed by ISBN. */
public class BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }

    /** Returns the book, or {@code null} if no book has that ISBN. */
    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }
}
