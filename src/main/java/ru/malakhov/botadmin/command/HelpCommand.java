package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;

import static ru.malakhov.botadmin.command.enums.CommandName.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpCommand implements Command {
    final SendMessageService sendMessageService;
    static final String HELP_MSG_USER = String.format("""
                    ✨<b>Дотупные команды</b>✨

                    %s - начать работу со мной
                    %s - приостановить работу со мной
                    %s - помощь в работе со мной
                    %s - удалить мои данные
                    """,
            START.getCommandName(),
            STOP.getCommandName(),
            HELP.getCommandName(),
            DELETE.getCommandName());

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(@NonNull Update update) {
        sendMessageService.answerSendMessage(update, HELP_MSG_USER);
    }
}