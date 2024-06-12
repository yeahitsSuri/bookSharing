package com.yeahitsSuri.booksharing.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthticationRequest {

  @Email(message = "Invalid format for email!")
  @NotEmpty(message = "Email can not be empty!")
  @NotNull(message = "Email can not be null!")
  private String email;

  @Size(min = 8, message = "Password should be at least 8 characters long!")
  @NotEmpty(message = "Password can not be empty!")
  @NotNull(message = "Password can not be null!")
  private String password;
}
