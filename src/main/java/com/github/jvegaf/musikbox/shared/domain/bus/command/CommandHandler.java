package com.github.jvegaf.musikbox.shared.domain.bus.command;

public interface CommandHandler<T extends Command> {

    void handle(T command);
}
