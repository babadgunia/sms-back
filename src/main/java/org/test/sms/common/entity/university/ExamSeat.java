package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ExamSeat implements Serializable {

    @Column(name = "SEAT")
    private int seat;

    @Column(name = "VACANT")
    private boolean vacant;
}