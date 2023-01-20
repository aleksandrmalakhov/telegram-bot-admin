package ru.malakhov.botadmin.service.imp;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.malakhov.botadmin.command.*;
import ru.malakhov.botadmin.service.CommandService;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

import static ru.malakhov.botadmin.command.enums.CommandName.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandServiceImp implements CommandService {
    final Command unknownCommand;
    final ImmutableMap<String, Command> commandMap;

    public CommandServiceImp(SendMessageService sendMessageService,
                             TelegramUserService userService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(userService, sendMessageService))
                .put(STOP.getCommandName(), new StopCommand(userService, sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(DELETE.getCommandName(), new DeleteMyData(userService, sendMessageService))
                .put(NO.getCommandName(), new NoCommand(sendMessageService))
                .put(ERROR.getCommandName(), new ErrorCommand(sendMessageService))
                .build();
        this.unknownCommand = new UnknownCommand(sendMessageService);
    }

    @Override
    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}