package org.lld.personalexpensetracker.repository;

import org.lld.personalexpensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsById(Long id);
}