package com.telegraph.authentication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String passwordHash;
    private String email;

    @ManyToMany(mappedBy = "channelUsers")
    private List<Channel> channels;

    @OneToMany(mappedBy = "creatorUser")
    private List<Channel> channelsCreator;

    @OneToMany(mappedBy = "senderUserPrivateMessage")
    private List<PrivateMessage> senderPrivateMessage;

    @OneToMany(mappedBy = "receiverUserPrivateMessage")
    private List<PrivateMessage> receiverPrivateMessage;

    @OneToMany(mappedBy = "senderUserChannelMessage")
    private List<ChannelMessage> senderChannelMessage;
}
