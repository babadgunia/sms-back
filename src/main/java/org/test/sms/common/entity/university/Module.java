package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Module.SEQUENCE_NAME, sequenceName = Module.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Module extends AbstractEntity {

    static final String SEQUENCE_NAME = "MODULE" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    private Integer maxGrade;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "module")
    private List<Group> groups = new ArrayList<>();

    public Module(long id) {
        super(id);
    }
}