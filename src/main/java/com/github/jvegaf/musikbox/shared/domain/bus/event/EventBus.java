package com.github.jvegaf.musikbox.shared.domain.bus.event;

import java.util.List;

public interface EventBus {

    void publish(final List<DomainEvent> events);
}
