package telegraph.basic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "channelMessages")
public class ChannelMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderUserId", nullable = false)
    private User senderUserChannelMessage;

    @ManyToOne
    @JoinColumn(name = "channelId", nullable = false)
    private Channel receiverChannelMessage;

    private String message;
}
