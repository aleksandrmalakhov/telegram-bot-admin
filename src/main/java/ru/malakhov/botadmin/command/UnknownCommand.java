package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnknownCommand implements Command {
    final SendMessageService sendMessageService;
    public static final String UNKNOWN_MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите /help чтобы узнать что я понимаю.";

    public UnknownCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(@NonNull Update update) {
        sendMessageService.answerSendMessage(update, UNKNOWN_MESSAGE);
    }
}