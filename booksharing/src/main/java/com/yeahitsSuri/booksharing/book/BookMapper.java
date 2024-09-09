package com.yeahitsSuri.booksharing.book;

import com.yeahitsSuri.booksharing.file.FileUtils;
import com.yeahitsSuri.booksharing.history.BookTransactionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {
  public Book toBook(BookRequest request) {
    return Book.builder()
            .id(request.id())
            .title(request.title())
            .author(request.authorName())
            .synopsis(request.synopsis())
            .archived(false)
            .shareable(request.shareable())
            .build();
  }

  public BookResponse toBookResponse(Book book) {
    return BookResponse.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .synopsis(book.getSynopsis())
            .rate(book.getRate())
            .archived(book.isArchived())
            .shareable(book.isShareable())
            .owner(book.getOwner().getFullName())
            .cover(FileUtils.readFileFromLocation(book.getBookCover()))
            .build();
  }

  public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
    return BorrowedBookResponse.builder()
            .id(history.getBook().getId())
            .title(history.getBook().getTitle())
            .author(history.getBook().getAuthor())
            .isbn(history.getBook().getIsbn())
            .rate(history.getBook().getRate())
            .returned(history.isReturned())
            .returnApproved(history.isReturnApproved())
            .build();
  }
}
