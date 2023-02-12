package ru.malakhov.botadmin.command;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartCommand implements Command {
    final TelegramUserService userService;
    final SendMessageService sendMessageService;

    public StartCommand(TelegramUserService userService,
                        SendMessageService sendMessageService) {
        this.userService = userService;
        this.sendMessageService = sendMessageService;
    }


    @Override
    public void execute(@NonNull Update update) {
        AtomicReference<String> text = new AtomicReference<>();
        var telegramUser = update.getMessage().getFrom();
        var persistentAppUser = userService.findById(telegramUser.getId());

        persistentAppUser.ifPresentOrElse((user) -> {
            text.set(user.getFirstName() + ", с возвращением!");
            user.setActive(true);
            userService.save(user);
        }, () -> {
            text.set(telegramUser.getFirstName() + ", добро пожаловать!");
            var transientAppUser = new TelegramUser.Builder()
                    .id(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isActive(true)
                    .build();
            userService.save(transientAppUser);
        });
        sendMessageService.answerSendMessage(update, text.get());
    }
}