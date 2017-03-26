package silver.bars.marketplace.live.order.board.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.PricePerKg;
import silver.bars.marketplace.live.order.board.domain.SummaryOrder;
import silver.bars.marketplace.live.order.board.domain.WeightValue;
import silver.bars.marketplace.live.order.board.persistence.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by raimon on 26/03/2017.
 */
public class LiveOrderBoardServiceTest {

    private LiveOrderBoardService liveOrderBoardService;

    private OrderRepository mockOrderRepository = Mockito.mock(OrderRepository.class);

    @Before
    public void initTestSubject() {
        liveOrderBoardService = new LiveOrderBoardService(mockOrderRepository);
    }

    @Test
    public void listSummaryReturnsEmptyListWhenThereAreNoOrdersInTheRepository() {
        given(mockOrderRepository.findByType(any())).willReturn(emptyList());

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.SELL);

        assertThat(actualSummaryOrders).isEmpty();
    }

    @Test
    public void listSummaryConvertsOrderToSummaryOrder() {
        Order sellOrder = new Order(UUID.randomUUID(), "user-1", BigDecimal.ONE, 300, Order.Type.SELL);
        given(mockOrderRepository.findByType(Order.Type.SELL)).willReturn(singletonList(sellOrder));

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.SELL);

        SummaryOrder summaryOrder = new SummaryOrder(WeightValue.inKg(BigDecimal.ONE), PricePerKg.inGBP(300));
        assertThat(actualSummaryOrders).containsExactly(summaryOrder);
    }

    @Test
    public void listSummaryConvertsManyOrderToSummaryOrders() {
        Order sellOrderOne = new Order(UUID.randomUUID(), "user-1", BigDecimal.ONE, 300, Order.Type.SELL);
        Order sellOrderTwo = new Order(UUID.randomUUID(), "user-2", BigDecimal.TEN, 450, Order.Type.SELL);
        given(mockOrderRepository.findByType(Order.Type.SELL)).willReturn(asList(sellOrderOne, sellOrderTwo));

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.SELL);

        SummaryOrder summaryOrderOne = new SummaryOrder(WeightValue.inKg(BigDecimal.ONE), PricePerKg.inGBP(300));
        SummaryOrder summaryOrderTwo = new SummaryOrder(WeightValue.inKg(BigDecimal.TEN), PricePerKg.inGBP(450));
        assertThat(actualSummaryOrders).containsExactly(summaryOrderOne, summaryOrderTwo);
    }

    @Test
    public void listSummaryMergesQuantitiesForOrdersWithSamePrice() {
        Order sellOrderOne = new Order(UUID.randomUUID(), "user-1", new BigDecimal(3.4), 300, Order.Type.SELL);
        Order sellOrderTwo = new Order(UUID.randomUUID(), "user-2", new BigDecimal(5.2), 300, Order.Type.SELL);
        given(mockOrderRepository.findByType(Order.Type.SELL)).willReturn(asList(sellOrderOne, sellOrderTwo));

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.SELL);

        SummaryOrder summaryOrder = new SummaryOrder(WeightValue.inKg(new BigDecimal(8.6)), PricePerKg.inGBP(300));
        assertThat(actualSummaryOrders).containsExactly(summaryOrder);
    }

    @Test
    public void listSummarySortsSellSummaryOrdersByPricePerKgInAscendingOrder() {
        Order sellOrderOne = new Order(UUID.randomUUID(), "user-1", new BigDecimal(3.4), 301, Order.Type.SELL);
        Order sellOrderTwo = new Order(UUID.randomUUID(), "user-2", new BigDecimal(5.2), 299, Order.Type.SELL);
        Order sellOrderThree = new Order(UUID.randomUUID(), "user-1", new BigDecimal(1.6), 300, Order.Type.SELL);
        given(mockOrderRepository.findByType(Order.Type.SELL)).willReturn(asList(sellOrderOne, sellOrderTwo, sellOrderThree));

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.SELL);

        SummaryOrder summaryOrderOne = new SummaryOrder(sellOrderTwo.getQuantityPurchased(), sellOrderTwo.getPricePerKg());
        SummaryOrder summaryOrderTwo = new SummaryOrder(sellOrderThree.getQuantityPurchased(), sellOrderThree.getPricePerKg());
        SummaryOrder summaryOrderThree = new SummaryOrder(sellOrderOne.getQuantityPurchased(), sellOrderOne.getPricePerKg());
        assertThat(actualSummaryOrders).containsExactly(summaryOrderOne, summaryOrderTwo, summaryOrderThree);
    }

    @Test
    public void listSummarySortsBuySummaryOrdersByPricePerKgInDescendingOrder() {
        Order buyOrderOne = new Order(UUID.randomUUID(), "user-1", new BigDecimal(3.4), 299, Order.Type.BUY);
        Order buyOrderTwo = new Order(UUID.randomUUID(), "user-2", new BigDecimal(5.2), 301, Order.Type.BUY);
        Order buyOrderThree = new Order(UUID.randomUUID(), "user-1", new BigDecimal(1.6), 300, Order.Type.BUY);
        given(mockOrderRepository.findByType(Order.Type.BUY)).willReturn(asList(buyOrderOne, buyOrderTwo, buyOrderThree));

        List<SummaryOrder> actualSummaryOrders = liveOrderBoardService.listSummary(Order.Type.BUY);

        SummaryOrder summaryOrderOne = new SummaryOrder(buyOrderTwo.getQuantityPurchased(), buyOrderTwo.getPricePerKg());
        SummaryOrder summaryOrderTwo = new SummaryOrder(buyOrderThree.getQuantityPurchased(), buyOrderThree.getPricePerKg());
        SummaryOrder summaryOrderThree = new SummaryOrder(buyOrderOne.getQuantityPurchased(), buyOrderOne.getPricePerKg());
        assertThat(actualSummaryOrders).containsExactly(summaryOrderOne, summaryOrderTwo, summaryOrderThree);
    }
}