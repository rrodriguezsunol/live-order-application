package silver.bars.marketplace.live.order.board.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by raimon on 26/03/2017.
 */
public final class Order {

    public enum Type {
        BUY,
        SELL
    }

    private final UUID id;
    private final String userId;
    private final WeightValue quantityPurchased;
    private final PricePerKg pricePerKg;
    private final Order.Type type;

    public Order(UUID id, String userId, BigDecimal quantity, Integer pricePerKg, Type type) {
        this.id = id;
        this.userId = userId;
        this.quantityPurchased = WeightValue.inKg(quantity);
        this.pricePerKg = PricePerKg.inGBP(pricePerKg);
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantityPurchased.toBigDecimal();
    }

    public Integer getPricePerKg() {
        return pricePerKg.getAmount();
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(quantityPurchased, order.quantityPurchased) &&
                Objects.equals(pricePerKg, order.pricePerKg) &&
                type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, quantityPurchased, pricePerKg, type);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", quantity=" + quantityPurchased +
                ", pricePerKg=" + pricePerKg +
                ", type=" + type +
                '}';
    }
}
