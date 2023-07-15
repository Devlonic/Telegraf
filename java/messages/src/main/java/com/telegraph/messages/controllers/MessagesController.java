package com.telegraph.messages.controllers;

import com.telegraph.messages.models.ChannelMessage;
import com.telegraph.messages.models.User;
import com.telegraph.messages.payload.request.ChannelMessageRequest;
import com.telegraph.messages.payload.request.JwtRequest;
import com.telegraph.messages.payload.request.LoginRequest;
import com.telegraph.messages.payload.request.SignupRequest;
import com.telegraph.messages.payload.response.JwtResponse;
import com.telegraph.messages.payload.response.MessageResponse;
import com.telegraph.messages.rep.MessagesRepository;
import com.telegraph.messages.security.services.ChatService;
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

import org.springframework.web.socket.TextMessage;

import java.io.IOException;
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

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessagesRepository messagesRepository;


    @GetMapping("/")
    public ResponseEntity<?> getUserData(@RequestHeader(HttpHeaders.AUTHORIZATION)String authToken, @RequestBody ChannelMessageRequest request) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<JwtRequest> entity = new HttpEntity<JwtRequest>(JwtRequest.toJWT(authToken), headers);

        try {
            JwtResponse isValid = authTemplate.exchange(authUrl + "api/v1/auth/validate", HttpMethod.POST, entity, JwtResponse.class).getBody();
            var chat = chatService.getChannelById(isValid.getId().intValue()).get();
          if(!chat.getChannelUsers().contains(userService.getUserById(isValid.getId().longValue()))) { return ResponseEntity.ok("Error: invalid channel");}

              var response = chat.getChannelMessages();

              for(var temp : response){
                  temp.setReceiverChannelMessage(null);
                  temp.getSenderUserChannelMessage().setChannels(null);
                  temp.getSenderUserChannelMessage().setChannelsCreator(null);
              }

              return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }

    }


}