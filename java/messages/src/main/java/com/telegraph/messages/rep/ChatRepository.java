package com.telegraph.messages.rep;



import com.telegraph.messages.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Channel, Long> {
    Channel findByName(String name);


}