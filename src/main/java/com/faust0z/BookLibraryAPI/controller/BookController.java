package com.faust0z.BookLibraryAPI.controller;

import com.faust0z.BookLibraryAPI.dto.BookDTO;
import com.faust0z.BookLibraryAPI.dto.CreateBookDTO;
import com.faust0z.BookLibraryAPI.dto.UpdateBookDTO;
import com.faust0z.BookLibraryAPI.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID bookId) {
        BookDTO book = bookService.getBookbyId(bookId);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody CreateBookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("bookId") UUID bookId, @RequestBody UpdateBookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(bookId, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }
}