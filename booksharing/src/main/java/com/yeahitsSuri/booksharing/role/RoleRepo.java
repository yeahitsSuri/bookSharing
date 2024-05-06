package com.yeahitsSuri.booksharing.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String role);
}
