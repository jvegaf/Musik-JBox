package me.jvegaf.musikbox.shared.infrastructure.bus.command;

import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.Command;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryCommandBus implements CommandBus {

    private final CommandHandlersInformation information;
    private final ApplicationContext         context;


    public InMemoryCommandBus(CommandHandlersInformation information, ApplicationContext context) {
        this.information = information;
        this.context     = context;
    }

    @Override
    public void dispatch(Command command) throws CommandHandlerExecutionError {
        try {
            Class<? extends CommandHandler> commandHandlerClass = information.search(command.getClass());

            CommandHandler handler = context.getBean(commandHandlerClass);

            handler.handle(command);
        } catch (Throwable error) {
            throw new CommandHandlerExecutionError(error);
        }
    }
}
