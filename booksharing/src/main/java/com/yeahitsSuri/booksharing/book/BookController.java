package com.yeahitsSuri.booksharing.book;

import com.yeahitsSuri.booksharing.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
  private final BookService service;

  @PostMapping
  public ResponseEntity<Integer> saveBook(
          @Valid @RequestBody BookRequest request,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.save(request, connectedUser));
  }

  @GetMapping("{book-id}")
  public ResponseEntity<BookResponse> findBookById(
          @PathVariable("book-id") Integer bookId
  ) {
    return ResponseEntity.ok(service.findById(bookId));
  }

  @GetMapping
  public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
          @RequestParam(name = "page", defaultValue = "0", required = false) int page,
          @RequestParam(name = "size", defaultValue = "10", required = false) int size,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
  }

  @GetMapping("/owner")
  public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
          @RequestParam(name = "page", defaultValue = "0", required = false) int page,
          @RequestParam(name = "size", defaultValue = "10", required = false) int size,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
  }

  @GetMapping("/borrowed")
  public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrwedBooks(
          @RequestParam(name = "page", defaultValue = "0", required = false) int page,
          @RequestParam(name = "size", defaultValue = "10", required = false) int size,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
  }

  @GetMapping("/returned")
  public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
          @RequestParam(name = "page", defaultValue = "0", required = false) int page,
          @RequestParam(name = "size", defaultValue = "10", required = false) int size,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
  }

  @PatchMapping("/shareable/{book_id}")
  public ResponseEntity<Integer> updShareableStatus(
          @PathVariable("book_id") Integer bookId,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.updShareableStatus(bookId, connectedUser));
  }

  @PatchMapping("/archived/{book_id}")
  public ResponseEntity<Integer> updArchivedStatus(
          @PathVariable("book_id") Integer bookId,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.updArchivedStatus(bookId, connectedUser));
  }

  @PostMapping("/borrow/{book_id}")
  public ResponseEntity<Integer> borrowBook(
          @PathVariable("book_id") Integer bookId,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
  }

  @PatchMapping("/borrow/return/{book_id}")
  public ResponseEntity<Integer> returnBook(
          @PathVariable("book_id") Integer bookId,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.returnBook(bookId, connectedUser));
  }

  @PatchMapping("/borrow/return/approve/{book_id}")
  public ResponseEntity<Integer> approveReturnBook(
          @PathVariable("book_id") Integer bookId,
          Authentication connectedUser
  ) {
    return ResponseEntity.ok(service.approveReturnBook(bookId, connectedUser));
  }

  @PostMapping(value = "/cover/{book_id}", consumes = "multipart/form-data")
  public ResponseEntity<?> uploadBookCover(
          @PathVariable("book_id") Integer bookId,
          @Parameter()
          @RequestPart("file") MultipartFile file,
          Authentication connectedUser
  ) {
    service.uploadBookCover(file, connectedUser, bookId);
    return ResponseEntity.accepted().build();
  }
}
