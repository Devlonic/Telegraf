package com.telegraph.chats.controllers;


import com.telegraph.chats.models.Channel;
import com.telegraph.chats.models.User;
import com.telegraph.chats.payload.request.ChatCreateRequest;
import com.telegraph.chats.payload.request.InviteRequest;
import com.telegraph.chats.payload.request.JwtRequest;
import com.telegraph.chats.payload.response.JwtResponse;
import com.telegraph.chats.payload.response.MessageResponse;
import com.telegraph.chats.rep.ChatRepository;
import com.telegraph.chats.rep.UserRepository;
import com.telegraph.chats.security.jwt.AuthEntryPointJwt;
import com.telegraph.chats.security.services.ChatService;
import com.telegraph.chats.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/chats")
public class ChatsController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;


    @Autowired
    ChatService chatService;

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
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    @GetMapping("/")
    public ResponseEntity<?> getUserChats(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<JwtRequest> entity = new HttpEntity<JwtRequest>(JwtRequest.toJWT(authToken), headers);

        try {
            JwtResponse isValid = authTemplate.exchange(authUrl + "api/v1/auth/validate", HttpMethod.POST, entity, JwtResponse.class).getBody();

            var user = userService.getUserById(isValid.getId().longValue()).get();
            var response = user.getChannels();

            for(var a: response){
                a.setChannelUsers(null);
                a.setCreatorUser(null);
            }
            return ResponseEntity.ok(response  );


        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:" + e.getMessage()));
        }

    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,@RequestBody ChatCreateRequest channel) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<JwtRequest> entity = new HttpEntity<JwtRequest>(JwtRequest.toJWT(authToken), headers);

        try {
            JwtResponse isValid = authTemplate.exchange(authUrl + "api/v1/auth/validate", HttpMethod.POST, entity, JwtResponse.class).getBody();

            Channel newChannel = new Channel(channel.getName(),userService.getUserById(isValid.getId()).get());

            var temp = userService.getUserById(isValid.getId()).get().getChannels();
            temp.add(newChannel);
            userService.getUserById(isValid.getId()).get().setChannels(temp);


            chatService.saveChannel(newChannel) ;
            return ResponseEntity.ok(new MessageResponse("Chat created successfully!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:" + e.getMessage()));
        }

    }

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @RequestBody InviteRequest request) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<JwtRequest> entity = new HttpEntity<JwtRequest>(JwtRequest.toJWT(authToken), headers);

        try {
            JwtResponse isValid = authTemplate.exchange(authUrl + "api/v1/auth/validate", HttpMethod.POST, entity, JwtResponse.class).getBody();

            Channel temp = chatService.getChannelById(request.getChatId()).get();
            temp.getChannelUsers().add(userService.getUserByName(request.getUsername()).get());



            return ResponseEntity.ok(new MessageResponse("User has been invited to chat successfully!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:" + e.getMessage()));
        }

    }


}