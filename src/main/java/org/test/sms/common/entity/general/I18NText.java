package org.test.sms.common.entity.general;

import org.test.sms.common.enums.general.LanguageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = I18NText.SEQUENCE_NAME, sequenceName = I18NText.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
public class I18NText extends AbstractEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "I18N_TEXT" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(length = AbstractEntity.STRING_FIELD_MAX_LENGTH)
    private String value;

    @ManyToOne
    private Text text;

    public I18NText() {}

    public I18NText(long id) {
        super(id);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public void setLanguage(LanguageType language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}