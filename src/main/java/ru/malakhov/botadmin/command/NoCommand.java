package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoCommand implements Command {
    final SendMessageService sendMessageService;
    public static final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(@NonNull Update update) {
        sendMessageService.answerSendMessage(update, NO_MESSAGE);
    }
}