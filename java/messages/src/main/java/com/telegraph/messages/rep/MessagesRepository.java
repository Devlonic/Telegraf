package com.telegraph.messages.rep;

import com.telegraph.messages.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MessagesRepository extends JpaRepository<User, Long> {

}