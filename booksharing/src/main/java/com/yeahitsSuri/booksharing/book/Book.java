package com.yeahitsSuri.booksharing.book;

import com.yeahitsSuri.booksharing.common.BaseEntity;
import com.yeahitsSuri.booksharing.feedback.Feedback;
import com.yeahitsSuri.booksharing.history.BookTransactionHistory;
import com.yeahitsSuri.booksharing.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

  private String title;
  private String author;
  private String isbn;
  private String synopsis;
  private String bookCover;
  private boolean archived;
  private boolean shareable;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @OneToMany(mappedBy = "book")
  private List<Feedback> feedbacks;

  @OneToMany(mappedBy = "book")
  private List<BookTransactionHistory> histories;

  @Transient
  public double getRate() {
    if (feedbacks == null || feedbacks.isEmpty()) {
      return 0.0;
    }
    var rate = this.feedbacks.stream()
            .mapToDouble(Feedback::getNote)
            .average()
            .orElse(0.0);
    double roundedRate = Math.round(rate * 10.0) / 10.0;
    return roundedRate;
  }


}
