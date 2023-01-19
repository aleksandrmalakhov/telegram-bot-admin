package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import ru.malakhov.botadmin.entity.TelegramUser;
import ru.malakhov.botadmin.repository.TelegramUserRepository;
import ru.malakhov.botadmin.service.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramUserServiceImp implements TelegramUserService {
    private final TelegramUserRepository repository;

    public TelegramUserServiceImp(TelegramUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public TelegramUser findById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public TelegramUser findByUsername(String username) {
        return repository.findByUserName(username).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<TelegramUser> findAll() {
        List<TelegramUser> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }
}