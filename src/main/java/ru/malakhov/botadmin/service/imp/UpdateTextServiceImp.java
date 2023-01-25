package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.malakhov.botadmin.service.CommandService;
import ru.malakhov.botadmin.service.UpdateTextService;

@Service
public class UpdateTextServiceImp implements UpdateTextService {
    final CommandService commandService;

    public UpdateTextServiceImp(CommandService commandService) {
        this.commandService = commandService;
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
    }
}