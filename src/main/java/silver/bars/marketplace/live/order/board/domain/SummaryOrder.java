package silver.bars.marketplace.live.order.board.domain;

import java.util.Objects;

/**
 * Created by raimon on 26/03/2017.
 */
public final class SummaryOrder {
    private final WeightValue quantityPurchased;
    private final PricePerKg price;

    public SummaryOrder(WeightValue quantityPurchased, PricePerKg pricePerKg) {
        Objects.requireNonNull(quantityPurchased, "quantityPurchased cannot be null");
        Objects.requireNonNull(pricePerKg, "pricePerKg cannot be null");

        this.quantityPurchased = quantityPurchased;
        this.price = pricePerKg;
    }

    public WeightValue getQuantityPurchased() {
        return quantityPurchased;
    }

    public int getPriceAmount() {
        return price.getAmount();
    }

    public String getQuantityPurchasedAsString() {
        return quantityPurchased.toBigDecimal() + " " + quantityPurchased.getUnitName();
    }

    public String getPricePerKgAsString() {
        return price.getCurrencySymbol() + price.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryOrder that = (SummaryOrder) o;
        return Objects.equals(quantityPurchased, that.quantityPurchased) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantityPurchased, price);
    }

    @Override
    public String toString() {
        return "SummaryOrder{" +
                "quantityPurchased='" + quantityPurchased + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
