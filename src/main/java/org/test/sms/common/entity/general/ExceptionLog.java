package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.ExceptionLogType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = ExceptionLog.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class ExceptionLog extends AbstractEntity {

    static final String TABLE_NAME = "EXCEPTION_LOG";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private ExceptionLogType type;

    @Lob
    @Column(name = "INFO")
    private String info;

    @Lob
    @Column(name = "STACK_TRACE")
    private String stackTrace;

    public ExceptionLog(long id) {
        super(id);
    }
}