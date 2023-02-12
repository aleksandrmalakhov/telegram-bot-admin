package ru.malakhov.botadmin.entity;

import lombok.*;
import ru.malakhov.botadmin.entity.enums.SubscribeStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Entity(name = "channel_subscribe")
@NoArgsConstructor
public class ChannelSubscribe {
    @EmbeddedId
    private SubscribeKey id;
    @Enumerated(EnumType.STRING)
    private SubscribeStatus status;
    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private BotAccountTelegramUser account;
    @ManyToOne
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private TelegramChannel channel;
    private LocalDateTime dateCreate;
    private LocalDateTime dateLastPayment;
    private LocalDateTime dateNextPayment;

    public ChannelSubscribe(BotAccountTelegramUser account, TelegramChannel channel) {
        this.id = new SubscribeKey(account.getId(), channel.getId());
    }

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscribeKey implements Serializable {
        @Column(name = "account_id")
        private Long accountId;
        @Column(name = "channel_id")
        private Long channelId;
    }
}
