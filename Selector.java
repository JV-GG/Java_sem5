import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class Selector extends JFrame {
    private JComboBox<String> employeeSelector;
    private HashMap<String, EmpProfile> employees;

    public Selector() {
        super("Employee Payroll Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        // Read employee data from the file
        employees = readEmployeeData();
        employeeSelector = new JComboBox<>(employees.keySet().toArray(new String[0]));

        add(new JLabel("Select Employee NRIC:"));
        add(employeeSelector);

        // Button to handle selection
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            String selectedID = (String) employeeSelector.getSelectedItem();
            EmpProfile selectedEmployee = employees.get(selectedID);
            if (selectedEmployee != null) {
                // Open SalaryCalculatorGUI with selected employee's data
                new SalaryCalculator(selectedEmployee);
            }
        });
        add(selectButton);

        setVisible(true);
    }

    private HashMap<String, EmpProfile> readEmployeeData() {
        HashMap<String, EmpProfile> employeeMap = new HashMap<>();
        File file = new File("ProfileManagement/employee_profiles.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String id = null, name = null, position = null;
            double salary = 0.0;
            String address = "";
            LocalDate dob = LocalDate.now(); // Default DOB, replace with actual if available

            while ((line = br.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    id = line.substring(4).trim();
                } else if (line.startsWith("Name: ")) {
                    name = line.substring(6).trim();
                } else if (line.startsWith("Position: ")) {
                    position = line.substring(10).trim();
                } else if (line.startsWith("Salary: ")) {
                    salary = Double.parseDouble(line.substring(8).trim());
                }

                if (id != null && name != null && position != null && salary != 0.0) {
                    employeeMap.put(id, new EmpProfile(id, "-", "-", name, "-", dob, address, "-", new ArrayList<>(), position, "-", salary));
                    id = name = position = null;
                    salary = 0.0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(this, "Error reading employee_profiles.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return employeeMap;
    }

    public static void main(String[] args) {
        new Selector();
    }
}
