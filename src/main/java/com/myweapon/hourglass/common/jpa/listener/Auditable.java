package com.myweapon.hourglass.common.jpa.listener;

import com.myweapon.hourglass.common.embeddable.DateAudit;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Auditable {
    public Optional<DateAudit> getDateAudit();
    public Optional<LocalDateTime> getCreateAt();
    public Optional<LocalDateTime> getUpdatedAt();

    public void setDateAudit(DateAudit dateAudit);
    public void setCreatedAt(LocalDateTime createAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
}
