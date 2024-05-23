package com.yeahitsSuri.booksharing.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
  ACTIVATE_ACCOUNT("activate_account");
  private final String template;

  EmailTemplateName(String template) {
    this.template = template;
  }
}
