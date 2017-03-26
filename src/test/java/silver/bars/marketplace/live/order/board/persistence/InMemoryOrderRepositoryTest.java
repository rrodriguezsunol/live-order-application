package silver.bars.marketplace.live.order.board.persistence;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.OrderNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class InMemoryOrderRepositoryTest {

    private InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();

    @Test
    public void saveThrowsExceptionWhenNewOrderIsNull() {

        Throwable caughtException = Assertions.catchThrowable(() -> inMemoryOrderRepository.save(null));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("newOrder cannot be null");
    }

    @Test
    public void saveReturnsCreatedOrder() {
        NewOrder newOrder = new NewOrder("user-1", new BigDecimal("3.5"), 305, Order.Type.SELL);

        Order actualOrder = inMemoryOrderRepository.save(newOrder);

        assertThat(actualOrder.getId()).isNotNull();
        assertThat(actualOrder.getUserId()).isEqualTo(newOrder.getUserId());
        assertThat(actualOrder.getQuantity()).isEqualTo(newOrder.getQuantity());
        assertThat(actualOrder.getPriceAmount()).isEqualTo(newOrder.getPricePerKg());
        assertThat(actualOrder.getType()).isEqualTo(newOrder.getType());
    }

    @Test
    public void saveStoresOrderInTheRepository() {
        NewOrder newOrder = new NewOrder("user-1", new BigDecimal("3.5"), 305, Order.Type.SELL);

        inMemoryOrderRepository.save(newOrder);

        List<Order> allOrders = inMemoryOrderRepository.findAll();

        Order expectedOrder = new Order(
                UUID.randomUUID(),
                newOrder.getUserId(),
                newOrder.getQuantity(),
                newOrder.getPricePerKg(),
                newOrder.getType());
        assertThat(allOrders).usingElementComparatorIgnoringFields("id").containsExactly(expectedOrder);
    }

    @Test
    public void cancelThrowsExceptionWhenSpecifiedIdIsNull() {
        Throwable caughtException = Assertions.catchThrowable(() -> inMemoryOrderRepository.cancel(null));

        assertThat(caughtException)
                .isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("orderId cannot be null");
    }

    @Test
    public void cancelThrowsNotFoundExceptionWhenNoOrderWithSpecifiedIdExists() {
        UUID orderId = UUID.fromString("02904e39-9dc0-47a4-b7e3-1793fbc64afc");

        Throwable caughtException = Assertions.catchThrowable(() -> inMemoryOrderRepository.cancel(orderId));

        assertThat(caughtException).isExactlyInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order with id '02904e39-9dc0-47a4-b7e3-1793fbc64afc' not found");
    }

    @Test
    public void cancelRemovesOrderFromTheRepository() {
        NewOrder newOrder = new NewOrder("user-1", new BigDecimal("3.5"), 305, Order.Type.SELL);
        Order savedOrder = inMemoryOrderRepository.save(newOrder);

        inMemoryOrderRepository.cancel(savedOrder.getId());

        List<Order> allOrders = inMemoryOrderRepository.findAll();
        assertThat(allOrders).isEmpty();
    }

    @Test
    public void findByTypeThrowsExceptionWhenSpecifiedTypeIsNull() {
        Throwable caughtException = Assertions.catchThrowable(() -> inMemoryOrderRepository.findByType(null));

        assertThat(caughtException)
                .isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("orderType cannot be null");
    }

    @Test
    public void findByTypeReturnsEmptyWhenThereAreNoSavedOrders() {
        List<Order> actualOrders = inMemoryOrderRepository.findByType(Order.Type.BUY);

        assertThat(actualOrders).isEmpty();
    }

    @Test
    public void findByTypeReturnsEmptyWhenThereIsNoOrderOfTheSpecifiedType() {
        NewOrder newOrder = new NewOrder("user-1", new BigDecimal("3.5"), 305, Order.Type.SELL);
        inMemoryOrderRepository.save(newOrder);

        List<Order> actualOrders = inMemoryOrderRepository.findByType(Order.Type.BUY);

        assertThat(actualOrders).isEmpty();
    }
}