package ru.malakhov.botadmin.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malakhov.botadmin.components.Buttons;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.repository.TelegramUserRepository;

import javax.annotation.PostConstruct;

import static ru.malakhov.botadmin.components.BotCommands.HELP_TEXT;
import static ru.malakhov.botadmin.components.BotCommands.LIST_OF_COMMAND;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

    final TelegramUserRepository telegramUserRepository;

    public TelegramBot(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @PostConstruct
    public void init() {
        try {
            execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        User user;
        long chatId;
        String receivedMessage;

        if (update.hasMessage()) {
            var message = update.getMessage();
            user = message.getFrom();
            chatId = message.getChatId();

            if (message.hasText()) {
                receivedMessage = message.getText();
                botAnswerUtils(receivedMessage, chatId, user.getFirstName());
                updateTelegramUsers(user);
            } else {
                sendAnswer(message);
                log.error(update.toString());
            }
        } else if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            chatId = callbackQuery.getMessage().getChatId();
            user = callbackQuery.getFrom();
            receivedMessage = callbackQuery.getData();

            botAnswerUtils(receivedMessage, chatId, user.getFirstName());
            updateTelegramUsers(user);
        } else {
            log.error(update.toString());
        }
    }

    private void updateTelegramUsers(User user) {
        if (telegramUserRepository.findById(user.getId()).isEmpty()) {
            var tgUser = new TelegramUser();
            tgUser.setId(user.getId());
            tgUser.setFirstName(user.getFirstName());
            tgUser.setLastName(user.getLastName());
            tgUser.setUserName(user.getUserName());
            tgUser.setIsActive(true);
            tgUser.setMsgNumber(1);

            telegramUserRepository.save(tgUser);
            log.info("Added to DB: " + tgUser);
        } else {
            telegramUserRepository.updateMsgNumberByUserId(user.getId());
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage) {
            case "/start" -> startBot(chatId, userName);
            case "/help" -> sendHelpText(chatId);
            default -> sendError(chatId);
        }
    }

    private void startBot(long chatId, String userName) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi " + userName + "! I'm a Telegram bot.");
        message.setReplyMarkup(Buttons.inlineMarkup());

        sendAnswer(message);
    }

    private void sendHelpText(long chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(HELP_TEXT);

        sendAnswer(message);
    }

    private void sendError(long chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Error");

        sendAnswer(message);
    }


    private void sendAnswer(Message message) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Я вас не понимаю");

        try {
            log.info("Reply sent");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendAnswer(SendMessage message) {
        try {
            log.info("Reply sent");
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}