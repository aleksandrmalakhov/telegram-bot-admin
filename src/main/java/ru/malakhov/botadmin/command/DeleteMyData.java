package ru.malakhov.botadmin.command;

import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.concurrent.atomic.AtomicReference;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class DeleteMyData implements Command {
    final TelegramUserService userService;
    final SendMessageService sendMessageService;

    public DeleteMyData(TelegramUserService userService, SendMessageService sendMessageService) {
        this.userService = userService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        AtomicReference<String> text = new AtomicReference<>();
        var telegramUser = update.getMessage().getFrom();
        var persistentAppUser = userService.findById(telegramUser.getId());

        persistentAppUser.ifPresentOrElse((user) -> {
            userService.delete(user);
            text.set("Ваши данные успешно удалены");
        }, () -> text.set("Ваши данные не найдены"));

        sendMessageService.answerSendMessage(update, text.get());
    }
}