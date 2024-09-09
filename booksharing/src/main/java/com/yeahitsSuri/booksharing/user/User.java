package com.yeahitsSuri.booksharing.user;

import com.yeahitsSuri.booksharing.book.Book;
import com.yeahitsSuri.booksharing.history.BookTransactionHistory;
import com.yeahitsSuri.booksharing.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "_user")
@EntityListeners(AuditingEntityListener.class)


public class User implements UserDetails, Principal {
  /**
   * Returns the name of this principal.
   *
   * @return the name of this principal.
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;

  @Column(unique = true)
  private String email;

  private String password;
  private boolean accountLocked;
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;
  // private List<Tokens> tokens;

  @OneToMany(mappedBy = "owner") // one user can have many books
  private List<Book> books;

  @OneToMany(mappedBy = "user")
  private List<BookTransactionHistory> histories;

  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModifiedDate;


  @Override
  public String getName() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // false means expired
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }
}
