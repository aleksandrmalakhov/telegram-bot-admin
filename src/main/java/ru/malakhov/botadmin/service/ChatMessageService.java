package ru.malakhov.botadmin.service;

import ru.malakhov.botadmin.entity.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    void save(ChatMessage chatMessage);

    void delete(ChatMessage chatMessage);

    void deleteById(Long id);

    Optional<ChatMessage> findById(Long id);

    List<ChatMessage> findAllByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);
}