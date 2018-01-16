package pl.beck.vehicleworkshop.domain;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

class ClientRepositoryInMemoryImpl implements ClientRepository {

    private final Map<Long, Client> clients = new ConcurrentHashMap<>();

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public void save(final Client client) {
        requireNonNull(client);
        final long id = atomicLong.getAndIncrement();

        final Field id1 = ReflectionUtils.findField(client.getClass(), "id");
        ReflectionUtils.makeAccessible(id1);
        ReflectionUtils.setField(id1, client, id);
        clients.put(client.getId(), client);
    }

    @Override
    public Optional<Client> findOne(final Long id) {
        return Optional.ofNullable(clients.get(id));
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
        clients.remove(client);
    }
}
