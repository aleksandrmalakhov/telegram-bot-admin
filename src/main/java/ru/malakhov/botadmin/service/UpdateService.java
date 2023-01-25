package ru.malakhov.botadmin.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void processing(Update update);
}