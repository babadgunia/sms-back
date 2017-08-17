package org.test.sms.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AppEntity implements Serializable {

    public static final int SEQUENCE_ALLOCATION_SIZE = 1;

    protected static final String SEQUENCE_PREFIX = "SMS_";

    protected static final String SEQUENCE_SUFFIX = "_SEQ";

    protected static final int STRING_FIELD_MAX_LENGTH = 4000;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime creationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime lastModifiedTime;

    @Version
    private int version;

    protected AppEntity() {}

    protected AppEntity(long id) {
        setId(id);
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public abstract long getId();

    public abstract void setId(long id);

    @Override
    public boolean equals(Object obj) {
        return Objects.nonNull(obj) && obj instanceof AppEntity && ((AppEntity) obj).getId() == getId();
    }

    @Override
    public int hashCode() {
        return new Long(getId()).hashCode();
    }
}