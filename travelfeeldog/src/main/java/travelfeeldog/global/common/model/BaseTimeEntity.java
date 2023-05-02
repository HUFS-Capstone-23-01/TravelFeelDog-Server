package travelfeeldog.global.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity implements Serializable {

    @CreatedDate
    @Column(name="created_time",updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name="updated_time")
    private LocalDateTime updatedDateTime;

    protected BaseTimeEntity(){}

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDateTime = now;
        updatedDateTime = now;
    }

    @PreUpdate
    public void preUpdate(){
        updatedDateTime = LocalDateTime.now();
    }

}
