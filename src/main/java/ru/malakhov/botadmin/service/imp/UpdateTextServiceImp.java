package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.entity.ChatMessage;
import ru.malakhov.botadmin.service.ChatMessageService;
import ru.malakhov.botadmin.service.CommandService;
import ru.malakhov.botadmin.service.UpdateTextService;

@Service
public class UpdateTextServiceImp implements UpdateTextService {
    private final CommandService commandService;
    private final ChatMessageService chatMessageService;

    public UpdateTextServiceImp(CommandService commandService,
                                ChatMessageService chatMessageService) {
        this.commandService = commandService;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public void processing(Update update) {
        var commandPrefix = "/";
        var message = update.getMessage();
        var text = message.getText();

        if (text.startsWith(commandPrefix)) {
            var commandIdentifier = text.split(" ")[0].toLowerCase();
            commandService.retrieveCommand(commandIdentifier).execute(update);
        } else {
            commandService.retrieveCommand("noCommand").execute(update);
        }

        chatMessageService.save(new ChatMessage(message));
    }
}