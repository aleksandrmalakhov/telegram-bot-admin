package ru.malakhov.botadmin.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malakhov.botadmin.config.BotConfiguration;
import ru.malakhov.botadmin.service.SendMessageService;

import javax.annotation.PostConstruct;

import static ru.malakhov.botadmin.components.BotCommands.LIST_OF_COMMAND;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {
    final UpdateHandler updateHandler;
    final BotConfiguration botConfiguration;
    @Getter
    final SendMessageService sendMessageService;

    public TelegramBot(UpdateHandler updateHandler,
                       BotConfiguration botConfiguration,
                       SendMessageService sendMessageService) {
        this.updateHandler = updateHandler;
        this.botConfiguration = botConfiguration;
        this.sendMessageService = sendMessageService;
    }

    @PostConstruct
    public void init() {
        updateHandler.registerBot(this);
        sendMessageService.registerBot(this);
        try {
            execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.processUpdate(update);
    }
}