package pl.forex.trading_platform.domain.nbp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TableA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String table;

    private String no;

    private String effectiveDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rates> rates;

    public TableA(Long id, String table, String no, String effectiveDate, List<Rates> rates) {
        this.id = id;
        this.table = table;
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }
}
