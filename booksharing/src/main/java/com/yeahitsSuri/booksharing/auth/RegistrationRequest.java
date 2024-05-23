package com.yeahitsSuri.booksharing.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {
  @NotEmpty(message = "FirstName needed!")
  @NotBlank(message = "FirstName can not be empty!")
  private String firstName;
  @NotEmpty(message = "LastName needed!")
  @NotBlank(message = "LastName can not be empty!")
  private String lastName;
  @Email(message = "Invalid format for email!")
  @NotEmpty(message = "Email needed!")
  @NotBlank(message = "Email can not be empty!")
  private String email;
  @Size(min = 8, message = "Password should be at least 8 characters long!")
  @NotEmpty(message = "Email needed!")
  @NotBlank(message = "Email can not be empty!")
  private String password;
}
