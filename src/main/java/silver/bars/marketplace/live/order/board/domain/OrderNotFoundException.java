package silver.bars.marketplace.live.order.board.domain;

import java.util.UUID;

/**
 * Created by raimon on 26/03/2017.
 */
public final class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID orderId) {
        super(String.format("Order with id '%s' not found", orderId));
    }
}
