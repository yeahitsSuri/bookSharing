package com.yeahitsSuri.booksharing.book;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
  private Integer id;
  private String title;
  private String author;
  private String isbn;
  private String synopsis;
  private String owner;
  private byte[] cover;
  private double rate; //average ratings
  private boolean archived;
  private boolean shareable;
}
