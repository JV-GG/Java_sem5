import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class Payslipupdate extends JFrame {
    private JComboBox<String> employeeSelector;
    private JTextField hoursWorkedField, unpaidLeaveDaysField, lateDaysField;
    private JLabel nricLabel, otPayLabel, unpaidLeaveDeductionLabel, latePenaltyLabel, epfLabel, socsoLabel, eisLabel, pcbLabel, totalPayableLabel, allowanceLabel, netSalaryLabel, salaryLabel;
    private JButton calculateButton, printPayslipButton, backButton;
    private HashMap<String, EmpProfile> employees;
    private EmpProfile selectedEmployee;

    public Payslipupdate() {
        setTitle("Salary Updater");
        setSize(500, 800);  // Adjusted height for back button
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Read employee data from the file
        employees = readEmployeeData();

        // Create a panel for the input fields and labels
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Employee NRIC Selector
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Employee NRIC:"), gbc);
        employeeSelector = new JComboBox<>(employees.keySet().toArray(new String[0]));
        gbc.gridx = 1;
        panel.add(employeeSelector, gbc);

        // Salary
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Salary (RM):"), gbc);
        salaryLabel = new JLabel("RM0.00");
        gbc.gridx = 1;
        panel.add(salaryLabel, gbc);

        // NRIC Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Employee NRIC:"), gbc);
        nricLabel = new JLabel("-");
        gbc.gridx = 1;
        panel.add(nricLabel, gbc);

        // Hours Worked
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Enter Hours Worked:"), gbc);
        hoursWorkedField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(hoursWorkedField, gbc);

        // Unpaid Leave Days
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Enter Unpaid Leave Days:"), gbc);
        unpaidLeaveDaysField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(unpaidLeaveDaysField, gbc);

        // Late Days
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Enter Late Days:"), gbc);
        lateDaysField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(lateDaysField, gbc);

        // Calculate Button
        calculateButton = new JButton("Calculate Salary");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(70, 130, 180));
        calculateButton.setForeground(Color.WHITE);
        panel.add(calculateButton, gbc);

        // Output labels (with NRIC before OT Pay)
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("OT Pay (RM):"), gbc);
        otPayLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(otPayLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Unpaid Leave Deduction (RM):"), gbc);
        unpaidLeaveDeductionLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(unpaidLeaveDeductionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Late Penalty (RM):"), gbc);
        latePenaltyLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(latePenaltyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(new JLabel("Payable EPF (RM):"), gbc);
        epfLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(epfLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(new JLabel("Payable SOCSO (RM):"), gbc);
        socsoLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(socsoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(new JLabel("Payable EIS (RM):"), gbc);
        eisLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(eisLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add(new JLabel("Payable PCB (RM):"), gbc);
        pcbLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(pcbLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        panel.add(new JLabel("Total Payable (RM):"), gbc);
        totalPayableLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(totalPayableLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 15;
        panel.add(new JLabel("Gross Allowance (RM):"), gbc);
        allowanceLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(allowanceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        panel.add(new JLabel("Net Salary (RM):"), gbc);
        netSalaryLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(netSalaryLabel, gbc);

        // Print Payslip Button
        printPayslipButton = new JButton("Print Payslip");
        gbc.gridx = 1;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(printPayslipButton, gbc);

        // Back Button
        backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(backButton, gbc);

        add(panel);

        // Add Action Listeners
        employeeSelector.addActionListener(e -> updateEmployeeDetails());
        calculateButton.addActionListener(e -> calculateAndDisplaySalary());
        printPayslipButton.addActionListener(e -> printPayslip());
        backButton.addActionListener(e -> navigateBack());

        setVisible(true);
    }

    private void navigateBack() {
        // Close the current frame and navigate back to PROfficer
        this.dispose(); // Close the Payslipupdate window
        SwingUtilities.invokeLater(() -> new PROfficer()); // Open the PROfficer window
    }
    
    private void updateEmployeeDetails() {
        String selectedID = (String) employeeSelector.getSelectedItem();
        selectedEmployee = employees.get(selectedID);

        if (selectedEmployee != null) {
            salaryLabel.setText(String.format("RM%.2f", selectedEmployee.getSalary()));
            nricLabel.setText(selectedEmployee.getNRIC());  // Set NRIC label
        }
    }

    private void calculateAndDisplaySalary() {
        try {
            double salary = selectedEmployee.getSalary();
            int hoursWorked = Integer.parseInt(hoursWorkedField.getText());
            int unpaidLeaveDays = Integer.parseInt(unpaidLeaveDaysField.getText());
            int lateDays = Integer.parseInt(lateDaysField.getText());
    
            double otRate = 8.0;
            double otPay = 0.0;
            if (hoursWorked > 160) {
                int otHours = hoursWorked - 160;
                otPay = otHours * otRate;
            }
            otPayLabel.setText(String.format("RM%.2f", otPay));
    
            double unpaidLeaveDeduction = 0.0;
            if (unpaidLeaveDays > 14) {
                int outstandingLeave = unpaidLeaveDays - 14;
                unpaidLeaveDeduction = (salary / 20) * outstandingLeave;
            }
            unpaidLeaveDeductionLabel.setText(String.format("RM%.2f", unpaidLeaveDeduction));
    
            // Late Penalty Calculation (RM100 for every 3 days late)
            double latePenalty = (lateDays / 3) * 100.0;
            latePenaltyLabel.setText(String.format("RM%.2f", latePenalty));
    
            double epfContribution = salary * 0.11;
            epfLabel.setText(String.format("RM%.2f", epfContribution));
    
            double socsoContribution = salary * 0.005;
            socsoLabel.setText(String.format("RM%.2f", socsoContribution));
    
            double eisContribution = salary * 0.002;
            eisLabel.setText(String.format("RM%.2f", eisContribution));
    
            double pcbContribution = salary * 0.00416;
            pcbLabel.setText(String.format("RM%.2f", pcbContribution));
    
            double totalPayable = epfContribution + socsoContribution + eisContribution + pcbContribution;
            totalPayableLabel.setText(String.format("RM%.2f", totalPayable));
    
            double allowance = 300;
            allowanceLabel.setText(String.format("RM%.2f", allowance));
    
            double netSalary = salary - totalPayable + allowance + otPay - unpaidLeaveDeduction - latePenalty;
            netSalaryLabel.setText(String.format("RM%.2f", netSalary));
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void printPayslip() {
        // Create the folder if it doesn't exist
        File folder = new File("Payslip");
        if (!folder.exists()) {
            folder.mkdir(); // Create the folder if it doesn't exist
        }
    
        // Generate the file name based on the selected NRIC
        String fileName = "Payslip/" + selectedEmployee.getNRIC() + ".txt";
    
        // Use FileWriter without 'append' mode (second argument 'false') to overwrite existing file
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println("--------------------------------------------------");
            writer.println("Payslip for " + selectedEmployee.getName());
            writer.println("NRIC: " + selectedEmployee.getNRIC());
            writer.println("Salary: RM" + String.format("%.2f", selectedEmployee.getSalary()));
            writer.println("OT Pay: RM" + otPayLabel.getText());
            writer.println("Unpaid Leave Deduction: RM" + unpaidLeaveDeductionLabel.getText());
            writer.println("Late Penalty: RM" + latePenaltyLabel.getText());
            writer.println("EPF: " + epfLabel.getText());
            writer.println("SOCSO: " + socsoLabel.getText());
            writer.println("EIS: " + eisLabel.getText());
            writer.println("PCB: " + pcbLabel.getText());
            writer.println("Total Payable: " + totalPayableLabel.getText());
            writer.println("Allowance: " + allowanceLabel.getText());
            writer.println("Net Salary: " + netSalaryLabel.getText());
            writer.println("--------------------------------------------------");
    
            // Show success message
            JOptionPane.showMessageDialog(this, "Payslip has been saved as " + fileName);
        } catch (IOException ex) {
            // Handle any exceptions during file writing
            JOptionPane.showMessageDialog(this, "An error occurred while saving the payslip.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
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
        SwingUtilities.invokeLater(Payslipupdate::new);
    }
}

