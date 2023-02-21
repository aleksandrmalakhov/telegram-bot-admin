package ru.malakhov.botadmin.components;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class MessageUtils {
    public SendMessage generateSendMessageWithText(@NonNull Update update, String text) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(text).build();
    }

    public DeleteMessage createDeleteMessage(Long chatId, Integer messageId) {
        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }

    public SendMessage createSendMessageWithKeyboard(Long chatId, String text, ReplyKeyboard keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }
}