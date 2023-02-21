package ru.malakhov.botadmin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malakhov.botadmin.entity.ChatMessage;

import java.util.List;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatId(Long chatId);
}