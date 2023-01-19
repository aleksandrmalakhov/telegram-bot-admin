package ru.malakhov.botadmin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malakhov.botadmin.entity.TelegramChat;

@Repository
public interface TelegramChatRepository extends CrudRepository<TelegramChat, Long> {
}