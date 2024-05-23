package com.yeahitsSuri.booksharing.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

  Optional<Token> findByToken(String token);
}
