package example.ddd.infrastructure;

import example.ddd.domain.BaseAggregateRoot;

import java.io.Serializable;
import java.util.Optional;

public interface DomainRepository<T extends BaseAggregateRoot, I extends Serializable> {

    void save(T t);

    Optional<T> findOne(I id);

    default T findOneOrThrow(I id) {
        return findOne(id).orElseThrow(() -> new RepositoryException("Record not found!"));
    }

    void remove(T t);
}
