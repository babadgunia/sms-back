package org.test.sms.common.entity.general;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Text.SEQUENCE_NAME, sequenceName = Text.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
public class Text extends AbstractEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "TEXT" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Column(name = "textKey")
    private String key;

    @OneToMany(mappedBy = "text", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<I18NText> values = new ArrayList<>();

    public Text() {}

    public Text(long id) {
        super(id);
    }

    public Text(long id, String key) {
        this(id);

        this.key = key;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<I18NText> getValues() {
        return values;
    }

    public void setValues(List<I18NText> values) {
        this.values = values;
    }
}