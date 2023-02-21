package ru.malakhov.botadmin.service;

import ru.malakhov.botadmin.entity.BotAccountTelegramUser;

import java.util.List;
import java.util.Optional;

public interface BotAccountService {
    void save(BotAccountTelegramUser botAccount);

    void delete(BotAccountTelegramUser botAccount);
    void deleteById(Long id);

    Optional<BotAccountTelegramUser> findById(Long id);

    List<BotAccountTelegramUser> findAll();
}
