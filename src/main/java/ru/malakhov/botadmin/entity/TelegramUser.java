package ru.malakhov.botadmin.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "tg_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramUser {
    @Id
    Long id;
    @CreationTimestamp
    LocalDateTime firstLoginDate;
    String firstName;
    String lastName;
    String userName;
    String email;
    Boolean isActive;

    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH},
            mappedBy = "tgUser")
    List<ChatSubscribe> chatSubscribes;

    public TelegramUser() {
        this.chatSubscribes = new ArrayList<>();
    }

    public static class Builder {
        private final TelegramUser telegramUser;

        public Builder() {
            this.telegramUser = new TelegramUser();
        }

        public Builder id(Long id) {
            telegramUser.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            telegramUser.firstName = firstName;
            return this;

        }

        public Builder lastName(String lastName) {
            telegramUser.lastName = lastName;
            return this;
        }

        public Builder userName(String userName) {
            telegramUser.userName = userName;
            return this;
        }

        public Builder email(String email) {
            telegramUser.email = email;
            return this;
        }

        public Builder isActive(Boolean isActive) {
            telegramUser.isActive = isActive;
            return this;
        }

        public TelegramUser build() {
            return telegramUser;
        }
    }
}