package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = ExceptionLog.SEQUENCE_NAME, sequenceName = ExceptionLog.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@Getter @Setter
public class ExceptionLog extends AbstractEntity {

    static final String SEQUENCE_NAME = "EXCEPTION_LOG" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Lob
    private String message;

    @Lob
    private String stackTrace;
}