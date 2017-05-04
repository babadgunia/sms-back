package org.test.sms.common.entities.general;

import org.hibernate.annotations.Type;
import org.test.sms.common.entities.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = ExceptionLog.SEQUENCE_NAME, sequenceName = ExceptionLog.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class ExceptionLog extends AppEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "EXCEPTION_LOG" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Column(length = AppEntity.STRING_FIELD_MAX_LENGTH)
    private String message;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String stackTrace;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}