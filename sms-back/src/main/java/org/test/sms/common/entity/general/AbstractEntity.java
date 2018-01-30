package org.test.sms.common.entity.general;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public abstract class AbstractEntity implements Serializable {

    public static final int SEQUENCE_ALLOCATION_SIZE = 1;

    protected static final String SEQUENCE_SUFFIX = "_SEQ";

    private LocalDateTime creationTime;

    private LocalDateTime lastModifiedTime;

    @Version
    private int version;

    protected AbstractEntity(long id) {
        setId(id);
    }

    public abstract long getId();

    public abstract void setId(long id);

    @Override
    public boolean equals(Object obj) {
        return Objects.nonNull(obj) && obj instanceof AbstractEntity && ((AbstractEntity) obj).getId() == getId();
    }

    @Override
    public int hashCode() {
        return new Long(getId()).hashCode();
    }
}