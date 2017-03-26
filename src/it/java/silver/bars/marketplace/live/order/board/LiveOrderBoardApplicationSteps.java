package silver.bars.marketplace.live.order.board;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class LiveOrderBoardApplicationSteps implements En {
    private LiveOrderBoardApplication liveOrderBoardApplication;

    private Order registeredOrder;

    @Before
    public void startApplication() {
        liveOrderBoardApplication = LiveOrderBoardApplication.start();
    }

    @After
    public void stopApplication() {
        liveOrderBoardApplication = null;
    }

    public LiveOrderBoardApplicationSteps() {
        Given("^There are no orders on the board$", () -> {
            List<Order> orders = liveOrderBoardApplication.listAll();

            assertThat(orders).isEmpty();
        });

        When("^I register a (.*?) order of (.*?)Kg for £(\\d+)$", (String orderType, String quantityInKg, Integer pricePerKg) -> {
            NewOrder newOrder = new NewOrder("1",
                    new BigDecimal(quantityInKg),
                    pricePerKg,
                    Order.Type.valueOf(orderType.toUpperCase()));
            registeredOrder = liveOrderBoardApplication.register(newOrder);
        });

        Then("^The live order board displays such order$", () -> {
            List<Order> orders = liveOrderBoardApplication.listAll();

            assertThat(orders).containsExactly(registeredOrder);
        });

        When("^I cancel the registered order$", () -> {
            liveOrderBoardApplication.cancel(registeredOrder.getId());
        });

        Then("^The live order board does not display the order anymore$", () -> {
            List<Order> orders = liveOrderBoardApplication.listAll();

            assertThat(orders).isEmpty();
        });
    }
}
