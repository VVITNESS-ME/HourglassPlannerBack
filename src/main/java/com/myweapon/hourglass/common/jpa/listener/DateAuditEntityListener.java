package com.myweapon.hourglass.common.jpa.listener;

import com.myweapon.hourglass.common.embeddable.DateAudit;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class DateAuditEntityListener {
    @PrePersist
    public void prePersist(Object o){
        if(!(o instanceof Auditable)){
            log.info("not Auditable object");
            return;
        }
        Auditable auditable = (Auditable)o;
        Optional<DateAudit> optionalDateAudit = auditable.getDateAudit();
        if(optionalDateAudit.isEmpty()){
            auditable.setDateAudit(DateAudit.createEmpty());
        }
        auditable.setCreatedAt(LocalDateTime.now());
        auditable.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void postPersist(Object o){
        if(o instanceof Auditable){
            ((Auditable)o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
