package com.github.jvegaf.musikbox.shared.infrastructure.bus.command;

import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandNotRegisteredError;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

@Log4j2
@Service
public final class CommandHandlersInformation {
    HashMap<Class<? extends Command>, Class<? extends CommandHandler>> indexedCommandHandlers;

    public CommandHandlersInformation() {
        Reflections                          reflections = new Reflections("com.github.jvegaf");
        Set<Class<? extends CommandHandler>> classes     = reflections.getSubTypesOf(CommandHandler.class);

        indexedCommandHandlers = formatHandlers(classes);

    }

    private HashMap<Class<? extends Command>, Class<? extends CommandHandler>> formatHandlers(
            Set<Class<? extends CommandHandler>> commandHandlers
    ) {
        HashMap<Class<? extends Command>, Class<? extends CommandHandler>> handlers = new HashMap<>();

        for (Class<? extends CommandHandler> handler : commandHandlers) {
            ParameterizedType        paramType    = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];

            handlers.put(commandClass, handler);
        }

        return handlers;
    }

    public Class<? extends CommandHandler> search(Class<? extends Command> commandClass) throws
                                                                                         CommandNotRegisteredError {
        Class<? extends CommandHandler> commandHandlerClass = indexedCommandHandlers.get(commandClass);

        if (null==commandHandlerClass) {
            throw new CommandNotRegisteredError(commandClass);
        }

        return commandHandlerClass;
    }
}
