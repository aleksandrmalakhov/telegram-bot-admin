package ru.malakhov.botadmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    Integer msgNumber;
}