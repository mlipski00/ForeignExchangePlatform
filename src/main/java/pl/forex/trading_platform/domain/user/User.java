package pl.forex.trading_platform.domain.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.validator.UniqueEmail;
import pl.forex.trading_platform.validator.ValidationGroupUniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Size(min = 1, message = "username can not be empty")
    @Column(name = "name")
    private String username;

    @Size(min = 6,  message = "password must be at least 6 characters")
    @Column(name = "password")
    private String password;

    @Size(min = 1, max = 250)
    private String userDetails;

    private boolean active;

    private double balance;

    private double blockedAmount;

    @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "type correct email")
    @UniqueEmail(groups = ValidationGroupUniqueEmail.class)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @CreationTimestamp
    private Date created;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "sender_messages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> sendedMessages;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "reciver_messages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> recivedMessages;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_transactions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions;

    public User(User user) {
        this.active = user.isActive();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.username = user.getUsername();
        this.id = user.getId();
        this.password = user.getPassword();
    }
}
