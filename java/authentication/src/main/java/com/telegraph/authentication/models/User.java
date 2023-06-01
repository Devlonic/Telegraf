package com.telegraph.authentication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

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

    public User(String name, String email, String encode) {
        this.name = name;
        this.email =email;
        this.passwordHash = encode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", channels=" + channels +
                ", channelsCreator=" + channelsCreator +
                ", senderPrivateMessage=" + senderPrivateMessage +
                ", receiverPrivateMessage=" + receiverPrivateMessage +
                ", senderChannelMessage=" + senderChannelMessage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(passwordHash, user.passwordHash) && Objects.equals(email, user.email) && Objects.equals(channels, user.channels) && Objects.equals(channelsCreator, user.channelsCreator) && Objects.equals(senderPrivateMessage, user.senderPrivateMessage) && Objects.equals(receiverPrivateMessage, user.receiverPrivateMessage) && Objects.equals(senderChannelMessage, user.senderChannelMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, passwordHash, email, channels, channelsCreator, senderPrivateMessage, receiverPrivateMessage, senderChannelMessage);
    }


}
