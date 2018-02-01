package example.ddd.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseAggregateRoot {

    protected final AggregateId aggregateId;

    public BaseAggregateRoot() {
        this.aggregateId = AggregateId.generate();
    }

    public static class AggregateId {

        private final String id;

        public static AggregateId generate() {
            return new AggregateId();
        }

        private AggregateId() {
            id = UUID.randomUUID().toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final AggregateId that = (AggregateId) o;

            return id.equals(that.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return id;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BaseAggregateRoot that = (BaseAggregateRoot) o;

        return aggregateId.equals(that.aggregateId);
    }

    @Override
    public int hashCode() {
        return aggregateId.hashCode();
    }
}
