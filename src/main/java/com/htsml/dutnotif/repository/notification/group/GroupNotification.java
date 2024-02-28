package com.htsml.dutnotif.repository.notification.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "group_notifications")
public class GroupNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private String groupId;

    private String title;

    private String content;

    @Column(columnDefinition = "BINARY(16)")
    private byte[] hash;
}
