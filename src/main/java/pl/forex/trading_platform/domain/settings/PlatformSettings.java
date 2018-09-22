package pl.forex.trading_platform.domain.settings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlatformSettings {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Size(min = 1, max = 100)
    private int decisionTime;

    private Long minimumTradeAmount;
    private Long maximumTradeAmount;
    private Long initialBalance;

    private PlatformSettings(int decisionTime, Long minimumTradeAmount, Long maximumTradeAmount, long initialBalance) {
        this.decisionTime = decisionTime;
        this.minimumTradeAmount = minimumTradeAmount;
        this.maximumTradeAmount = maximumTradeAmount;
        this.initialBalance = initialBalance;
    }
    public static PlatformSettingsBuilder builder() {
        return new PlatformSettingsBuilder();
    }

    public static class PlatformSettingsBuilder {
        private int decisionTime;
        private Long minimumTradeAmount;
        private Long maximumTradeAmount;
        private Long initialBalance;

        public PlatformSettingsBuilder setDecisionTime(final int decisionTime) {
            this.decisionTime = decisionTime;
            return this;
        }

        public PlatformSettingsBuilder setMinimumTradeAumount(final Long minimumTradeAmount) {
            this.minimumTradeAmount = minimumTradeAmount;
            return this;
        }

        public PlatformSettingsBuilder setMaximumTradeAumount(final Long maximumTradeAmount) {
            this.maximumTradeAmount = maximumTradeAmount;
            return this;
        }

        public PlatformSettingsBuilder setInitialBalance(final long initialBalance) {
            this.initialBalance = initialBalance;
            return this;
        }

        public PlatformSettings build() {
            return new PlatformSettings(decisionTime, minimumTradeAmount, maximumTradeAmount, initialBalance);
        }
    }
}
