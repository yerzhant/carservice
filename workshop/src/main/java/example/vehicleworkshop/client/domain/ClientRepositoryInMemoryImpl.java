package example.vehicleworkshop.client.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.client.domain.exceptions.ClientNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

class ClientRepositoryInMemoryImpl implements ClientRepository {

    private final Map<BaseAggregateRoot.AggregateId, Client> clients = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(final Client client) {
        requireNonNull(client);
        clients.put(client.getAggregateId(), client);
    }

    @Override
    public Optional<Client> findOne(final Long id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public Client findOneOrThrow(final Long id) {
        return Optional.ofNullable(clients.get(id)).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client findByPersonalNumberOrThrow(final String personalNumber) {
        return findByPersonalNumber(personalNumber).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Optional<Client> findByPersonalNumber(final String personalNumber) {
        return clients.entrySet().stream()
                .filter(e -> e.getValue().getPersonalNumber().equals(personalNumber))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public void delete(final Client client) {
        clients.remove(client.getAggregateId());
    }
}
