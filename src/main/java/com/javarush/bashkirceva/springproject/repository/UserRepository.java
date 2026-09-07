package com.javarush.bashkirceva.springproject.repository;

import com.javarush.bashkirceva.springproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByIdAndUsername(Long id, String username);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
