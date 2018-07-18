package pl.forex.trading_platform.domain.nbp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private String code;

    private String mid;

    public Rates(String currency, String code, String mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }
}
