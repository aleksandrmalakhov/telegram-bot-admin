package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import ru.malakhov.botadmin.entity.BotAccountTelegramUser;
import ru.malakhov.botadmin.repository.BotAccountRepository;
import ru.malakhov.botadmin.service.BotAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BotAccountServiceImp implements BotAccountService {
    private final BotAccountRepository accountRepository;

    public BotAccountServiceImp(BotAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(BotAccountTelegramUser botAccount) {
        accountRepository.save(botAccount);
    }

    @Override
    public void delete(BotAccountTelegramUser botAccount) {
        accountRepository.delete(botAccount);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<BotAccountTelegramUser> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<BotAccountTelegramUser> findAll() {
        List<BotAccountTelegramUser> botAccounts = new ArrayList<>();
        accountRepository.findAll().forEach(botAccounts::add);
        return botAccounts;
    }
}