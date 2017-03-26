package silver.bars.marketplace.live.order.board.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class PricePerKgTest {

    @Test
    public void inGBPThrowsExceptionWhenSpecifiedValueIsEqualToZero() {
        Throwable caughtException = Assertions.catchThrowable(() -> PricePerKg.inGBP(0));

        assertThat(caughtException)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount cannot be equal to or less than 0");
    }

    @Test
    public void inGBPThrowsExceptionWhenSpecifiedValueIsLessThanZero() {
        Throwable caughtException = Assertions.catchThrowable(() -> PricePerKg.inGBP(-1));

        assertThat(caughtException)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("amount cannot be equal to or less than 0");
    }

    @Test
    public void inGBPReturnsInstanceWithSpecifiedPositiveValue() {
        PricePerKg pricePerKg = PricePerKg.inGBP(1);

        assertThat(pricePerKg.getAmount()).isEqualTo(1);
    }

    @Test
    public void inGBPReturnsInstanceWithPoundSterlingAsCurrencySymbol() {
        PricePerKg pricePerKg = PricePerKg.inGBP(1);

        assertThat(pricePerKg.getCurrencySymbol()).isEqualTo("£");
    }
}