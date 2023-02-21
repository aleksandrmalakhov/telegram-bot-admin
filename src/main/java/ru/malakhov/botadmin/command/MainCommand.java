package ru.malakhov.botadmin.command;

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

public class MainCommand implements Command {
    private final MessageUtils messageUtils;
    private final TelegramUserService userService;
    private final BotAccountService accountService;
    private final SendMessageService sendMessageService;
    public static final String MAIN_TEXT = "Main text";

    public MainCommand(MessageUtils messageUtils,
                       TelegramUserService userService,
                       BotAccountService accountService,
                       SendMessageService sendMessageService) {
        this.messageUtils = messageUtils;
        this.userService = userService;
        this.accountService = accountService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        var telegramUser = update.getMessage().getFrom();

        accountService.findById(telegramUser.getId()).ifPresentOrElse((acc) -> {
            //update user
            var user = acc.getTelegramUser();
            if (!user.isActive()) {
                user.setActive(true);
                accountService.save(acc);
            }
        }, () ->
                userService.findById(telegramUser.getId()).ifPresentOrElse((user) -> {
                    //create account
                    user.setActive(true);
                    createBotAccount(user);
                }, () -> {
                    //create user and account
                    var transientAppUser = new TelegramUser.Builder()
                            .id(telegramUser.getId())
                            .userName(telegramUser.getUserName())
                            .firstName(telegramUser.getFirstName())
                            .lastName(telegramUser.getLastName())
                            .isActive(true)
                            .build();

                    createBotAccount(transientAppUser);
                }));

        sendMessageService.answerSendMessage(messageUtils.createSendMessageWithKeyboard(chatId, MAIN_TEXT, MainKeyboard.create()));
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