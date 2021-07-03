package qna.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class UpdatableEntity {
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private LocalDateTime updatedAt;
}
