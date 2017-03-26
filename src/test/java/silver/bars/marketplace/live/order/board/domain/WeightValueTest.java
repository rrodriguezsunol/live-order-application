package silver.bars.marketplace.live.order.board.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class WeightValueTest {

    @Test
    public void inKgThrowsExceptionWhenSpecifiedValueIsNull() {
        Throwable caughtException = Assertions.catchThrowable(() -> WeightValue.inKg(null));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class).hasMessage("value cannot be null");
    }

    @Test
    public void inKgThrowsExceptionWhenSpecifiedValueIsEqualToZero() {
        Throwable caughtException = Assertions.catchThrowable(() -> WeightValue.inKg(BigDecimal.ZERO));

        assertThat(caughtException)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("value cannot be equal to or less than 0");
    }

    @Test
    public void inKgThrowsExceptionWhenSpecifiedValueIsLessThanZero() {
        Throwable caughtException = Assertions.catchThrowable(() -> WeightValue.inKg(new BigDecimal("-0.1")));

        assertThat(caughtException)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("value cannot be equal to or less than 0");
    }

    @Test
    public void inKgReturnsInstanceWithSpecifiedPositiveValue() {
        WeightValue weightValue = WeightValue.inKg(BigDecimal.ONE);

        assertThat(weightValue.toBigDecimal()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void inKgReturnsInstanceKilogramsAsTheUnitName() {
        WeightValue weightValue = WeightValue.inKg(BigDecimal.ONE);

        assertThat(weightValue.getUnitName()).isEqualTo("kg");
    }
}