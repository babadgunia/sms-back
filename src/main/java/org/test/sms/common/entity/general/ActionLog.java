package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.ActionLogType;

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
@Table(name = ActionLog.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class ActionLog extends AbstractEntity {

    static final String TABLE_NAME = "ACTION_LOG";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private ActionLogType type;

    @Lob
    @Column(name = "INFO")
    private String info;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    public ActionLog(long id) {
        super(id);
    }
}