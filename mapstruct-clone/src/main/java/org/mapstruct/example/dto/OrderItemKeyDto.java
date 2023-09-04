package org.mapstruct.example.dto;

import java.util.Objects;

/**
 * @author Sjaak Derksen
 */
public class OrderItemKeyDto {

    private long stockNumber;

    public long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(long stockNumber) {
        this.stockNumber = stockNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemKeyDto)) return false;
        final OrderItemKeyDto that = (OrderItemKeyDto) o;
        return stockNumber == that.stockNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockNumber);
    }
}
