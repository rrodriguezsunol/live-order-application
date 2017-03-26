package silver.bars.marketplace.live.order.board.persistence;

/**
 * Created by raimon on 26/03/2017.
 */
public final class OrderRepositoryFactory {

    private OrderRepositoryFactory() {}

    public static OrderRepository newInMemoryInstance() {
        return new InMemoryOrderRepository();
    }
}
