package com.telegraph.chats.rep;


import com.telegraph.chats.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ChatRepository extends JpaRepository<Channel, Long> {
    Channel findByName(String name);


}