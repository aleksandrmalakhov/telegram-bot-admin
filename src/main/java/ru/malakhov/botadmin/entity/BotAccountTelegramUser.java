package ru.malakhov.botadmin.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.malakhov.botadmin.entity.enums.AccountStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "acc_tg_user")
@EqualsAndHashCode(exclude = "id")
public class BotAccountTelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dataCreate;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToOne(mappedBy = "account")
    private TelegramUser telegramUser;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "account_channel",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Set<TelegramChannel> channels;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChannelSubscribe> channelsSubscribers;

    public BotAccountTelegramUser() {
        this.channels = new HashSet<>();
        this.channelsSubscribers = new ArrayList<>();
    }

    public void addChannel(TelegramChannel channel) {
        this.channels.add(channel);
        channel.getAdmins().add(this);
    }

    public void removeChannel(TelegramChannel channel) {
        this.channels.remove(channel);
        channel.getAdmins().remove(this);
    }
}