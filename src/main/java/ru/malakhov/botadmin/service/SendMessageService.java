package ru.malakhov.botadmin.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.bot.TelegramBot;

public interface SendMessageService {
    void registerBot(TelegramBot telegramBot);

    void answerSendMessage(Update update, String textMessage);

    void answerSendMessage(SendMessage response);

    void answerSendMessage(EditMessageText response);

    void answerSendMessage(DeleteMessage response);
}