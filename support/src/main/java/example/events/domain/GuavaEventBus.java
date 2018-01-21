package example.events.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class GuavaEventBus implements EventBus {

    private final com.google.common.eventbus.EventBus eventBus;
    private static GuavaEventBus instance = null;


    static synchronized GuavaEventBus getDefaultInstance() {
        if(instance == null) {
            synchronized (GuavaEventBus.class) {
                if(instance == null) {
                    instance = new GuavaEventBus();
                }
            }
        }
        return instance;
    }

    private GuavaEventBus() {
        this.eventBus = new com.google.common.eventbus.EventBus();
    }

    @Autowired
    public GuavaEventBus(final com.google.common.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(final EventSubscriber eventSubscriber) {
        log.debug("Register event: {}", eventSubscriber);
        eventBus.register(eventSubscriber);
    }

    @Override
    public void publish(final Event event) {
        log.debug("Publish event: {}", event);
        eventBus.post(event);
    }
}
