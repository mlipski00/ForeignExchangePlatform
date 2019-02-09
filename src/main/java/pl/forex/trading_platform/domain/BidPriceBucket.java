package pl.forex.trading_platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BidPriceBucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Long liquidity;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidPriceBucket_id")
    @JsonIgnore
    private Quotation quotation;

    public BidPriceBucket(Double price, Long liquidity) {
        this.price = price;
        this.liquidity = liquidity;
    }
}
