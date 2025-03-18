package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    List<Task> subTasks;

    public ComplexTask(int idTask, String statusTask, List<Task> subTasks) {
        super(idTask, statusTask);
        this.subTasks = subTasks != null ? subTasks : new ArrayList<>();
    }

    @Override
    public int estimateDuration() {
        int totalDuration = 0;
        for (Task subTask : subTasks) {
            totalDuration += subTask.estimateDuration();
        }
        return totalDuration;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    @Override
    public String toString() {
        return "ComplexTask{" +
                "subTasks=" + subTasks +
                '}';
    }

    public void setIdTask(int i) {
        this.idTask = i;
    }


}
