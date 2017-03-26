package silver.bars.marketplace.live.order.board.persistence;

import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;

import java.util.List;
import java.util.UUID;

/**
 * Created by raimon on 26/03/2017.
 */
public interface OrderRepository {

    Order save(NewOrder newOrder);

    List<Order> findAll();

    void cancel(UUID orderId);
}
