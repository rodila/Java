package Presentation;

import Business_Logic.*;
import Data_Access.SerializationOperations;
import Model.Employee;
import Model.Task;
import Model.SimpleTask;
import Model.ComplexTask;

import javax.swing.*;
import java.util.*;

public class Controller {
    private final TasksManagement tasksManagement;

    public Controller() {
        tasksManagement = new TasksManagement();
        tasksManagement.setMap(SerializationOperations.loadData());
    }

    public TasksManagement getTasksManagement() {
        return tasksManagement;
    }

    public String addEmployee(int id, String name) {
        if (tasksManagement.findEmployee(id) == null) {
            Employee newEmployee = new Employee(name, id);
            tasksManagement.addEmployee(newEmployee);
            saveChanges();
            return "Angajatul a fost adăugat cu succes!";
        } else {
            return "Angajatul cu ID-ul " + id + " există deja.";
        }
    }

    public void assignTaskToEmployee(int employeeId, Task task) {
        Employee employee = findEmployeeById(employeeId);

        if (employee != null) {
            tasksManagement.assignTaskToEmployee(employeeId, task);
            saveChanges();
        }

    }

    public void toggleTaskStatus(int employeeId, int taskId) {
        tasksManagement.modifyTaskStatus(employeeId, taskId);
        saveChanges();
    }

    private Employee findEmployeeById(int id) {
        for (Employee emp : tasksManagement.getMap().keySet()) {
            if (emp.getIdEmployee() == id) {
                return emp;
            }
        }
        return null;
    }

    public void deleteEmployee(int idEmployee) {
        tasksManagement.deleteEmployee(idEmployee);
    }

    private void saveChanges() {
        SerializationOperations.saveData(tasksManagement.getMap());
    }

    public List<Task> getTasksForEmployee(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        return (employee != null) ? tasksManagement.getMap().get(employee) : new ArrayList<>();
    }

    public List<Employee> getAllEmployees() {return new ArrayList<>(tasksManagement.getMap().keySet());}

    public Map<Employee, List<Task>> getTasksData() {
        return tasksManagement.getMap();
    }

    public ComplexTask getComplexTaskById(int taskId) {
        for (List<Task> tasks : tasksManagement.getMap().values()) {
            for (Task task : tasks) {
                if (task instanceof ComplexTask complexTask && complexTask.getIdTask() == taskId) {
                    return complexTask;
                } else if (task instanceof ComplexTask complexTask) {
                    ComplexTask foundTask = findComplexTaskInSubTasks(complexTask, taskId);
                    if (foundTask != null) {
                        return foundTask;
                    }
                }
            }
        }
        return null;
    }

    private ComplexTask findComplexTaskInSubTasks(ComplexTask parentTask, int taskId) {
        for (Task subTask : parentTask.getSubTasks()) {
            if (subTask instanceof ComplexTask complexTask && complexTask.getIdTask() == taskId) {
                return complexTask;
            } else if (subTask instanceof ComplexTask complexTask) {
                ComplexTask foundTask = findComplexTaskInSubTasks(complexTask, taskId);
                if (foundTask != null) {
                    return foundTask;
                }
            }
        }
        return null;
    }

    public boolean deleteSubTask(int employeeId, int complexTaskId, int subTaskId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            ComplexTask complexTask = getComplexTaskById(complexTaskId);
            if (complexTask != null) {
                boolean subTaskDeleted = deleteSubTaskRecursively(complexTask, subTaskId);
                if (subTaskDeleted) {
                    saveChanges();
                    return true;
                } else {
                    System.out.println("Subtask-ul cu ID-ul " + subTaskId + " nu a fost găsit în sarcina complexă!");
                }
            }
        }
        return false;
    }

    private boolean deleteSubTaskRecursively(ComplexTask complexTask, int subTaskId) {
        boolean deleted = false;

        List<Task> subTasks = complexTask.getSubTasks();
        for (Task subTask : subTasks) {
            if (subTask.getIdTask() == subTaskId) {
                complexTask.deleteTask(subTask);
                deleted = true;
                break;
            } else if (subTask instanceof ComplexTask) {
                boolean result = deleteSubTaskRecursively((ComplexTask) subTask, subTaskId);
                if (result) {
                    deleted = true;
                    break;
                }
            }
        }

        return deleted;
    }

    private int generateUniqueIdForSimpleTask() {
        return new Random().nextInt(1000000);
    }

    private int generateUniqueIdForComplexTask() {
        return new Random().nextInt(1000000);
    }

    public void deleteTask(int employeeId, int taskId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            List<Task> tasks = tasksManagement.getMap().get(employee);
            Task taskToDelete = null;

            for (Task task : tasks) {
                if (task.getIdTask() == taskId) {
                    taskToDelete = task;
                    break;
                }
            }

            if (taskToDelete != null) {
                tasks.remove(taskToDelete);
                saveChanges();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Angajatul nu a fost găsit!");
        }
    }

    public boolean addSubTaskToComplexTask(int employeeId, int complexTaskId, Task subTask) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            ComplexTask complexTask = getComplexTaskById(complexTaskId);
            if (complexTask != null) {
                if (subTask instanceof SimpleTask) {
                    SimpleTask simpleTask = (SimpleTask) subTask;
                    simpleTask.setIdTask(generateUniqueIdForSimpleTask());
                    complexTask.addTask(simpleTask);
                } else if (subTask instanceof ComplexTask) {
                    ComplexTask subComplexTask = (ComplexTask) subTask;
                    subComplexTask.setIdTask(generateUniqueIdForComplexTask());
                    complexTask.addTask(subComplexTask);
                    addSubTasksRecursively(subComplexTask);
                }
                saveChanges();
                return true;
            }
        }
        return false;
    }

    private void addSubTasksRecursively(ComplexTask complexTask) {
        for (Task subTask : complexTask.getSubTasks()) {
            if (subTask instanceof ComplexTask) {
                ComplexTask nestedComplexTask = (ComplexTask) subTask;
                complexTask.addTask(nestedComplexTask);
                addSubTasksRecursively(nestedComplexTask);
            }
        }
    }

    public int calculateTotalWorkingHours(int idEmployee) {
        return tasksManagement.calculateEmployeeWorkDuration(idEmployee);
    }

}
