package silver.bars.marketplace.live.order.board.domain;

import java.util.Currency;
import java.util.Objects;

/**
 * Created by raimon on 26/03/2017.
 */
public final class PricePerKg {
    private final Currency currency;
    private final int amount;

    private PricePerKg(Currency currency, int amount) {
        Objects.requireNonNull(currency, "currency cannot be null");
        if (amount <= 0) {
            throw new IllegalArgumentException("amount cannot be equal to or less than 0");
        }

        this.currency = currency;
        this.amount = amount;
    }

    public static PricePerKg inGBP(int amount) {
        return new PricePerKg(Currency.getInstance("GBP"), amount);
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrencySymbol() {
        return currency.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricePerKg that = (PricePerKg) o;
        return Objects.equals(currency, that.currency) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "PricePerKg{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
