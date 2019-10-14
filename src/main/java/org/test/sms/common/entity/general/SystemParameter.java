package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.CachePut;
import org.test.sms.common.enums.general.SystemParameterGroupType;
import org.test.sms.common.enums.general.SystemParameterType;
import org.test.sms.common.enums.general.SystemParameterUnitType;
import org.test.sms.common.utils.DateTimeUtils;
import org.test.sms.common.utils.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = SystemParameter.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@Getter @Setter
@CachePut("SystemParameter")
public class SystemParameter extends AbstractEntity {

    static final String TABLE_NAME = "SYSTEM_PARAMETER";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROUP_TYPE")
    private SystemParameterGroupType group;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private SystemParameterType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "UNIT")
    private SystemParameterUnitType unit;

    public SystemParameter(long id) {
        super(id);
    }

    public <T> T getActualValue(Class<T> clazz) {
        Object result;

        switch (type) {
            case BOOLEAN:
                result = Boolean.parseBoolean(value);

                break;
            case STRING:
                result = value;

                break;
            case INTEGER:
                result = Integer.parseInt(value);

                break;
            case LONG:
                result = Long.parseLong(value);

                break;
            case DOUBLE:
                result = Double.parseDouble(value);

                break;
            case LIST_STRING:
                result = Utils.toList(value, ",", String.class);

                break;
            default:
                result = value;
        }

        return clazz.cast(result);
    }

    public long getUnitMultiplier() {
        switch (unit) {
            case MILLISECOND:
                return 1;
            case SECOND:
                return DateTimeUtils.MILLIS_IN_SECOND;
            case MINUTE:
                return DateTimeUtils.MILLIS_IN_MINUTE;
            case HOUR:
                return DateTimeUtils.MILLIS_IN_HOUR;
            default:
                return 1;
        }
    }
}