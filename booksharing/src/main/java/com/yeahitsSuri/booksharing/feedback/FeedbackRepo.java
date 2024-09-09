package com.yeahitsSuri.booksharing.feedback;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
  @Query("""
            SELECT feedback
            FROM Feedback feedback
            WHERE feedback.book.id = :bookId 
            """)
  Page<Feedback> finaAllByBookId(int bookId, Pageable pageable);
}
