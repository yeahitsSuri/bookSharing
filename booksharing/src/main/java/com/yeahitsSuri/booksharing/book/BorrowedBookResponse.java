package com.yeahitsSuri.booksharing.book;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowedBookResponse {
  private Integer id;
  private String title;
  private String author;
  private String isbn;
  private double rate; //average ratings
  private boolean returned;
  private boolean returnApproved;
}
