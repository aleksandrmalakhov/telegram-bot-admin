package ru.malakhov.botadmin.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.UpdateTextService;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHandler {
    TelegramBot telegramBot;
    final UpdateTextService updateTextService;

    public UpdateHandler(UpdateTextService updateTextService) {
        this.updateTextService = updateTextService;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null!");
            return;
        }

        if (update.hasMessage()) {
            distributeMessageByType(update);
        } else if (update.hasMyChatMember()) {
            chatUpdated(update);
        } else {
            log.error("Unsupported message type is received " + update);
        }
    }

    private void chatUpdated(Update update) {
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();

        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.getMigrateToChatId() != null) {
            
        } else {
            setUnsupportedTypeMessageView(update);
        }
    }

    private void processTextMessage(Update update) {
        updateTextService.processing(update);
    }

    private void setUnsupportedTypeMessageView(Update update) {
        telegramBot.getSendMessageService().answerSendMessage(update, "Неподдерживаемый тип сообщения!");
    }
}