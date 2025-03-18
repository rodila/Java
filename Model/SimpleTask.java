package Model;

import java.io.Serial;
import java.io.Serializable;

import Data_Access.*;

public non-sealed class SimpleTask extends Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        if (endHour >= startHour) {
            return endHour - startHour;
        } else {
            return (24 - startHour) + endHour;
        }
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }

    public void setIdTask(int i) {
        this.idTask = i;
    }
}
