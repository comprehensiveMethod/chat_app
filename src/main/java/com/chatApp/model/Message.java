package com.chatApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Setter
    @Getter
    @Column
    private LocalDateTime timeStamp;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
