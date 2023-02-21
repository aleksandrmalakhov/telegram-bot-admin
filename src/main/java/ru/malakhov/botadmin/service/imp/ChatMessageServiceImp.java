package ru.malakhov.botadmin.service.imp;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.malakhov.botadmin.entity.ChatMessage;
import ru.malakhov.botadmin.repository.ChatMessageRepository;
import ru.malakhov.botadmin.service.ChatMessageService;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageServiceImp implements ChatMessageService {
    private final ChatMessageRepository repository;

    public ChatMessageServiceImp(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ChatMessage chatMessage) {
        repository.save(chatMessage);
    }

    @Override
    public void delete(ChatMessage chatMessage) {
        repository.delete(chatMessage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ChatMessage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ChatMessage> findAllByChatId(Long chatId) {
        return repository.findAllByChatId(chatId);
    }

    @Override
    public void deleteAllByChatId(Long chatId) {
        var listMessage = this.findAllByChatId(chatId);

        if (listMessage != null) {
            listMessage.forEach(this::delete);
        }
    }
}