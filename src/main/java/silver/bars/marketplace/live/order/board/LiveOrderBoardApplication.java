package silver.bars.marketplace.live.order.board;

import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.persistence.OrderRepository;
import silver.bars.marketplace.live.order.board.persistence.OrderRepositoryFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by raimon on 26/03/2017.
 */
public final class LiveOrderBoardApplication {
    private final OrderRepository orderRepository;

    private LiveOrderBoardApplication(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public static LiveOrderBoardApplication start() {
        return new LiveOrderBoardApplication(OrderRepositoryFactory.newInMemoryInstance());
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public Order register(NewOrder newOrder) {
        return orderRepository.save(newOrder);
    }

    public void cancel(UUID registeredOrderId) {
        orderRepository.cancel(registeredOrderId);
    }
}
