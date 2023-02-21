package ru.malakhov.botadmin.command;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.components.MessageUtils;
import ru.malakhov.botadmin.entity.BotAccountTelegramUser;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.entity.enums.AccountStatus;
import ru.malakhov.botadmin.keyboard.MainKeyboard;
import ru.malakhov.botadmin.service.BotAccountService;
import ru.malakhov.botadmin.service.SendMessageService;
import ru.malakhov.botadmin.service.TelegramUserService;

import static ru.malakhov.botadmin.command.MainCommand.MAIN_TEXT;

public class StartCommand implements Command {
    private final MessageUtils messageUtils;
    private final TelegramUserService userService;
    private final BotAccountService accountService;
    private final SendMessageService sendMessageService;

    public StartCommand(MessageUtils messageUtils,
                        TelegramUserService userService,
                        BotAccountService accountService,
                        SendMessageService sendMessageService) {
        this.messageUtils = messageUtils;
        this.userService = userService;
        this.accountService = accountService;
        this.sendMessageService = sendMessageService;
    }


    @Override
    public void execute(@NonNull Update update) {
        var chatId = update.getMessage().getChatId();
        var telegramUser = update.getMessage().getFrom();
        var persistentAppUser = userService.findById(telegramUser.getId());

        persistentAppUser.ifPresentOrElse((user) -> {
            //TODO подумать про создание аккаунта
            //сделать отправку сообщений в отдельный поток???
            sendMessageService.answerSendMessage(update, user.getFirstName() + ", с возвращением!");

            if (!user.isActive()) {
                user.setActive(true);

                userService.save(user);
            }

            if (user.getAccount() == null) {
                createBotAccount(user);
            }
        }, () -> {
            //сделать отправку сообщений в отдельный поток???
            sendMessageService.answerSendMessage(update, telegramUser.getFirstName() + ", добро пожаловать!");

            var transientAppUser = new TelegramUser.Builder()
                    .id(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isActive(true)
                    .build();

            var botAccount = new BotAccountTelegramUser();
            botAccount.setFirstName(transientAppUser.getFirstName());
            botAccount.setLastName(transientAppUser.getLastName());
            botAccount.setTelegramUser(transientAppUser);
            botAccount.setStatus(AccountStatus.TRIAL);

            transientAppUser.setAccount(botAccount);
            userService.save(transientAppUser);
        });

        var mainMessage = messageUtils.createSendMessageWithKeyboard(chatId, MAIN_TEXT, MainKeyboard.create());
        sendMessageService.answerSendMessage(mainMessage);
    }

    private void createBotAccount(TelegramUser user) {
        var botAccount = new BotAccountTelegramUser();
        botAccount.setFirstName(user.getFirstName());
        botAccount.setLastName(user.getLastName());
        botAccount.setTelegramUser(user);
        botAccount.setStatus(AccountStatus.TRIAL);

        accountService.save(botAccount);
    }
}