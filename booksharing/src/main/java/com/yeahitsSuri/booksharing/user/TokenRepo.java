package com.yeahitsSuri.booksharing.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

  Optional<Token> findByTokenId(Integer integer);
}
