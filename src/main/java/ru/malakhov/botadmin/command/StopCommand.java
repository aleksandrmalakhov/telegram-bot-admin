package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopCommand implements Command {
    final TelegramUserService userService;
    final SendMessageService sendMessageService;

    public StopCommand(TelegramUserService userService, SendMessageService sendMessageService) {
        this.userService = userService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(@NonNull Update update) {
        var telegramUser = update.getMessage().getFrom();
        var appUser = userService.findById(telegramUser.getId());

        appUser.ifPresentOrElse((user) -> {
            user.setIsActive(false);
            userService.save(user);

            sendMessageService.answerSendMessage(update, user.getFirstName() + ", до новых встреч!");
        }, () -> {
            var text = telegramUser.getFirstName() + ", ваших данных нет в базе.\n Введи команду /start и начнем наше знакомство!";
            sendMessageService.answerSendMessage(update, text);
        });
    }
}