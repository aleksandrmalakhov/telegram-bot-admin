package ru.malakhov.botadmin.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malakhov.botadmin.bot.TelegramBot;
import ru.malakhov.botadmin.components.MessageUtils;
import ru.malakhov.botadmin.entity.ChatMessage;
import ru.malakhov.botadmin.service.ChatMessageService;
import ru.malakhov.botadmin.service.SendMessageService;

@Slf4j
@Service
public class SendMessageServiceImp implements SendMessageService {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final ChatMessageService chatMessageService;

    public SendMessageServiceImp(MessageUtils messageUtils,
                                 ChatMessageService chatMessageService) {
        this.messageUtils = messageUtils;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void answerSendMessage(Update update, String textMessage) {
        try {
            var message = telegramBot.execute(messageUtils.generateSendMessageWithText(update, textMessage));
            chatMessageService.save(new ChatMessage(message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void answerSendMessage(SendMessage response) {
        try {
            var message = telegramBot.execute(response);
            chatMessageService.save(new ChatMessage(message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void answerSendMessage(EditMessageText response) {
        try {
            var message = (Message) telegramBot.execute(response);
            chatMessageService.save(new ChatMessage(message));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void answerSendMessage(DeleteMessage response) {
        try {
            telegramBot.executeAsync(response);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}