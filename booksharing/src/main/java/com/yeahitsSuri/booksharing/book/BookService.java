package com.yeahitsSuri.booksharing.book;

import com.yeahitsSuri.booksharing.common.PageResponse;
import com.yeahitsSuri.booksharing.exception.OperationNotPermittedException;
import com.yeahitsSuri.booksharing.file.FileStorageService;
import com.yeahitsSuri.booksharing.history.BookTransactionHistory;
import com.yeahitsSuri.booksharing.history.BookTransactionHistoryRepo;
import com.yeahitsSuri.booksharing.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepo bookRepo;
  private final BookTransactionHistoryRepo bookTransactionHistoryRepo;
  private final BookMapper bookMapper;
  private final FileStorageService fileStorageService;

  public Integer save(BookRequest request, Authentication connectedUser) {
    User user = ((User) connectedUser.getPrincipal());
    Book book = bookMapper.toBook(request);
    book.setOwner(user);
    return bookRepo.save(book).getId();
  }

  public BookResponse findById(Integer bookId) {
    return bookRepo.findById(bookId)
            .map(bookMapper::toBookResponse)
            .orElseThrow(() -> new EntityNotFoundException("Book not found of id: " + bookId));
  }


  public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
    User user = ((User) connectedUser.getPrincipal());
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<Book> books = bookRepo.findAllDisplayableBooks(pageable, user.getId());
    List<BookResponse> bookResponses = books.stream()
            .map(bookMapper::toBookResponse)
            .toList();
    return new PageResponse<>(
            bookResponses,
            books.getNumber(),
            books.getSize(),
            books.getTotalElements(),
            books.getTotalPages(),
            books.isFirst(),
            books.isLast()
    );
  }

  public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
    User user = ((User) connectedUser.getPrincipal());
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<Book> books = bookRepo.findAll(BookSpecification.withOwnerId(user.getId()), pageable);

    List<BookResponse> bookResponses = books.stream()
            .map(bookMapper::toBookResponse)
            .toList();
    return new PageResponse<>(
            bookResponses,
            books.getNumber(),
            books.getSize(),
            books.getTotalElements(),
            books.getTotalPages(),
            books.isFirst(),
            books.isLast()
    );
  }

  public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
    User user = ((User) connectedUser.getPrincipal());
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepo.findAllBorrowedBooks(pageable, user.getId());
    List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
            .map(bookMapper::toBorrowedBookResponse)
            .toList();
    return new PageResponse<>(
            bookResponses,
            allBorrowedBooks.getNumber(),
            allBorrowedBooks.getSize(),
            allBorrowedBooks.getTotalElements(),
            allBorrowedBooks.getTotalPages(),
            allBorrowedBooks.isFirst(),
            allBorrowedBooks.isLast()
    );
  }

  public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
    User user = ((User) connectedUser.getPrincipal());
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepo.findAllReturnedBooks(pageable, user.getId());
    List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
            .map(bookMapper::toBorrowedBookResponse)
            .toList();
    return new PageResponse<>(
            bookResponses,
            allBorrowedBooks.getNumber(),
            allBorrowedBooks.getSize(),
            allBorrowedBooks.getTotalElements(),
            allBorrowedBooks.getTotalPages(),
            allBorrowedBooks.isFirst(),
            allBorrowedBooks.isLast()
    );
  }

  public Integer updShareableStatus(Integer bookId, Authentication connectedUser) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(
                    () -> new EntityNotFoundException("Book not found of id: " + bookId)
            );
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You are not the owner, so no shareable status can be updated");
    }
    book.setShareable(!book.isShareable());
    bookRepo.save(book);
    return bookId;
  }

  public Integer updArchivedStatus(Integer bookId, Authentication connectedUser) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(
                    () -> new EntityNotFoundException("Book not found of id: " + bookId)
            );
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You are not the owner, so no archived status can be updated");
    }
    book.setArchived(!book.isArchived());
    bookRepo.save(book);
    return bookId;
  }

  public Integer borrowBook(Integer bookId, Authentication connectedUser) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(()-> new EntityNotFoundException("Book not found of id: " + bookId));
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("The requested book cannot be borrowed, status is Archived or not shareable");
    }
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You can not borrow you own book");
    }
    final boolean isBorrowed = bookTransactionHistoryRepo.isAlreadyBorrowedByUser(bookId, user.getId());
    if (isBorrowed) {
      throw new OperationNotPermittedException("The requested book is already borrowed by someone else");
    }
    BookTransactionHistory history = BookTransactionHistory.builder()
            .user(user)
            .book(book)
            .returned(false)
            .returnApproved(false)
            .build();
    return bookTransactionHistoryRepo.save(history).getId();
  }

  public Integer returnBook(Integer bookId, Authentication connectedUser) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found of id: " + bookId));
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("The requested book cannot be borrowed, status is Archived or not shareable");
    }
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You can not borrow or return your own book");
    }
    BookTransactionHistory history = bookTransactionHistoryRepo.findByBookIdAndUserId(bookId, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("You did not borrow this book"));;
    history.setReturned(true);
    return bookTransactionHistoryRepo.save(history).getId();
  }

  public Integer approveReturnBook(Integer bookId, Authentication connectedUser) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found of id: " + bookId));
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("The requested book cannot be borrowed, status is Archived or not shareable");
    }
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You can not borrow or return you own book");
    }
    BookTransactionHistory history = bookTransactionHistoryRepo.findByBookIdAndOwnerId(bookId, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("The book has not been returned yet."));;
    history.setReturnApproved(true);
    return bookTransactionHistoryRepo.save(history).getId();
  }

  public void uploadBookCover(MultipartFile file, Authentication connectedUser, Integer bookId) {
    Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found of id: " + bookId));
    User user = ((User) connectedUser.getPrincipal());
    var bookCover = fileStorageService.saveFile(file, user.getId());
    book.setBookCover(bookCover);
    bookRepo.save(book);
  }
}
