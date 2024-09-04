import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class PayslipViewer extends JFrame {
    private JComboBox<String> fileDropdown;
    private JTextField nricField, salaryField, otPayField, unpaidLeaveField, latePenaltyField, socsoField, eisField, pcbField, totalPayableField, allowanceField, netSalaryField;
    private JButton viewButton, backButton;

    public PayslipViewer() {
        setTitle("Payslip Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a panel for the top section
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 69, 58)); // Red color for back button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToPROfficer();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(backButton, gbc);

        // Dropdown for file selection
        fileDropdown = new JComboBox<>(getPayslipFiles());
        fileDropdown.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        topPanel.add(new JLabel("Select Payslip File:"), gbc);
        gbc.gridx = 2;
        topPanel.add(fileDropdown, gbc);

        // View button
        viewButton = new JButton("View");
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewButton.setBackground(new Color(70, 130, 180));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);
        viewButton.setPreferredSize(new Dimension(100, 30));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFileContent();
            }
        });
        gbc.gridx = 3;
        topPanel.add(viewButton, gbc);

        // Create a panel for displaying payslip details
        JPanel contentPanel = new JPanel(new GridLayout(12, 2, 10, 10)); // 12 rows, 2 columns
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Labels and text fields for displaying payslip data
        contentPanel.add(new JLabel("NRIC:"));
        nricField = createTextField();
        contentPanel.add(nricField);

        contentPanel.add(new JLabel("Salary:"));
        salaryField = createTextField();
        contentPanel.add(salaryField);

        contentPanel.add(new JLabel("OT Pay:"));
        otPayField = createTextField();
        contentPanel.add(otPayField);

        contentPanel.add(new JLabel("Unpaid Leave Deduction:"));
        unpaidLeaveField = createTextField();
        contentPanel.add(unpaidLeaveField);

        contentPanel.add(new JLabel("Late Penalty:"));
        latePenaltyField = createTextField();
        contentPanel.add(latePenaltyField);

        contentPanel.add(new JLabel("SOCSO:"));
        socsoField = createTextField();
        contentPanel.add(socsoField);

        contentPanel.add(new JLabel("EIS:"));
        eisField = createTextField();
        contentPanel.add(eisField);

        contentPanel.add(new JLabel("PCB:"));
        pcbField = createTextField();
        contentPanel.add(pcbField);

        contentPanel.add(new JLabel("Total Payable:"));
        totalPayableField = createTextField();
        contentPanel.add(totalPayableField);

        contentPanel.add(new JLabel("Allowance:"));
        allowanceField = createTextField();
        contentPanel.add(allowanceField);

        contentPanel.add(new JLabel("Net Salary:"));
        netSalaryField = createTextField();
        contentPanel.add(netSalaryField);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setEditable(false); // Text fields are read-only
        return textField;
    }

    private String[] getPayslipFiles() {
        File folder = new File("Payslip"); // Folder "Payslip" is specified here
        System.out.println("Reading files from: " + folder.getAbsolutePath()); // Debug message

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "No payslip files found in the Payslip folder.", "Error", JOptionPane.ERROR_MESSAGE);
            return new String[0];
        }
        
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }
        return fileNames;
    }
    
    private void viewFileContent() {
        String selectedFile = (String) fileDropdown.getSelectedItem();
        if (selectedFile != null) {
            File file = new File("Payslip/" + selectedFile);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Clear all previous content before displaying new one
                clearTextFields();

                // Extract values and display them in the correct fields
                String line;
                while ((line = reader.readLine()) != null) {
                    updatePayslipField(line);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearTextFields() {
        nricField.setText("");
        salaryField.setText("");
        otPayField.setText("");
        unpaidLeaveField.setText("");
        latePenaltyField.setText("");
        socsoField.setText("");
        eisField.setText("");
        pcbField.setText("");
        totalPayableField.setText("");
        allowanceField.setText("");
        netSalaryField.setText("");
    }

    private void updatePayslipField(String line) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "NRIC":
                    nricField.setText(value);
                    break;
                case "Salary":
                    salaryField.setText(value);
                    break;
                case "OT Pay":
                    otPayField.setText(value);
                    break;
                case "Unpaid Leave Deduction":
                    unpaidLeaveField.setText(value);
                    break;
                case "Late Penalty":
                    latePenaltyField.setText(value);
                    break;
                case "SOCSO":
                    socsoField.setText(value);
                    break;
                case "EIS":
                    eisField.setText(value);
                    break;
                case "PCB":
                    pcbField.setText(value);
                    break;
                case "Total Payable":
                    totalPayableField.setText(value);
                    break;
                case "Allowance":
                    allowanceField.setText(value);
                    break;
                case "Net Salary":
                    netSalaryField.setText(value);
                    break;
                default:
                    break;
            }
        }
    }

    private void backToPROfficer() {
        // Close the current window and open PROfficer
        dispose();
        SwingUtilities.invokeLater(() -> new PROfficer());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayslipViewer::new);
    }
}
