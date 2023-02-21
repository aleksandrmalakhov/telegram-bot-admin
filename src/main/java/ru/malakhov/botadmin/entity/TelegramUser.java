package ru.malakhov.botadmin.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tg_user")
@EqualsAndHashCode(exclude = "id")
public class TelegramUser {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private boolean isActive;

    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private BotAccountTelegramUser account;

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

        public Builder isActive(boolean active) {
            telegramUser.isActive = active;
            return this;
        }

        public TelegramUser build() {
            return telegramUser;
        }
    }
}