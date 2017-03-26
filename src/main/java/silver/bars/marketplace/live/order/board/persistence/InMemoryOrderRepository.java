package silver.bars.marketplace.live.order.board.persistence;

import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.OrderNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by raimon on 26/03/2017.
 */
final class InMemoryOrderRepository implements OrderRepository {
    private Map<UUID, Order> savedOrders = new HashMap<>();

    @Override
    public Order save(NewOrder newOrder) {
        Objects.requireNonNull(newOrder, "newOrder cannot be null");

        Order order = new Order(UUID.randomUUID(),
                newOrder.getUserId(),
                newOrder.getQuantity(),
                newOrder.getPricePerKg(),
                newOrder.getType());

        savedOrders.put(order.getId(), order);

        return order;
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(savedOrders.values());
    }

    @Override
    public List<Order> findByType(Order.Type orderType) {
        Objects.requireNonNull(orderType, "orderType cannot be null");

        return savedOrders.values().stream()
                .filter(order -> order.getType() == orderType)
                .collect(Collectors.toList());
    }

    @Override
    public void cancel(UUID orderId) {
        Objects.requireNonNull(orderId, "orderId cannot be null");

        if (!savedOrders.containsKey(orderId)) {
            throw new OrderNotFoundException(orderId);
        }

        savedOrders.remove(orderId);
    }
}
