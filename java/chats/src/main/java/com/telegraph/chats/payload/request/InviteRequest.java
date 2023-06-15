package com.telegraph.chats.payload.request;

import jakarta.validation.constraints.NotBlank;

public class InviteRequest {
    @NotBlank

    private String username;
    private int chatId;

    public InviteRequest(String username, int chatId) {
        this.username = username;
        this.chatId = chatId;
    }
    public InviteRequest(){}

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
