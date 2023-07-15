package com.telegraph.chats.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ChatCreateRequest {
    @NotBlank
   private String name;

    public ChatCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  ChatCreateRequest(){}
}
