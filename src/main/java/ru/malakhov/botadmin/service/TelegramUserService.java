package ru.malakhov.botadmin.service;

import ru.malakhov.botadmin.entity.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    TelegramUser findById(Long id);

    TelegramUser findByUsername(String username);

    List<TelegramUser> findAll();
}