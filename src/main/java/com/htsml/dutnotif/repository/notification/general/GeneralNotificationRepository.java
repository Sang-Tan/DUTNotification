package com.htsml.dutnotif.repository.notification.general;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GeneralNotificationRepository extends JpaRepository<GeneralNotification, Long> {
    boolean existsByHash(byte[] hash);
}
