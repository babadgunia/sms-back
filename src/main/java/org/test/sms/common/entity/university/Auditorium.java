package org.test.sms.common.entity.university;

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

    public Auditorium() {}

    public Auditorium(long id) {
        super(id);
    }

    public Auditorium(long id, String name) {
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<ExamSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<ExamSeat> seats) {
        this.seats = seats;
    }
}