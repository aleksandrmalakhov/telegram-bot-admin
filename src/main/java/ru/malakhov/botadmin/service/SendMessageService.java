package ru.malakhov.botadmin.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.bot.TelegramBot;

public interface SendMessageService {
    void registerBot(TelegramBot telegramBot);
    void answerSendMessage(Update update, String textMessage);
}