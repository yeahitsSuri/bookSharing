package com.yeahitsSuri.booksharing.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

  @Query("""
        SELECT book
        FROM Book book
        WHERE book.archived = false
        AND book.shareable = true
        AND book.owner.id != :userId
""")
  Page<Book> findAllDisplayableBooks(Pageable pageable, int userId);
}