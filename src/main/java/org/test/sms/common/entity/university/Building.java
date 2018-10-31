package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Building.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Building extends AbstractEntity {

    static final String TABLE_NAME = "BUILDING";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    private String name;

    private String address;

    private Double lat;

    private Double lon;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auditorium> auditoriums = new ArrayList<>();

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
}