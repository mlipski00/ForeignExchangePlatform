package pl.forex.trading_platform.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
//@EqualsAndHashCode(exclude = {"quotation"})
@Entity
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrument")
    private Set<Quotation> quotations = new HashSet<>();
}
