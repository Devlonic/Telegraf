package com.telegraph.users.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private User creatorUser;

    @ManyToMany
    @JoinTable(name = "channelsUsers", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "channelId"))
    private List<User> channelUsers;

    @OneToMany(mappedBy = "receiverChannelMessage")
    private List<ChannelMessage> channelMessages;
}
