package silver.bars.marketplace.live.order.board.domain;

import java.math.BigDecimal;

/**
 * Created by raimon on 26/03/2017.
 */
public final class NewOrder {
    private final String userId;
    private final BigDecimal quantity;
    private final Integer pricePerKg;
    private final Order.Type type;

    public NewOrder(String userId, BigDecimal quantity, Integer pricePerKg, Order.Type type) {
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Integer getPricePerKg() {
        return pricePerKg;
    }

    public Order.Type getType() {
        return type;
    }
}
