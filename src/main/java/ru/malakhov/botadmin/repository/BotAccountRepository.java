package ru.malakhov.botadmin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malakhov.botadmin.entity.BotAccountTelegramUser;

@Repository
public interface BotAccountRepository extends CrudRepository<BotAccountTelegramUser, Long> {
}