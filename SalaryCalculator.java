import java.awt.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SalaryCalculator extends JFrame {
    private JTextField hoursWorkedField, unpaidLeaveDaysField;
    private JLabel otPayLabel, unpaidLeaveDeductionLabel, epfLabel, socsoLabel, eisLabel, pcbLabel, totalPayableLabel, allowanceLabel, netSalaryLabel;
    private JButton calculateButton, printPayslipButton;
    private EmpProfile employee;

    public SalaryCalculator(EmpProfile employee) {
        this.employee = employee;
        setTitle("Salary Calculator");
        setSize(500, 650);  // Adjust size to fit the new button
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on the screen
        setLayout(new GridBagLayout());

        // Create a panel for the input fields and labels
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Salary
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Salary (RM):"), gbc);
        JLabel salaryLabel = new JLabel(String.format("RM%.2f", employee.getSalary()));
        gbc.gridx = 1;
        panel.add(salaryLabel, gbc);

        // Hours Worked
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Enter Hours Worked:"), gbc);
        hoursWorkedField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(hoursWorkedField, gbc);

        // Unpaid Leave Days
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Enter Unpaid Leave Days:"), gbc);
        unpaidLeaveDaysField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(unpaidLeaveDaysField, gbc);

        // Calculate Button
        calculateButton = new JButton("Calculate Salary");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(70, 130, 180));
        calculateButton.setForeground(Color.WHITE);
        panel.add(calculateButton, gbc);

        // Output labels
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("OT Pay (RM):"), gbc);
        otPayLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(otPayLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Unpaid Leave Deduction (RM):"), gbc);
        unpaidLeaveDeductionLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(unpaidLeaveDeductionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Payable EPF (RM):"), gbc);
        epfLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(epfLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Payable SOCSO (RM):"), gbc);
        socsoLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(socsoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Payable EIS (RM):"), gbc);
        eisLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(eisLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Payable PCB (RM):"), gbc);
        pcbLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(pcbLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(new JLabel("Total Payable (RM):"), gbc);
        totalPayableLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(totalPayableLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(new JLabel("Gross Allowance (RM):"), gbc);
        allowanceLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(allowanceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(new JLabel("Net Salary (RM):"), gbc);
        netSalaryLabel = new JLabel();
        gbc.gridx = 1;
        panel.add(netSalaryLabel, gbc);

        // Print Payslip Button
        printPayslipButton = new JButton("Print Payslip");
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(printPayslipButton, gbc);

        add(panel);

        calculateButton.addActionListener(e -> calculateAndDisplaySalary());
        printPayslipButton.addActionListener(e -> printPayslip());

        setVisible(true);
    }

    private void calculateAndDisplaySalary() {
        try {
            double salary = employee.getSalary();
            int hoursWorked = Integer.parseInt(hoursWorkedField.getText());
            int unpaidLeaveDays = Integer.parseInt(unpaidLeaveDaysField.getText());

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

            double netSalary = salary - totalPayable + allowance + otPay - unpaidLeaveDeduction;
            netSalaryLabel.setText(String.format("RM%.2f", netSalary));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printPayslip() {
        String fileName = "Payslips.txt"; // Common file for all payslips

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) { // 'true' to append to file
            writer.println("--------------------------------------------------");
            writer.println("Payslip for " + employee.getName());
            writer.println("Salary: RM" + String.format("%.2f", employee.getSalary()));
            writer.println("OT Pay: RM" + otPayLabel.getText());
            writer.println("Unpaid Leave Deduction: RM" + unpaidLeaveDeductionLabel.getText());
            writer.println("EPF: " + epfLabel.getText());
            writer.println("SOCSO: " + socsoLabel.getText());
            writer.println("EIS: " + eisLabel.getText());
            writer.println("PCB: " + pcbLabel.getText());
            writer.println("Total Payable: " + totalPayableLabel.getText());
            writer.println("Allowance: " + allowanceLabel.getText());
            writer.println("Net Salary: " + netSalaryLabel.getText());
            writer.println("--------------------------------------------------");
            writer.println(); // Add a blank line for separation between payslips

            JOptionPane.showMessageDialog(this, "Payslip has been appended to the file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while printing the payslip.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
