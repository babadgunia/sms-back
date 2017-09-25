package org.test.sms.common.entity.university;

import org.test.sms.common.entity.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Building.SEQUENCE_NAME, sequenceName = Building.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
public class Building extends AbstractEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "BUILDING" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    private String address;

    private Double lat;

    private Double lon;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auditorium> auditoriums = new ArrayList<>();

    public Building() {}

    public Building(long id) {
        super(id);
    }

    public Building(long id, String name, String address, Double lat, Double lon) {
        this(id);
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}