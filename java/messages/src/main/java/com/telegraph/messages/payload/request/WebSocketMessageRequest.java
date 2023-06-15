package com.telegraph.messages.payload.request;

import com.telegraph.messages.models.ChannelMessage;
import com.telegraph.messages.payload.MessageType;

public class WebSocketMessageRequest {
    ChannelMessage channelMessage;
    MessageType messageType;

    public WebSocketMessageRequest(ChannelMessage channelMessage, MessageType messageType) {
        this.channelMessage = channelMessage;
        this.messageType = messageType;
    }

    public WebSocketMessageRequest() {
    }

    public ChannelMessage getChannelMessage() {
        return channelMessage;
    }

    public void setChannelMessage(ChannelMessage channelMessage) {
        this.channelMessage = channelMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
