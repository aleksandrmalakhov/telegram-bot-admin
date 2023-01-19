package ru.malakhov.botadmin.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.malakhov.botadmin.entity.enums.ChatRole;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chat_subscribe")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatSubscribe {
    @EmbeddedId
    SubscribeKey id;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "tg_user", insertable = false, updatable = false)
    TelegramUser tgUser;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "tg_chat", insertable = false, updatable = false)
    TelegramChat tgChat;
    @Enumerated(EnumType.STRING)
    ChatRole role;

    public ChatSubscribe(TelegramUser tgUser, TelegramChat tgChat, ChatRole role) {
        this.id = new SubscribeKey(tgUser.getId(), tgChat.getId());
        this.tgUser = tgUser;
        this.tgChat = tgChat;
        this.role = role;
    }

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscribeKey implements Serializable {
        @Column(name = "tg_user")
        private Long tgUser;
        @Column(name = "tg_chat")
        private Long tgChat;
    }
}