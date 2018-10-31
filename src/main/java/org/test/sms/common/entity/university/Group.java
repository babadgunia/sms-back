package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Group.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Group extends AbstractEntity {

    static final String TABLE_NAME = "UNI_GROUP";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    private int groupNumber;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    private Module module;

    @ManyToOne
    private Auditorium auditorium;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToMany
    private List<Student> students = new ArrayList<>();

    public Group(long id) {
        super(id);
    }
}