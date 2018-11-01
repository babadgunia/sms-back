package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @Column(name = "NAME")
    private String name;

    @ElementCollection
    @CollectionTable(name = "AUDITORIUM_SEAT", joinColumns = @JoinColumn(name = "AUDITORIUM_ID"))
    private List<ExamSeat> seats = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "BUILDING_ID")
    private Building building;

    public Auditorium(long id) {
        super(id);
    }
}