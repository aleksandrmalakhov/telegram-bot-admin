package ru.malakhov.botadmin.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.malakhov.botadmin.entity.enums.TypeChat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tg_chat")
@EqualsAndHashCode(exclude = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramChat {
    @Id
    Long id;
    String title;
    String firstName;
    String lastName;
    String userName;
    String description;
    @Enumerated(EnumType.STRING)
    TypeChat typeChat;

    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH},
            mappedBy = "tgChat")
    List<ChatSubscribe> chatSubscribes;

    public TelegramChat() {
        this.chatSubscribes = new ArrayList<>();
    }
}