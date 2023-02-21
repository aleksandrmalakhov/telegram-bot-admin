package ru.malakhov.botadmin.service.imp;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.malakhov.botadmin.command.*;
import ru.malakhov.botadmin.components.MessageUtils;
import ru.malakhov.botadmin.service.*;

import static ru.malakhov.botadmin.command.enums.CommandName.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandServiceImp implements CommandService {
    final Command unknownCommand;
    final ImmutableMap<String, Command> commandMap;

    public CommandServiceImp(MessageUtils messageUtils,
                             TelegramUserService userService,
                             BotAccountService accountService,
                             ChatMessageService chatMessageService,
                             SendMessageService sendMessageService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(MAIN.getCommandName(), new MainCommand(messageUtils, userService, accountService, sendMessageService))
                .put(START.getCommandName(), new StartCommand(messageUtils, userService, accountService, sendMessageService))
                .put(STOP.getCommandName(), new StopCommand(userService, sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(CLEAN.getCommandName(), new CleanCommand(messageUtils, chatMessageService, sendMessageService))
                .put(DELETE.getCommandName(), new DeleteMyData(userService, accountService, sendMessageService))
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