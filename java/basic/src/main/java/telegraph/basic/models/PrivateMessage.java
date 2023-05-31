package telegraph.basic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "privateMessages")
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderUserId", nullable = false)
    private User senderUserPrivateMessage;

    @ManyToOne
    @JoinColumn(name = "receiverUserId", nullable = false)
    private User receiverUserPrivateMessage;

    private String message;
}
