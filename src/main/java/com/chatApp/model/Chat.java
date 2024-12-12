package com.chatApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "chats")
    private List<User> participants;

    @Setter
    @Getter
    @OneToMany(mappedBy = "chat")
    List<Message> messages;

}
