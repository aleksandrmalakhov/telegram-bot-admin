package ru.malakhov.botadmin.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.BotAccountService;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.concurrent.atomic.AtomicReference;

public class DeleteMyData implements Command {
    private final TelegramUserService userService;
    private final BotAccountService accountService;
    private final SendMessageService sendMessageService;

    public DeleteMyData(TelegramUserService userService,
                        BotAccountService accountService,
                        SendMessageService sendMessageService) {
        this.userService = userService;
        this.accountService = accountService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        AtomicReference<String> text = new AtomicReference<>();
        var telegramUser = update.getMessage().getFrom();
        var persistentAcc = accountService.findById(telegramUser.getId());

        persistentAcc.ifPresentOrElse((acc) -> {
            //TODO обработать исключение
            var userAccount = userService.findById(acc.getId()).orElseThrow();
            userAccount.setActive(false);
            userService.save(userAccount);
            accountService.delete(acc);

            text.set("Ваши данные успешно удалены");
        }, () -> text.set("Ваши данные не найдены"));

        sendMessageService.answerSendMessage(update, text.get());
    }
}