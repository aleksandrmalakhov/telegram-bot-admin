package ru.malakhov.botadmin.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malakhov.botadmin.bot.TelegramBot;
import ru.malakhov.botadmin.components.MessageUtils;
import ru.malakhov.botadmin.service.SendMessageService;

@Slf4j
@Service
public class SendMessageServiceImp implements SendMessageService {
    TelegramBot telegramBot;
    final MessageUtils messageUtils;

    public SendMessageServiceImp(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void answerSendMessage(Update update, String textMessage) {
        try {
            telegramBot.execute(messageUtils.generateSendMessageWithText(update, textMessage));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}