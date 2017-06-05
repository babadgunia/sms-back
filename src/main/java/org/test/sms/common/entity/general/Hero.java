package org.test.sms.common.entity.general;

import org.test.sms.common.entity.AppEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = Hero.SEQUENCE_NAME, sequenceName = Hero.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class Hero extends AppEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "HERO" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    public Hero() {
    }

    public Hero(long id) {
        this.id = id;
    }

    public Hero(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}