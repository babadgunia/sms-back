package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ExamSeat implements Serializable {

    private int seat;

    private boolean isVacant;
}