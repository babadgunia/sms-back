package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Auditorium.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Auditorium extends AbstractEntity {

    static final String TABLE_NAME = "AUDITORIUM";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
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