package ru.malakhov.botadmin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.malakhov.botadmin.entity.TelegramUser;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    Optional<TelegramUser> findByUserName(String userName);
}