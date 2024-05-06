package com.yeahitsSuri.booksharing.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeahitsSuri.booksharing.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)

public class Role {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(unique = true) //role should be unique
  private String name;

  @ManyToMany(mappedBy = "roles")
  @JsonIgnore
  private List<User> users;


  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModifiedDate;


}
