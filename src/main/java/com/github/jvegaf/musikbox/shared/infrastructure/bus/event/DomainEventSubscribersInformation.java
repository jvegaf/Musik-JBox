package com.github.jvegaf.musikbox.shared.infrastructure.bus.event;

import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.event.DomainEventSubscriber;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Service
public final class DomainEventSubscribersInformation {
    final HashMap<Class<?>, DomainEventSubscriberInformation> information;

    public DomainEventSubscribersInformation() {
        this(scanDomainEventSubscribers());
    }

    public DomainEventSubscribersInformation(HashMap<Class<?>, DomainEventSubscriberInformation> information) {
        this.information = information;
    }

    private static HashMap<Class<?>, DomainEventSubscriberInformation> scanDomainEventSubscribers() {
        Reflections   reflections = new Reflections("com.github.jvegaf");
        Set<Class<?>> subscribers = reflections.getTypesAnnotatedWith(DomainEventSubscriber.class);

        HashMap<Class<?>, DomainEventSubscriberInformation> subscribersInformation = new HashMap<>();

        for (Class<?> subscriberClass : subscribers) {
            DomainEventSubscriber annotation = subscriberClass.getAnnotation(DomainEventSubscriber.class);

            subscribersInformation.put(subscriberClass,
                                       new DomainEventSubscriberInformation(subscriberClass,
                                                                            Arrays.asList(annotation.value())));
        }

        return subscribersInformation;
    }

    public Collection<DomainEventSubscriberInformation> all() {
        return information.values();
    }

    public String[] rabbitMqFormattedNames() {
        return information.values()
                          .stream()
                          .map(DomainEventSubscriberInformation::formatRabbitMqQueueName)
                          .distinct()
                          .toArray(String[]::new);
    }
}
