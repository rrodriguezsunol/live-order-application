package silver.bars.marketplace.live.order.board.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class SummaryOrderTest {

    @Test
    public void constructorThrowsExceptionWhenQuantityPurchasedIsNull() {

        Throwable caughtException = Assertions.catchThrowable(() -> new SummaryOrder(null, PricePerKg.inGBP(1)));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("quantityPurchased cannot be null");
    }

}