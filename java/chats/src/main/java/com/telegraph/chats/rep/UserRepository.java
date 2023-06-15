package com.telegraph.chats.rep;

import com.telegraph.chats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);

}