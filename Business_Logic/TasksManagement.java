package Business_Logic;

import Model.ComplexTask;
import Model.Employee;
import Model.SimpleTask;
import Model.Task;

import java.util.*;
import javax.swing.*;


public class TasksManagement {
    private final Map<Employee, List<Task>> map = new HashMap<>();


    public void addEmployee(Employee employee) {
        if (findEmployee(employee.getIdEmployee()) == null) {
            map.put(employee, new ArrayList<>());
        } else {
            System.out.println("Employee with ID " + employee.getIdEmployee() + " already exists.");
        }
    }


    public Employee findEmployee(int idEmployee) {
        for (Employee employee : map.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                return employee;
            }
        }
        return null;
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        Employee employee = findEmployee(idEmployee);
        if (employee != null) {
            map.get(employee).add(task);
        } else {
            System.out.println("Employee with ID " + idEmployee + " not found.");
        }
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        Employee employee = findEmployee(idEmployee);
        if (employee == null) {
            System.out.println("Employee with ID " + idEmployee + " not found.");
            return 0;
        }

        int totalDuration = 0;

        for (Task task : map.get(employee)) {
            totalDuration += calculateTaskDurationRecursively(task);
        }

        return totalDuration;
    }

    private int calculateTaskDurationRecursively(Task task) {
        int taskDuration = 0;

        if ("Completed".equals(task.getStatusTask())) {
            taskDuration += calculateTaskDuration(task);
        }

        if (task instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) task;

            if (!"Completed".equals(complexTask.getStatusTask())) {
                for (Task subTask : complexTask.getSubTasks()) {
                    taskDuration += calculateTaskDurationRecursively(subTask);
                }
            }
        }

        return taskDuration;
    }

    private int calculateTaskDuration(Task task) {
        int totalDuration = 0;

        if (task instanceof SimpleTask) {
            totalDuration += task.estimateDuration();
        } else if (task instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) task;

            if (!"Completed".equals(complexTask.getStatusTask())) {
                for (Task subTask : complexTask.getSubTasks()) {
                    totalDuration += calculateTaskDuration(subTask);
                }
            } else {
                totalDuration += complexTask.estimateDuration();
            }
        }

        return totalDuration;
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = findEmployee(idEmployee);
        if (employee == null) {
            JOptionPane.showMessageDialog(null, "Employee with ID " + idEmployee + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean taskFound = false;
        for (Task task : map.get(employee)) {
            if (findAndModifyTaskStatus(task, idTask)) {
                taskFound = true;
                break;
            }
        }

        if (!taskFound) {
            JOptionPane.showMessageDialog(null, "Task with ID " + idTask + " not found for employee ID " + idEmployee + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean findAndModifyTaskStatus(Task task, int idTask) {
        if (task.getIdTask() == idTask) {
            if (task instanceof ComplexTask) {
                ComplexTask complexTask = (ComplexTask) task;

                if ("Uncompleted".equals(complexTask.getStatusTask())) {
                    if (areAllSubtasksCompleted(complexTask)) {
                        complexTask.setStatusTask("Completed");
                        JOptionPane.showMessageDialog(null, "Task status updated to 'Completed' for complex task ID " + idTask);
                    } else {
                        JOptionPane.showMessageDialog(null, "Not all subtasks are completed! Cannot change status to 'Completed'.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String newStatus = "Completed".equals(complexTask.getStatusTask()) ? "Uncompleted" : "Completed";
                    complexTask.setStatusTask(newStatus);
                    JOptionPane.showMessageDialog(null, "Task status updated to '" + newStatus + "' for complex task ID " + idTask);
                }
            } else {
                String newStatus = "Uncompleted".equals(task.getStatusTask()) ? "Completed" : "Uncompleted";
                task.setStatusTask(newStatus);
                JOptionPane.showMessageDialog(null, "Task status updated to '" + newStatus + "' for task ID " + idTask);
            }
            return true;
        }

        if (task instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) task;

            for (Task subTask : complexTask.getSubTasks()) {
                if (findAndModifyTaskStatus(subTask, idTask)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean areAllSubtasksCompleted(ComplexTask complexTask) {
        for (Task subTask : complexTask.getSubTasks()) {
            if (subTask instanceof ComplexTask) {
                if (!areAllSubtasksCompleted((ComplexTask) subTask)) {
                    return false;
                }
            }
            if (!"Completed".equals(subTask.getStatusTask())) {
                return false;
            }
        }
        return true;
    }

    public Map<Employee, List<Task>> getMap() {return map;}

    public void setMap(Map<Employee, List<Task>> map) {this.map.putAll(map);}

    public void deleteEmployee(int idEmployee) {
        Employee employee = findEmployee(idEmployee);
        if (employee != null) {
            map.remove(employee);
            System.out.println("Employee with ID " + idEmployee + " has been removed.");
        } else {
            System.out.println("Employee with ID " + idEmployee + " not found.");
        }
    }

}
