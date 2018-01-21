package example.vehicleworkshop.vehiclecatalog.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
class EngineCapacity {

    private final BigDecimal capacity;

    private final Unit unit;

    private final BigDecimal MULTIPLIER = BigDecimal.valueOf(1000L);

    enum Unit {
        CUBIC_CENTIMETER, CUBIC_DECIMETER
    }

    public EngineCapacity(double capacity, String unitName) {
        this.capacity = BigDecimal.valueOf(capacity);
        this.unit = Unit.valueOf(unitName);
    }

    public EngineCapacity(double capacity, Unit unit) {
        this.capacity = BigDecimal.valueOf(capacity);
        this.unit = unit;
    }

    BigDecimal toCubicCentimeter() {
        if(this.unit == Unit.CUBIC_CENTIMETER) {
            return capacity;
        }
        return capacity.multiply(MULTIPLIER);
    }

    BigDecimal toCubicDecimeter() {
        if(this.unit == Unit.CUBIC_DECIMETER) {
            return capacity;
        }
        return capacity.divide(MULTIPLIER);
    }

    @Override
    public int hashCode() {
        int result = capacity.hashCode();
        result = 31 * result + unit.hashCode();
        result = 31 * result + MULTIPLIER.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EngineCapacity that = (EngineCapacity) o;

        if (!capacity.equals(that.capacity)) return false;
        if (unit != that.unit) return false;
        return MULTIPLIER.equals(that.MULTIPLIER);
    }
}
