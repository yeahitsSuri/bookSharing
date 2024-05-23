package com.yeahitsSuri.booksharing.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthticationRequest {
  @Email(message = "Invalid format for email!")
  @NotEmpty(message = "Email needed!")
  @NotBlank(message = "Email can not be empty!")
  private String email;
  @Size(min = 8, message = "Password should be at least 8 characters long!")
  @NotEmpty(message = "Email needed!")
  @NotBlank(message = "Email can not be empty!")
  private String password;
}
