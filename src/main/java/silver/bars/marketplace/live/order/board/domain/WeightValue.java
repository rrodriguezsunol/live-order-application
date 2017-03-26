package silver.bars.marketplace.live.order.board.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by raimon on 26/03/2017.
 */
public final class WeightValue {
    private final String unitName;
    private final BigDecimal value;

    private WeightValue(String unitName, BigDecimal value) {
        Objects.requireNonNull(unitName, "unitName cannot be null");
        Objects.requireNonNull(value, "value cannot be null");

        value = value.setScale(2, BigDecimal.ROUND_UP).setScale(1, BigDecimal.ROUND_HALF_DOWN);

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("value cannot be equal to or less than 0");
        }

        this.unitName = unitName;
        this.value = value;
    }

    public static WeightValue inKg(BigDecimal value) {
        return new WeightValue("kg", value);
    }

    public String getUnitName() {
        return unitName;
    }

    public BigDecimal toBigDecimal() {
        return value;
    }

    public WeightValue add(WeightValue other) {
        Objects.requireNonNull(other, "other cannot be null");

        BigDecimal sum = this.value.add(other.value);
        return new WeightValue(this.unitName, sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightValue that = (WeightValue) o;
        return Objects.equals(unitName, that.unitName) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitName, value);
    }

    @Override
    public String toString() {
        return "WeightValue{" +
                "unitName='" + unitName + '\'' +
                ", value=" + value +
                '}';
    }
}
