package com.yeahitsSuri.booksharing.feedback;

import com.yeahitsSuri.booksharing.book.Book;
import com.yeahitsSuri.booksharing.book.BookRepo;
import com.yeahitsSuri.booksharing.common.PageResponse;
import com.yeahitsSuri.booksharing.exception.OperationNotPermittedException;
import com.yeahitsSuri.booksharing.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final BookRepo bookRepo;
  private final FeedbackMapper feedbackMapper;
  private final FeedbackRepo feedbackRepo;

  public Integer save(FeedbackRequest request, Authentication connectedUser) {
    Book book = bookRepo.findById(request.bookId())
            .orElseThrow(() -> new EntityNotFoundException("Book not found of id: " + request.bookId()));
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("No feedback allowed on a archive or un-shareable book.");
    }
    User user = ((User) connectedUser.getPrincipal());
    if (!Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You can not give feedback to your own book");
    }
    Feedback feedback = feedbackMapper.toFeedback(request);
    return feedbackRepo.save(feedback).getId();
  }


  public PageResponse<FeedbackResponse> findAllFeedbacksByBook(int bookId, int page, int size, Authentication connectedUser) {
    Pageable pageable = PageRequest.of(page, size);
    User user = ((User) connectedUser.getPrincipal());
    Page<Feedback> feedbacks = feedbackRepo.finaAllByBookId(bookId, pageable);
    List<FeedbackResponse> feedbackResponses = feedbacks.stream()
            .map(f -> feedbackMapper.toFeedbackResponse(f, user.getId()))
            .toList();
    return new PageResponse<>(
            feedbackResponses,
            feedbacks.getNumber(),
            feedbacks.getSize(),
            feedbacks.getTotalElements(),
            feedbacks.getTotalPages(),
            feedbacks.isFirst(),
            feedbacks.isLast()
    );
  }
}
