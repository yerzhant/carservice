package example.events.domain;

public interface EventBus {

    void register(EventSubscriber eventSubscriber);

    void publish(Event event);

    static EventBus singletonDefaultBus() {
        return  GuavaEventBus.getDefaultInstance();
    }
}
