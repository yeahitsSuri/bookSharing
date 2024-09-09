package com.yeahitsSuri.booksharing.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
  private List<T> contet;
  private int number;
  private int size;
  private long totalElements;
  private int totalPages;
  private boolean first;
  private boolean last;

}
