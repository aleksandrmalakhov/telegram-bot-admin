package ru.malakhov.botadmin.command.enums;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    DELETE("/delete"),
    NO("noCommand"),
    ERROR("error");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}