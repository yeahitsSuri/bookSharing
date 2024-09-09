package com.yeahitsSuri.booksharing.feedback;

import com.yeahitsSuri.booksharing.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
  public Feedback toFeedback(FeedbackRequest request) {
    return Feedback.builder()
            .note(request.note())
            .comment(request.comment())
            .book(Book.builder()
                    .id(request.bookId())
                    .archived(false)
                    .shareable(false) // lombok: primitive type should be always assigned a value
                    .build())
            .build();
  }

  public FeedbackResponse toFeedbackResponse(Feedback feedback, int id) {
    return FeedbackResponse.builder()
            .note(feedback.getNote())
            .comment(feedback.getComment())
            .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
            .build();
  }
}
