package calamadevs.domain.Supply;

import java.math.BigDecimal;

public enum FuelType {
    GASOLINE(new BigDecimal("28.99")),
    ETHANOL(new BigDecimal("20.09")),
    DIESEL(new BigDecimal("35.65"));

    private final BigDecimal mjPerLiter;

    FuelType(BigDecimal mjPerLiter) {
        this.mjPerLiter = mjPerLiter;
    }

    public BigDecimal getMjPerLiter() {
        return mjPerLiter;
    }
}