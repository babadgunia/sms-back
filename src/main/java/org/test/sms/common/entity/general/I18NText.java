package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = I18NText.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class I18NText extends AbstractEntity {

    static final String TABLE_NAME = "I18N_TEXT";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Column(name = "VALUE")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE")
    private LanguageType language;

    @ManyToOne
    @JoinColumn(name = "TEXT_ID")
    private Text text;

    public I18NText(long id) {
        super(id);
    }
}