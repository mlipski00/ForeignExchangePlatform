package pl.forex.trading_platform.domain;

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
    private Quotation quotation;
}
