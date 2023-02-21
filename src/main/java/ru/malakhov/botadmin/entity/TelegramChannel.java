package ru.malakhov.botadmin.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tg_channel")
@EqualsAndHashCode(exclude = "id")
public class TelegramChannel {
    @Id
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String userName;
    private String description;
    @ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY)
    private Set<BotAccountTelegramUser> admins;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ChannelSubscribe> subscribes;

    public TelegramChannel() {
        this.admins = new HashSet<>();
        this.subscribes = new HashSet<>();
    }
}
