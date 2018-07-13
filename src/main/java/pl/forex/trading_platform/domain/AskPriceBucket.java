package pl.forex.trading_platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AskPriceBucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Long liquidity;

    @OneToOne
    @JsonIgnore
    private Quotation quotation;

    public AskPriceBucket(Double price, Long liquidity) {
        this.price = price;
        this.liquidity = liquidity;
    }
}
