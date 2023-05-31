package com.telegraph.authentication;
import com.telegraph.authentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}