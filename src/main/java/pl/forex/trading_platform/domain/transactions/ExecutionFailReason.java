package pl.forex.trading_platform.domain.transactions;

import lombok.Getter;

@Getter
public enum ExecutionFailReason {

    NOT_ENOUGH_BALANCE("Balance not enough"),
    STATUS_OK("0")
    ;

    private final String reason;

    ExecutionFailReason(String reason) {
        this.reason = reason;
    }
}
