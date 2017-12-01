package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.general.ActionType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = ActionLog.SEQUENCE_NAME, sequenceName = ActionLog.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@Getter @Setter
public class ActionLog extends AbstractEntity {

    static final String SEQUENCE_NAME = "ACTION_LOG" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private ActionType type;

    @Lob
    private String info;

    private String username;

    private String ipAddress;
}