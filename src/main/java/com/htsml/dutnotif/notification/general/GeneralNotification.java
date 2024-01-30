package com.htsml.dutnotif.notification.general;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "general_notifications")
public class GeneralNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Date date;

    @Column(columnDefinition = "BINARY(16)")
    private byte[] hash;
}
