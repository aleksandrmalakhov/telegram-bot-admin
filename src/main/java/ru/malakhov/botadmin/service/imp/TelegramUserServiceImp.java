package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.repository.TelegramUserRepository;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImp implements TelegramUserService {
    private final TelegramUserRepository repository;

    public TelegramUserServiceImp(TelegramUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(TelegramUser telegramUser) {
        repository.save(telegramUser);
    }

    @Override
    public void delete(TelegramUser telegramUser) {
        repository.delete(telegramUser);
    }

    @Override
    public Optional<TelegramUser> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<TelegramUser> findByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public List<TelegramUser> findAll() {
        List<TelegramUser> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }
}