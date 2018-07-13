package pl.forex.trading_platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
