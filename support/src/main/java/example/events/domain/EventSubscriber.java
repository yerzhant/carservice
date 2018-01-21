package example.events.domain;


import com.google.common.eventbus.Subscribe;

public interface EventSubscriber<T extends Event> {

    @Subscribe
    void subscribe(T event);
}
