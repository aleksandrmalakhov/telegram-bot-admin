package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorCommand implements Command {
    final SendMessageService sendMessageService;
    public static final String ERR_MESSAGE = "Неизвестная ошибка!";

    public ErrorCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        sendMessageService.answerSendMessage(update, ERR_MESSAGE);
    }
}