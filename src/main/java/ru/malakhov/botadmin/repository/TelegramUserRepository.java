package ru.malakhov.botadmin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.malakhov.botadmin.entity.TelegramUser;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE tg_user AS u SET u.msgNumber = u.msgNumber + 1 WHERE u.id IS NOT NULL AND u.id = :id")
    void updateMsgNumberByUserId(@Param("id") Long id);
}