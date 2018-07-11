package pl.forex.trading_platform.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BidPriceBucket bidPriceBucket;

    @OneToOne(cascade = CascadeType.ALL)
    private AskPriceBucket askPriceBucket;

    public Quotation(LocalDateTime dateTime, Instrument instrument, BidPriceBucket bidPriceBucket, AskPriceBucket askPriceBucket) {
        this.dateTime = dateTime;
        this.instrument = instrument;
        this.bidPriceBucket = bidPriceBucket;
        this.askPriceBucket = askPriceBucket;
    }
}
