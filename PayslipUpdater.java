import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PayslipUpdater extends JFrame {
    private JComboBox<String> nricDropdown;
    private JTextField salaryField, otPayField, unpaidLeaveField, latePenaltyField;
    private JTextField epfField, socsoField, eisField, pcbField, totalPayableField;
    private JTextField allowanceField, netSalaryField;
    private JButton saveButton;
    private Map<String, List<String>> payslipData = new HashMap<>();
    private static final String FILE_PATH = "PaySlips.txt";

    public PayslipUpdater() {
        setTitle("Payslip Updater");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dropdown panel
        JPanel dropdownPanel = new JPanel();
        nricDropdown = new JComboBox<>();
        nricDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedNRIC = (String) nricDropdown.getSelectedItem();
                if (selectedNRIC != null) {
                    displayPayslipData(selectedNRIC);
                }
            }
        });
        dropdownPanel.add(new JLabel("Select Employee NRIC:"));
        dropdownPanel.add(nricDropdown);
        add(dropdownPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        formPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        formPanel.add(salaryField);

        formPanel.add(new JLabel("OT Pay:"));
        otPayField = new JTextField();
        formPanel.add(otPayField);

        formPanel.add(new JLabel("Unpaid Leave Deduction:"));
        unpaidLeaveField = new JTextField();
        formPanel.add(unpaidLeaveField);

        formPanel.add(new JLabel("Late Penalty:"));
        latePenaltyField = new JTextField();
        formPanel.add(latePenaltyField);

        formPanel.add(new JLabel("EPF:"));
        epfField = new JTextField();
        formPanel.add(epfField);

        formPanel.add(new JLabel("SOCSO:"));
        socsoField = new JTextField();
        formPanel.add(socsoField);

        formPanel.add(new JLabel("EIS:"));
        eisField = new JTextField();
        formPanel.add(eisField);

        formPanel.add(new JLabel("PCB:"));
        pcbField = new JTextField();
        formPanel.add(pcbField);

        formPanel.add(new JLabel("Total Payable:"));
        totalPayableField = new JTextField();
        formPanel.add(totalPayableField);

        formPanel.add(new JLabel("Allowance:"));
        allowanceField = new JTextField();
        formPanel.add(allowanceField);

        formPanel.add(new JLabel("Net Salary:"));
        netSalaryField = new JTextField();
        formPanel.add(netSalaryField);

        add(formPanel, BorderLayout.CENTER);

        // Save button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePayslipData();
            }
        });
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load NRIC numbers and data
        loadNRICNumbers();
        setVisible(true);
    }

    private void loadNRICNumbers() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String currentNRIC = null;
            List<String> data = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.startsWith("NRIC:")) {
                    if (currentNRIC != null) {
                        payslipData.put(currentNRIC, data);
                    }
                    currentNRIC = line.split(":")[1].trim();
                    data = new ArrayList<>();
                    nricDropdown.addItem(currentNRIC);
                } else if (currentNRIC != null) {
                    data.add(line.split(":")[1].trim());
                }
            }
            if (currentNRIC != null) {
                payslipData.put(currentNRIC, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPayslipData(String nric) {
        List<String> data = payslipData.get(nric);
        if (data != null) {
            salaryField.setText(data.get(0));
            otPayField.setText(data.get(1));
            unpaidLeaveField.setText(data.get(2));
            latePenaltyField.setText(data.get(3));
            epfField.setText(data.get(4));
            socsoField.setText(data.get(5));
            eisField.setText(data.get(6));
            pcbField.setText(data.get(7));
            totalPayableField.setText(data.get(8));
            allowanceField.setText(data.get(9));
            netSalaryField.setText(data.get(10));
        }
    }

    private void savePayslipData() {
        String selectedNRIC = (String) nricDropdown.getSelectedItem();
        if (selectedNRIC != null) {
            List<String> updatedData = new ArrayList<>();
            updatedData.add(salaryField.getText());
            updatedData.add(otPayField.getText());
            updatedData.add(unpaidLeaveField.getText());
            updatedData.add(latePenaltyField.getText());
            updatedData.add(epfField.getText());
            updatedData.add(socsoField.getText());
            updatedData.add(eisField.getText());
            updatedData.add(pcbField.getText());
            updatedData.add(totalPayableField.getText());
            updatedData.add(allowanceField.getText());
            updatedData.add(netSalaryField.getText());

            payslipData.put(selectedNRIC, updatedData);
            updatePayslipsFile();
        }
    }

    private void updatePayslipsFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<String>> entry : payslipData.entrySet()) {
                String nric = entry.getKey();
                List<String> data = entry.getValue();
                bw.write("NRIC: " + nric);
                bw.newLine();
                bw.write("Salary: " + data.get(0));
                bw.newLine();
                bw.write("OT Pay: " + data.get(1));
                bw.newLine();
                bw.write("Unpaid Leave Deduction: " + data.get(2));
                bw.newLine();
                bw.write("Late Penalty: " + data.get(3));
                bw.newLine();
                bw.write("EPF: " + data.get(4));
                bw.newLine();
                bw.write("SOCSO: " + data.get(5));
                bw.newLine();
                bw.write("EIS: " + data.get(6));
                bw.newLine();
                bw.write("PCB: " + data.get(7));
                bw.newLine();
                bw.write("Total Payable: " + data.get(8));
                bw.newLine();
                bw.write("Allowance: " + data.get(9));
                bw.newLine();
                bw.write("Net Salary: " + data.get(10));
                bw.newLine();
                bw.newLine(); // Separate entries by a blank line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayslipUpdater::new);
    }
}
