package org.test.sms.common.entity.university;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ExamSeat implements Serializable {

    private int seat;

    private boolean isVacant;

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean isVacant) {
        this.isVacant = isVacant;
    }
}