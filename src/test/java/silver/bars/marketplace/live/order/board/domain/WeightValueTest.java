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

        assertThat(weightValue.toBigDecimal()).isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    public void inKgReturnsInstanceKilogramsAsTheUnitName() {
        WeightValue weightValue = WeightValue.inKg(BigDecimal.ONE);

        assertThat(weightValue.getUnitName()).isEqualTo("kg");
    }

    @Test
    public void inKgRoundsHalfUp() {

        WeightValue scaledWeightValue = WeightValue.inKg(new BigDecimal(3.45));

        assertThat(scaledWeightValue.toBigDecimal()).isEqualTo(new BigDecimal("3.5"));
    }

    @Test
    public void inKgSetsScaleToOne() {

        WeightValue scaledWeightValue = WeightValue.inKg(new BigDecimal(3.4));

        assertThat(scaledWeightValue.toBigDecimal()).isEqualTo(new BigDecimal("3.4"));
    }

    @Test
    public void addOne() {
        WeightValue weightValueOne = WeightValue.inKg(BigDecimal.ONE);

        WeightValue sumOfWeights = weightValueOne.add(weightValueOne);

        assertThat(sumOfWeights.getUnitName()).isEqualTo("kg");
        assertThat(sumOfWeights.toBigDecimal()).isEqualTo(new BigDecimal("2.0"));
    }

    @Test
    public void addWithDecimals() {
        WeightValue weightValueOne = WeightValue.inKg(new BigDecimal(9.1));
        WeightValue weightValueTwo = WeightValue.inKg(new BigDecimal(3.7));

        WeightValue sumOfWeights = weightValueOne.add(weightValueTwo);

        assertThat(sumOfWeights.getUnitName()).isEqualTo("kg");
        assertThat(sumOfWeights.toBigDecimal()).isEqualTo(new BigDecimal("12.8"));
    }

    @Test
    public void addThrowsExceptionWhenParameterIsNull() {
        WeightValue weightValueOne = WeightValue.inKg(BigDecimal.ONE);

        Throwable caughtException = Assertions.catchThrowable(() -> weightValueOne.add(null));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("other cannot be null");
    }
}