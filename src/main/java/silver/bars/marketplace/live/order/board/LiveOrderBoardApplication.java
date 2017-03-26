package silver.bars.marketplace.live.order.board;

import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.SummaryOrder;
import silver.bars.marketplace.live.order.board.persistence.OrderRepository;
import silver.bars.marketplace.live.order.board.persistence.OrderRepositoryFactory;
import silver.bars.marketplace.live.order.board.service.LiveOrderBoardService;

import java.util.List;
import java.util.UUID;

/**
 * Created by raimon on 26/03/2017.
 */
public final class LiveOrderBoardApplication {
    private final OrderRepository orderRepository;
    private final LiveOrderBoardService liveOrderBoardService;

    public LiveOrderBoardApplication(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.liveOrderBoardService = new LiveOrderBoardService(orderRepository);
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

    public List<SummaryOrder> listSummary(Order.Type orderType) {
        return liveOrderBoardService.listSummary(orderType);
    }
}
