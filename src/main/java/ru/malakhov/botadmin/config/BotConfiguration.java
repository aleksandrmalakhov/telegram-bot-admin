package ru.malakhov.botadmin.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfiguration {
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;
    @Value("${bot.admin}")
    String admin;
}