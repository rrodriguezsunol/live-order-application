package silver.bars.marketplace.live.order.board.service;

import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.PricePerKg;
import silver.bars.marketplace.live.order.board.domain.SummaryOrder;
import silver.bars.marketplace.live.order.board.domain.WeightValue;
import silver.bars.marketplace.live.order.board.persistence.OrderRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * Created by raimon on 26/03/2017.
 */
public final class LiveOrderBoardService {
    private final Map<Order.Type, Comparator<SummaryOrder>> summaryOrderComparators;

    private final OrderRepository orderRepository;

    public LiveOrderBoardService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        summaryOrderComparators = new HashMap<>();
        summaryOrderComparators.put(Order.Type.BUY, (orderOne, orderTwo) -> orderTwo.getPriceAmount() - orderOne.getPriceAmount());
        summaryOrderComparators.put(Order.Type.SELL, (orderOne, orderTwo) -> orderOne.getPriceAmount() - orderTwo.getPriceAmount());
    }

    public Order save(NewOrder newOrder) {
        return orderRepository.save(newOrder);
    }

    public void cancel(UUID orderId) {
        orderRepository.cancel(orderId);
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public List<SummaryOrder> listSummary(Order.Type orderType) {
        List<Order> ordersByType = orderRepository.findByType(orderType);

        Map<PricePerKg, SummaryOrder> summaryOrderMap = new HashMap<>();

        for (Order order : ordersByType) {
            PricePerKg pricePerKg = order.getPricePerKg();

            if (summaryOrderMap.containsKey(pricePerKg)) {
                SummaryOrder summaryOrder = summaryOrderMap.get(pricePerKg);

                WeightValue aggregatedQuantityPurchased = summaryOrder.getQuantityPurchased().add(order.getQuantityPurchased());
                summaryOrderMap.put(pricePerKg, new SummaryOrder(aggregatedQuantityPurchased, pricePerKg));
            } else {
                summaryOrderMap.put(pricePerKg, new SummaryOrder(order.getQuantityPurchased(), pricePerKg));
            }
        }

        return summaryOrderMap.values().stream()
                .sorted(summaryOrderComparators.get(orderType))
                .collect(toList());
    }
}
