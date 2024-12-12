package com.chatApp.model;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;


}
