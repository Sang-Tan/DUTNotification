package com.htsml.dutnotif.repository.notification.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupNotificationRepository extends JpaRepository<GroupNotification, Long> {
    boolean existsByHash(byte[] hash);
}
