package org.test.sms.common.entity;

import javax.persistence.Column;
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

    private LocalDateTime creationTime;

    private LocalDateTime lastModifiedTime;

    @Column(length = STRING_FIELD_MAX_LENGTH)
    private String description;

    @Version
    private int version;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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