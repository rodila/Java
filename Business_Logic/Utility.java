package Business_Logic;

import java.util.*;

import Model.*;

import javax.swing.*;

public class Utility {
    public void displayEmployeesWithMoreThan40Hours(TasksManagement tasksManagement) {
        Set<Map.Entry<Employee, List<Task>>> entries = tasksManagement.getMap().entrySet();
        List<Employee> filteredEmployees = new ArrayList<>();

        for (Map.Entry<Employee, List<Task>> entry : entries) {
            Employee employee = entry.getKey();
            int workDuration = tasksManagement.calculateEmployeeWorkDuration(employee.getIdEmployee());
            if (workDuration > 40) {
                filteredEmployees.add(employee);
            }
        }

        filteredEmployees.sort((e1, e2) -> {
            int duration1 = tasksManagement.calculateEmployeeWorkDuration(e1.getIdEmployee());
            int duration2 = tasksManagement.calculateEmployeeWorkDuration(e2.getIdEmployee());
            return Integer.compare(duration1, duration2);
        });

        StringBuilder sb = new StringBuilder("Angajați cu > 40 ore lucrate:\n\n");
        if (filteredEmployees.isEmpty()) {
            sb.append("Nu există angajați cu mai mult de 40 de ore lucrate.");
        } else {
            for (Employee employee : filteredEmployees) {
                int duration = tasksManagement.calculateEmployeeWorkDuration(employee.getIdEmployee());
                sb.append("Employee: ").append(employee.getName())
                        .append(", Work Duration: ").append(duration).append(" hours\n");
            }
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Angajați cu > 40 ore lucrate", JOptionPane.INFORMATION_MESSAGE);
    }

    public Map<String, Map<String, Integer>> getTaskStatusCountsForEmployees(TasksManagement tasksManagement) {

        Map<String, Map<String, Integer>> result = new HashMap<>();

        for (Map.Entry<Employee, List<Task>> entry : tasksManagement.getMap().entrySet()) {

            Map<String, Integer> taskStatusCount = new HashMap<>();
            taskStatusCount.put("Completed", 0);
            taskStatusCount.put("Uncompleted", 0);

            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();

            for (Task task : tasks) {
                countTaskStatusesRecursively(task, taskStatusCount);
            }

            result.put(employee.getName(), taskStatusCount);
        }

        return result;
    }

    private void countTaskStatusesRecursively(Task task, Map<String, Integer> taskStatusCount) {
        if (task instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) task;
            for (Task subTask : complexTask.getSubTasks()) {
                countTaskStatusesRecursively(subTask, taskStatusCount);
            }
        }
        String status = task.getStatusTask();
        if ("Completed".equals(status)) {
            taskStatusCount.put("Completed", taskStatusCount.get("Completed") + 1);
        } else if ("Uncompleted".equals(status)) {
            taskStatusCount.put("Uncompleted", taskStatusCount.get("Uncompleted") + 1);
        }
    }

}
