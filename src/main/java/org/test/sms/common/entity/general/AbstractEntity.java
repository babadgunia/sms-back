package org.test.sms.common.entity.general;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public abstract class AbstractEntity implements Serializable {

    protected static final String SEQUENCE_PREFIX = "SQ_";

    protected static final int SEQUENCE_ALLOCATION_SIZE = 1;

    @Column(name = "CREATED")
    private ZonedDateTime created;

    @Column(name = "LAST_MODIFIED")
    private ZonedDateTime lastModified;

    @Version
    @Column(name = "VERSION")
    private long version;

    protected AbstractEntity(long id) {
        setId(id);
    }

    public abstract long getId();

    public abstract void setId(long id);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractEntity && ((AbstractEntity) obj).getId() == getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    @PrePersist
    private void prePersist() {}

    @PreUpdate
    private void preUpdate() {}

    @PreRemove
    private void preRemove() {}
}