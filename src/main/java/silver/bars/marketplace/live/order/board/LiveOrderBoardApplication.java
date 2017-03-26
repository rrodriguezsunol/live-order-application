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
    private final LiveOrderBoardService liveOrderBoardService;

    public LiveOrderBoardApplication(LiveOrderBoardService liveOrderBoardService) {
        this.liveOrderBoardService = liveOrderBoardService;
    }

    public static LiveOrderBoardApplication start() {
        OrderRepository orderRepository = OrderRepositoryFactory.newInMemoryInstance();
        return new LiveOrderBoardApplication(new LiveOrderBoardService(orderRepository));
    }

    public List<Order> listAll() {
        return liveOrderBoardService.listAll();
    }

    public Order register(NewOrder newOrder) {
        return liveOrderBoardService.save(newOrder);
    }

    public void cancel(UUID registeredOrderId) {
        liveOrderBoardService.cancel(registeredOrderId);
    }

    public List<SummaryOrder> listSummary(Order.Type orderType) {
        return liveOrderBoardService.listSummary(orderType);
    }
}
