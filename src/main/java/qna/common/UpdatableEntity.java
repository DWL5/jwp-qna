package qna.common;

import javax.persistence.Column;
import java.time.LocalDateTime;

public abstract class UpdatableEntity {
    @Column(nullable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();
    protected LocalDateTime updatedAt;
}
