package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Auditorium.SEQUENCE_NAME, sequenceName = Auditorium.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Auditorium extends AbstractEntity {

    static final String SEQUENCE_NAME = "AUDITORIUM" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    @ManyToOne
    private Building building;

    @ElementCollection
    private List<ExamSeat> seats = new ArrayList<>();

    public Auditorium(long id) {
        super(id);
    }

    public Auditorium(long id, String name) {
        this(id);

        this.name = name;
    }
}