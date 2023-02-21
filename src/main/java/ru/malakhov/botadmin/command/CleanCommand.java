package ru.malakhov.botadmin.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.components.MessageUtils;
import ru.malakhov.botadmin.keyboard.MainKeyboard;
import ru.malakhov.botadmin.service.ChatMessageService;
import ru.malakhov.botadmin.service.SendMessageService;

import static ru.malakhov.botadmin.command.MainCommand.MAIN_TEXT;

public class CleanCommand implements Command {
    private final MessageUtils messageUtils;
    private final ChatMessageService chatMessageService;
    private final SendMessageService sendMessageService;

    public CleanCommand(MessageUtils messageUtils,
                        ChatMessageService chatMessageService,
                        SendMessageService sendMessageService) {
        this.messageUtils = messageUtils;
        this.chatMessageService = chatMessageService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        var messages = chatMessageService.findAllByChatId(chatId);

        if (messages != null) {
            messages.forEach(message -> sendMessageService.answerSendMessage(messageUtils.createDeleteMessage(message.getChatId(), message.getMessage().getMessageId())));
        }
        sendMessageService.answerSendMessage(messageUtils.createSendMessageWithKeyboard(chatId, MAIN_TEXT, MainKeyboard.create()));
    }
}