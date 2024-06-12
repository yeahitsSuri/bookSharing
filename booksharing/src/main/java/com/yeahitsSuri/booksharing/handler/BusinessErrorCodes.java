package com.yeahitsSuri.booksharing.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum BusinessErrorCodes {

  NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code for implementation"),
  ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "Account locked"),
  INCORRECT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Incorrect password"),
  NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "Password does not match"),
  ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "Account disabled"),
  BAD_CREDENTIALS(304, HttpStatus.BAD_REQUEST, "Login and / or password is incorrect"),
  ;

  @Getter
  private final int code;

  @Getter
  private final String description;

  @Getter
  private final HttpStatus httpStatus;

  BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
    this.code = code;
    this.description = description;
    this.httpStatus = httpStatus;
  }
}
