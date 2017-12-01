package org.test.sms.common.entity.general;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = I18nText.SEQUENCE_NAME, sequenceName = I18nText.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class I18nText extends AbstractEntity {

    static final String SEQUENCE_NAME = "I18N_TEXT" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Lob
    private String value;

    @ManyToOne
    @JsonIgnore
    private Text text;

    public I18nText(long id) {
        super(id);
    }
}