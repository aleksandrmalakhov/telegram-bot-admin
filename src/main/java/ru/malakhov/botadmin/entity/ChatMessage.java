package ru.malakhov.botadmin.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "chat_message")
@EqualsAndHashCode(exclude = "id")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private Long userId;
    private Message message;

    public ChatMessage(Message message) {
        this.message = message;
        this.chatId = message.getChatId();
        this.userId = message.getFrom().getId();
    }
}