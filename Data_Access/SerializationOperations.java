package Data_Access;

import Model.Employee;
import Model.Task;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class SerializationOperations {
    private static final String FILE_NAME = "Employees_and_Tasks.ser";

    public static void saveData(Map<Employee, List<Task>> data) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(data);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Eroare la salvarea datelor: " + e.getMessage());
        }
    }

    public static Map<Employee, List<Task>> loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Map<Employee, List<Task>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Eroare la încărcarea datelor: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
