package ru.malakhov.botadmin.service;

import ru.malakhov.botadmin.command.Command;

public interface CommandService {
    Command retrieveCommand(String commandIdentifier);
}