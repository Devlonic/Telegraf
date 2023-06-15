package com.telegraph.chats.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private User creatorUser;

    @ManyToMany
    @JoinTable(
            name = "channel_users",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> channelUsers;
    @OneToMany(mappedBy = "receiverChannelMessage")
    private List<ChannelMessage> channelMessages;

    public Channel(@NotNull String name, @NotNull User creatorUser) {
        this.name = name;
        this.creatorUser = creatorUser;
        this.channelUsers = new ArrayList<>();
        channelUsers.add(creatorUser);
        this.channelMessages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creatorUser=" + creatorUser +
                ", channelUsers=" + channelUsers +
                ", channelMessages=" + channelMessages +
                '}';
    }
}
