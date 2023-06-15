package com.telegraph.chats.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private User creatorUser;

    @ManyToMany
    @JoinTable(name = "channelsUsers", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "channelId"))
    private List<User> channelUsers;

    @OneToMany(mappedBy = "receiverChannelMessage")
    private List<ChannelMessage> channelMessages;

    public Channel(@NotNull String name, @NotNull User creatorUser) {
        this.name = name;
        this.creatorUser = creatorUser;
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
