package com.telegraph.messages.controllers;

import com.telegraph.messages.models.ChannelMessage;
import com.telegraph.messages.models.User;
import com.telegraph.messages.payload.request.JwtRequest;
import com.telegraph.messages.payload.request.LoginRequest;
import com.telegraph.messages.payload.request.SignupRequest;
import com.telegraph.messages.payload.response.JwtResponse;
import com.telegraph.messages.payload.response.MessageResponse;
import com.telegraph.messages.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import java.util.Arrays;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;


    @Value("${app.authUrl}")
    private String authUrl;
    @Value("${app.usersUrl}")
    private String usersUrl;

    @Value("${app.chatsUrl}")
    private String chatsUrl;

    @Autowired
    private RestTemplate authTemplate;


    @MessageMapping("/channelMessages")
    public void sendMessage(ChannelMessage message) {

    }


}