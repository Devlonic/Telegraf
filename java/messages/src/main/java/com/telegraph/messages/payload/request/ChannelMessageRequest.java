package com.telegraph.messages.payload.request;

import com.telegraph.messages.models.ChannelMessage;

public class ChannelMessageRequest {
    int chatId;

    public ChannelMessageRequest(int chatId) {
        this.chatId = chatId;
    }

    public ChannelMessageRequest() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
