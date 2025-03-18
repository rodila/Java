package Model;

import java.io.Serial;
import java.io.Serializable;

sealed public abstract class Task implements Serializable permits ComplexTask, SimpleTask {
    @Serial
    private static final long serialVersionUID = 1L;

    int idTask;
    private String statusTask = "Uncompleted";
    private static int nextId = 1;

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
    }

    public abstract int estimateDuration();

    public int getIdTask() {
        return idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public void setIdTask(int i) {
        this.idTask = i;
    }
}
