package silver.bars.marketplace.live.order.board;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import silver.bars.marketplace.live.order.board.domain.NewOrder;
import silver.bars.marketplace.live.order.board.domain.Order;
import silver.bars.marketplace.live.order.board.domain.SummaryOrder;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class LiveOrderBoardApplicationSteps implements En {
    private LiveOrderBoardApplication liveOrderBoardApplication;

    private Order registeredOrder;
    private List<SummaryOrder> summaryOfOrders;

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

        When("^'(.*?)' registers a '(.*?)' order of (.*?)Kg for £(\\d+)$", (String userId, String orderType, String quantityInKg, Integer pricePerKg) -> {
            NewOrder newOrder = new NewOrder(userId,
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

        When("^I ask for the summary of the '(.*?)' orders$", (String orderType) -> {
            summaryOfOrders = liveOrderBoardApplication.listSummary(Order.Type.valueOf(orderType.toUpperCase()));
        });

        Then("^the live order board looks as follows:$", (DataTable orderSummaryDataTable) -> {
            List<String> flattenedExpectedOrderSummaryTable = orderSummaryDataTable.asList(String.class);

            assertThat(summaryOfOrders.size()).isEqualTo(flattenedExpectedOrderSummaryTable.size());

            SummaryOrder firstSummaryOrder = summaryOfOrders.get(0);
            assertThat(firstSummaryOrder.getQuantityPurchasedAsString() + " " + firstSummaryOrder.getPricePerKgAsString())
                    .isEqualTo(flattenedExpectedOrderSummaryTable.get(0));
            SummaryOrder secondSummaryOrder = summaryOfOrders.get(1);
            assertThat(secondSummaryOrder.getQuantityPurchasedAsString() + " " + secondSummaryOrder.getPricePerKgAsString())
                    .isEqualTo(flattenedExpectedOrderSummaryTable.get(1));
        });
    }
}
