package Presentation;

import Model.Employee;
import Model.SimpleTask;
import Model.Task;
import Model.ComplexTask;
import Business_Logic.Utility;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;


public class View extends JFrame {
    private Controller controller;
    private Utility utility;
    private JTextField empIdField, empNameField, taskIdField, taskStatusField, startHourField, endHourField;
    private JComboBox<Employee> employeeDropdown;
    private JComboBox<String> taskTypeDropdown;
    private JTextField subTaskIdField;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;


    public View() {
        controller = new Controller();
        utility = new Utility();
        setTitle("Task Management System");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel employeePanel = createEmployeePanel();
        JPanel taskPanel = createTaskPanel();
        JPanel viewPanel = createViewPanel();
        JPanel statsPanel = createStatisticsPanel();

        mainPanel.add(employeePanel);
        mainPanel.add(taskPanel);
        mainPanel.add(viewPanel);
        mainPanel.add(statsPanel);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Gestionare Angajați", TitledBorder.LEFT, TitledBorder.TOP));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(800, 200));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("ID Angajat:"), gbc);

        empIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(empIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Nume Angajat:"), gbc);

        empNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(empNameField, gbc);

        JButton addEmployeeButton = new JButton("Adaugă Angajat");
        styleButton(addEmployeeButton);
        addEmployeeButton.addActionListener(e -> addEmployee());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addEmployeeButton, gbc);

        JButton deleteEmployeeButton = new JButton("Șterge Angajat");
        styleButton(deleteEmployeeButton);
        deleteEmployeeButton.addActionListener(e -> deleteEmployee());
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(deleteEmployeeButton, gbc);

        return panel;
    }

    private JPanel createTaskPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Atribuire Sarcină", TitledBorder.LEFT, TitledBorder.TOP));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(800, 250));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("ID Sarcină: "), gbc);

        taskIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(taskIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Status Sarcină: "), gbc);

        taskStatusField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(taskStatusField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Start Hour: "), gbc);

        startHourField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(startHourField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("End Hour: "), gbc);

        endHourField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(endHourField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Tip Sarcină: "), gbc);

        taskTypeDropdown = new JComboBox<>(new String[]{"Simple", "Complex"});
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(taskTypeDropdown, gbc);

        JButton assignTaskButton = new JButton("Asignează Sarcină");
        styleButton(assignTaskButton);
        assignTaskButton.addActionListener(e -> assignTask());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(assignTaskButton, gbc);

        JButton toggleStatusButton = new JButton("Schimbă Status Sarcină");
        styleButton(toggleStatusButton);
        toggleStatusButton.addActionListener(e -> toggleTaskStatus());
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(toggleStatusButton, gbc);

        JButton deleteTaskButton = new JButton("Șterge Sarcină");
        styleButton(deleteTaskButton);
        deleteTaskButton.addActionListener(e -> deleteTask());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(deleteTaskButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(new JLabel("ID Subtask: "), gbc);

        subTaskIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(subTaskIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(new JLabel("ID Complex Task: "), gbc);

        JTextField complexTaskIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(complexTaskIdField, gbc);

        JButton deleteSubTaskButton = new JButton("Șterge Subtask");
        styleButton(deleteSubTaskButton);
        deleteSubTaskButton.addActionListener(e -> deleteSubTask(complexTaskIdField));
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(deleteSubTaskButton, gbc);

        JButton addSubTaskButton = new JButton("Adaugă Subtask");
        styleButton(addSubTaskButton);
        addSubTaskButton.addActionListener(e -> addSubTask(complexTaskIdField));
        gbc.gridx = 2;
        gbc.gridy = 6;
        panel.add(addSubTaskButton, gbc);

        return panel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Vizualizare Sarcini"));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(800, 200));

        JPanel selectionPanel = new JPanel(new FlowLayout());
        employeeDropdown = new JComboBox<>();
        updateEmployeeDropdown();
        selectionPanel.add(new JLabel("Selectează Angajat: "));
        selectionPanel.add(employeeDropdown);

        JButton viewTasksButton = new JButton("Vezi Sarcinile ");
        styleButton(viewTasksButton);
        viewTasksButton.addActionListener(e -> updateTable());
        selectionPanel.add(viewTasksButton);

        panel.add(selectionPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Task", "Tip", "Durată", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        taskTable.setFillsViewportHeight(true);
        tableScrollPane = new JScrollPane(taskTable);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Statistici Sarcini", TitledBorder.LEFT, TitledBorder.TOP));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(800, 150));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(Color.WHITE);

        JButton employeesWithMoreThan40HoursButton = new JButton("Angajați cu > 40 ore lucrate");
        styleButton2(employeesWithMoreThan40HoursButton);
        employeesWithMoreThan40HoursButton.addActionListener(e -> displayEmployeesWithMoreThan40Hours());
        actionPanel.add(employeesWithMoreThan40HoursButton);

        JButton taskStatusStatsButton = new JButton("Statistici Status Sarcini");
        styleButton2(taskStatusStatsButton);
        taskStatusStatsButton.addActionListener(e -> displayTaskStatusStatistics());
        actionPanel.add(taskStatusStatsButton);

        JButton calculateWorkingHoursButton = new JButton("Calculează Ore Lucrate");
        styleButton2(calculateWorkingHoursButton);
        calculateWorkingHoursButton.addActionListener(e -> calculateWorkingHours());
        actionPanel.add(calculateWorkingHoursButton);

        panel.add(actionPanel, BorderLayout.NORTH);

        return panel;
    }

    private void updateTable() {
        Map<Employee, List<Task>> tasksData = controller.getTasksData();
        tableModel.setRowCount(0);

        for (Map.Entry<Employee, List<Task>> entry : tasksData.entrySet()) {
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();

            if (tasks.isEmpty()) {
                tableModel.addRow(new Object[]{employee.getIdEmployee(), "No tasks", "N/A", "N/A", ""});
            } else {
                for (Task task : tasks) {
                    if (task instanceof ComplexTask complexTask) {
                        int duration = complexTask.estimateDuration();
                        tableModel.addRow(new Object[]{employee.getIdEmployee(), "▶ " + task.getIdTask(), "Complex", duration + "h", task.getStatusTask()});
                        displaySubTasks(complexTask, "  ", tableModel);
                    } else {
                        tableModel.addRow(new Object[]{employee.getIdEmployee(), task.getIdTask(), "Simple", task.estimateDuration() + "h", task.getStatusTask()});
                    }
                }
            }
        }
    }

    private void displaySubTasks(ComplexTask parentTask, String prefix, DefaultTableModel model) {
        for (Task subTask : parentTask.getSubTasks()) {
            String subTaskType = (subTask instanceof SimpleTask) ? "Simple" : "Complex";

            model.addRow(new Object[]{"", prefix + "↳ " + subTask.getIdTask(), subTaskType, subTask.estimateDuration() + "h", subTask.getStatusTask()});

            if (subTask instanceof ComplexTask subComplexTask) {
                displaySubTasks(subComplexTask, prefix + "  ", model);
            }
        }
    }

    private void calculateWorkingHours() {
        Employee emp = (Employee) employeeDropdown.getSelectedItem();
        if (emp != null) {
            int totalHours = controller.calculateTotalWorkingHours(emp.getIdEmployee());
            JOptionPane.showMessageDialog(this, "Ore lucrate pentru " + emp.getName() + ": " + totalHours, "Ore Lucrate", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(empIdField.getText());
            String name = empNameField.getText();
            String resultMessage = controller.addEmployee(id, name);
            updateEmployeeDropdown();
            JOptionPane.showMessageDialog(this, resultMessage, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID-ul trebuie să fie un număr!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignTask() {
        try {
            Employee emp = (Employee) employeeDropdown.getSelectedItem();
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (taskTypeDropdown.getSelectedItem().equals("Simple")) {
                int taskId = Integer.parseInt(taskIdField.getText());
                String status = taskStatusField.getText();
                int startHour = Integer.parseInt(startHourField.getText());
                int endHour = Integer.parseInt(endHourField.getText());
                SimpleTask task = new SimpleTask(taskId, status, startHour, endHour);
                controller.assignTaskToEmployee(emp.getIdEmployee(), task);
            } else {
                int taskId = Integer.parseInt(taskIdField.getText());
                String status = taskStatusField.getText();
                ComplexTask complexTask = controller.getComplexTaskById(taskId);
                if (complexTask == null) {
                    ComplexTask newComplexTask = new ComplexTask(taskId, status, new ArrayList<>());
                    controller.assignTaskToEmployee(emp.getIdEmployee(), newComplexTask);
                    taskIdField.setText(String.valueOf(newComplexTask.getIdTask()));
                } else {
                    JOptionPane.showMessageDialog(this, "Sarcina complexă există deja! Folosește secțiunea de subtask-uri.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Toate câmpurile trebuie completate corect!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSubTask(JTextField complexTaskIdField) {
        try {
            Employee emp = (Employee) employeeDropdown.getSelectedItem();
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int complexTaskId = Integer.parseInt(complexTaskIdField.getText());
            int taskId = Integer.parseInt(subTaskIdField.getText());
            String status = taskStatusField.getText();
            int startHour = Integer.parseInt(startHourField.getText());
            int endHour = Integer.parseInt(endHourField.getText());

            Task subTask;
            if (taskTypeDropdown.getSelectedItem().equals("Simple")) {
                subTask = new SimpleTask(taskId, status, startHour, endHour);
            } else {
                subTask = new ComplexTask(taskId, status, new ArrayList<>());
            }

            boolean success = controller.addSubTaskToComplexTask(emp.getIdEmployee(), complexTaskId, subTask);

            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(this, "Subtask adăugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sarcina complexă nu există!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Toate câmpurile trebuie completate corect!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSubTask(JTextField complexTaskIdField) {
        try {
            Employee emp = (Employee) employeeDropdown.getSelectedItem();
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int complexTaskId = Integer.parseInt(complexTaskIdField.getText());
            int subTaskId = Integer.parseInt(subTaskIdField.getText());

            boolean success = controller.deleteSubTask(emp.getIdEmployee(), complexTaskId, subTaskId);

            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(this, "Subtask-ul a fost șters!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sarcina complexă sau subtask-ul nu au fost găsite!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID-ul sarcinii complexe și al subtask-ului trebuie să fie numere valide!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleTaskStatus() {
        try {
            Employee emp = (Employee) employeeDropdown.getSelectedItem();
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int taskId = Integer.parseInt(taskIdField.getText());
            controller.toggleTaskStatus(emp.getIdEmployee(), taskId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID-ul sarcinii trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        try {
            Employee emp = (Employee) employeeDropdown.getSelectedItem();
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Selectează un angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int taskId = Integer.parseInt(taskIdField.getText());

            List<Task> tasks = controller.getTasksForEmployee(emp.getIdEmployee());
            boolean exists = false;
            for (Task task : tasks) {
                if (task.getIdTask() == taskId) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                JOptionPane.showMessageDialog(this, "Sarcina cu ID-ul " + taskId + " nu există pentru angajatul selectat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.deleteTask(emp.getIdEmployee(), taskId);
            JOptionPane.showMessageDialog(this, "Sarcina a fost ștearsă!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID-ul sarcinii trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(empIdField.getText());
            boolean exists = false;
            for (Employee emp : controller.getAllEmployees()) {
                if (emp.getIdEmployee() == id) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                JOptionPane.showMessageDialog(this, "Angajatul nu există!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.deleteEmployee(id);
            updateEmployeeDropdown();
            JOptionPane.showMessageDialog(this, "Angajatul a fost șters cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID-ul trebuie să fie un număr!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayEmployeesWithMoreThan40Hours() {
        utility.displayEmployeesWithMoreThan40Hours(controller.getTasksManagement());
    }

    private void displayTaskStatusStatistics() {
        Map<String, Map<String, Integer>> taskStatusCounts = utility.getTaskStatusCountsForEmployees(controller.getTasksManagement());

        StringBuilder result = new StringBuilder("Task Status Statistics:\n");
        for (Map.Entry<String, Map<String, Integer>> entry : taskStatusCounts.entrySet()) {
            result.append("Employee: ").append(entry.getKey()).append("\n");
            Map<String, Integer> statusCounts = entry.getValue();
            result.append("Completed: ").append(statusCounts.get("Completed")).append("\n");
            result.append("Uncompleted: ").append(statusCounts.get("Uncompleted")).append("\n\n");
        }

        JOptionPane.showMessageDialog(this, result.toString(), "Task Status Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateEmployeeDropdown() {
        employeeDropdown.removeAllItems();
        for (Employee emp : controller.getAllEmployees()) {
            employeeDropdown.addItem(emp);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(170, 80, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(202, 30));
    }

    private void styleButton2(JButton button) {
        button.setBackground(new Color(170, 80, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(250, 40));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(View::new);
    }
}
