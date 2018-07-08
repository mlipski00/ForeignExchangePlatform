package pl.forex.trading_platform.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BidPriceBucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Long liquidity;

    @OneToOne
    private Quotation quotation;

    public BidPriceBucket(Double price, Long liquidity) {
        this.price = price;
        this.liquidity = liquidity;
    }
}
