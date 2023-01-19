package ru.malakhov.botadmin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malakhov.botadmin.entity.ChatSubscribe;

@Repository
public interface ChatSubscribeRepository extends CrudRepository<ChatSubscribe, ChatSubscribe.SubscribeKey> {
}