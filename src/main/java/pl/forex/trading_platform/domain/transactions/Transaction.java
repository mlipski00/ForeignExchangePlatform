package pl.forex.trading_platform.domain.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import pl.forex.trading_platform.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long id;

    private String instrument;

    @CreationTimestamp
    private Date tradeDateTime;

    private double amount;

    private double amountPLN;

    private boolean isClosed;

    private Date closeDateTime;

    private double price;

    private double closePrice;

    private BuySell buySell;

    private boolean isExecuted;

    private String executionFailReason;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @Column(name = "user_id")
    private User user;

    private double profit;

}
