package example.events.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public abstract class Event {

    private final String uuid;

    private final Long id;

    public Event(final Long id) {
        this.id = id;
        this.uuid = UUID.randomUUID().toString();
    }
}
