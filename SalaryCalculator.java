import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class SalaryCalculator extends JFrame {
    private JComboBox<String> employeeSelector;
    private JTextField hoursWorkedField, unpaidLeaveDaysField, lateDaysField;
    private JLabel nricLabel, otPayLabel, unpaidLeaveDeductionLabel, latePenaltyLabel, epfLabel, socsoLabel, eisLabel, pcbLabel, totalPayableLabel, allowanceLabel, netSalaryLabel, salaryLabel;
    private JButton calculateButton, printPayslipButton, backButton;
    private HashMap<String, EmpProfile> employees;
    private EmpProfile selectedEmployee;

    public SalaryCalculator() {
        setTitle("Salary Calculator");
        setSize(500, 800);  
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Read employee data from the file
        employees = readEmployeeData();

        // Employee NRIC Selector
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Select Employee NRIC:"), gbc);
        employeeSelector = new JComboBox<>(employees.keySet().toArray(new String[0]));
        gbc.gridx = 1;
        mainPanel.add(employeeSelector, gbc);

        // Salary
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Gross Salary (RM):"), gbc);
        salaryLabel = new JLabel("RM0.00");
        gbc.gridx = 1;
        mainPanel.add(salaryLabel, gbc);

        // NRIC Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Employee NRIC:"), gbc);
        nricLabel = new JLabel("-");
        gbc.gridx = 1;
        mainPanel.add(nricLabel, gbc);

        // Hours Worked
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Enter Hours Worked:"), gbc);
        hoursWorkedField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(hoursWorkedField, gbc);

        // Unpaid Leave Days
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Enter Unpaid Leave Days:"), gbc);
        unpaidLeaveDaysField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(unpaidLeaveDaysField, gbc);

        // Late Days
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Enter Late Days:"), gbc);
        lateDaysField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(lateDaysField, gbc);

        // Calculate Button
        calculateButton = new JButton("Calculate Salary");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(100, 149, 237));  // Cornflower Blue
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        mainPanel.add(calculateButton, gbc);

        // Output labels (with NRIC before OT Pay)
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("OT Pay (RM):"), gbc);
        otPayLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(otPayLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Unpaid Leave Deduction (RM):"), gbc);
        unpaidLeaveDeductionLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(unpaidLeaveDeductionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(new JLabel("Late Penalty (RM):"), gbc);
        latePenaltyLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(latePenaltyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(new JLabel("Payable EPF (RM):"), gbc);
        epfLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(epfLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        mainPanel.add(new JLabel("Payable SOCSO (RM):"), gbc);
        socsoLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(socsoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        mainPanel.add(new JLabel("Payable EIS (RM):"), gbc);
        eisLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(eisLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        mainPanel.add(new JLabel("Payable PCB (RM):"), gbc);
        pcbLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(pcbLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        mainPanel.add(new JLabel("Total Payable (RM):"), gbc);
        totalPayableLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(totalPayableLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 15;
        mainPanel.add(new JLabel("Gross Allowance (RM):"), gbc);
        allowanceLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(allowanceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        mainPanel.add(new JLabel("Net Salary (RM):"), gbc);
        netSalaryLabel = new JLabel();
        gbc.gridx = 1;
        mainPanel.add(netSalaryLabel, gbc);

        // Print Payslip Button
        printPayslipButton = new JButton("Print Payslip");
        printPayslipButton.setFont(new Font("Arial", Font.BOLD, 14));
        printPayslipButton.setBackground(new Color(100, 149, 237));  // Cornflower Blue
        printPayslipButton.setForeground(Color.WHITE);
        printPayslipButton.setFocusPainted(false);
        
        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 69, 0));  // Red-Orange
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        // Create a panel for the bottom bar with both buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(backButton);
        bottomPanel.add(printPayslipButton);

        add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Add Action Listeners
        employeeSelector.addActionListener(e -> updateEmployeeDetails());
        calculateButton.addActionListener(e -> calculateAndDisplaySalary());
        printPayslipButton.addActionListener(e -> printPayslip());
        backButton.addActionListener(e -> {
            new PROfficer().setVisible(true);
            dispose();  // Close current SalaryCalculator window
        });

        setVisible(true);
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
            otPayLabel.setText(String.format("%.2f", otPay));
    
            double unpaidLeaveDeduction = 0.0;
            if (unpaidLeaveDays > 14) {
                int outstandingLeave = unpaidLeaveDays - 14;
                unpaidLeaveDeduction = outstandingLeave * 20;
            }
            unpaidLeaveDeductionLabel.setText(String.format("%.2f", unpaidLeaveDeduction));
    
            double latePenalty = 0.0;
            if (lateDays > 0) {
                latePenalty = (lateDays / 3) * 100;
            }
            latePenaltyLabel.setText(String.format("%.2f", latePenalty));
    
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
        File file = new File(fileName);
    
        // Check if the file already exists
        if (file.exists()) {
            JOptionPane.showMessageDialog(this, "Payslip already exists for the selected NRIC.", "File Exists", JOptionPane.WARNING_MESSAGE);
            return;  // Exit the method to prevent overwriting
        }
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
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
    
            JOptionPane.showMessageDialog(this, "Payslip has been saved as " + fileName);
        } catch (IOException ex) {
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
    
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    id = line.substring(4).trim();
                } else if (line.startsWith("Name: ")) {
                    name = line.substring(6).trim();
                } else if (line.startsWith("Position: ")) {
                    position = line.substring(10).trim();
                } else if (line.startsWith("Gross Salary: ")) {
                    salary = Double.parseDouble(line.substring(14).trim());
                }
    
                // If all required fields are read, create EmpProfile and add to the map
                if (id != null && name != null && position != null && salary != 0.0) {
                    EmpProfile emp = new EmpProfile(id, "-", "-", name, "-", LocalDate.now(), "-", "-", new ArrayList<>(), position, "-", salary);
                    employeeMap.put(id, emp);
                    
                    // Reset variables for the next employee entry
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
        SwingUtilities.invokeLater(SalaryCalculator::new);
    }
}
