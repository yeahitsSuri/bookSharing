package com.yeahitsSuri.booksharing.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookTransactionHistoryRepo extends JpaRepository<BookTransactionHistory, Integer> {
  @Query ("""
        SELECT  history
        FROM BookTransactionHistory history
        WHERE history.user.id = :userId
        """)
  Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, int userId);

  @Query ("""
        SELECT history
        FROM BookTransactionHistory history
        WHERE history.book.owner.id = :userId
        """)
  Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, int userId);

  @Query ("""
        SELECT
        (COUNT(*) > 0) AS isBorrowed
        FROM BookTransactionHistory history
        WHERE history.user.id = :userId
        AND history.book.id = :bookId
        AND history.returnApproved = false
        """)
  boolean isAlreadyBorrowedByUser(Integer bookId, int userId);

  @Query("""
    SELECT history
    FROM BookTransactionHistory history
    WHERE history.user.id = :userId
    AND history.book.id = :bookId
    AND history.returned = false
    AND history.returnApproved = false
""")
  Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, @Param("userId") Integer userId);

  @Query ("""
        SELECT history
        FROM BookTransactionHistory history
        WHERE history.book.owner.id = :userId
        AND history.book.id = :bookId
        AND history.returned = true
        AND history.returnApproved = false
        
        """)
  Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, int userId);
}
