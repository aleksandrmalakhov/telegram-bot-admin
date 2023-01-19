package ru.malakhov.botadmin.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMAND = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );

    String HELP_TEXT = """
            This bot will help to count the number of messages int the chat. The following commands are available to you:\s

            /start - start the bot
            /help - help menu""";
}