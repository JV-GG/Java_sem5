// Purpose: HR Officer panel to manage employee profiles.
// Other classes required: EmpProfile.java, ProfileManagement.java

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class hrOfficer {
    private JFrame frame;
    private Map<String, String> userPasswords = new HashMap<>();
    private String username;
    private String password;
    private String nric;
    private ProfileManagement hrManager = new ProfileManagement();

    public hrOfficer(String password, String nric) {
        this.password = password;
        this.nric = nric;
    }

    public void runhrOfficer() {
        frame = new JFrame("HR Officer");
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("HR Officer Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JButton btnCreate = new JButton("Create Employee Profile");
        JButton btnRetrieve = new JButton("Retrieve Employee Profile");
        JButton btnUpdate = new JButton("Update Employee Profile");
        JButton btnChangePassword = new JButton("Change Password");
        JButton btnExit = new JButton("Logout");

        JButton[] buttons = { btnCreate, btnRetrieve, btnUpdate, btnChangePassword, btnExit };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            buttons[i].setPreferredSize(new Dimension(300, 50));
            gbc.gridy = i + 1;
            panel.add(buttons[i], gbc);
        }

        btnCreate.addActionListener(e -> createEmployeeProfile());
        btnRetrieve.addActionListener(e -> retrieveEmployeeProfile());
        btnUpdate.addActionListener(e -> updateEmployeeProfile());
        btnChangePassword.addActionListener(e -> changePassword(username, password, frame));
        btnExit.addActionListener(e -> {
            frame.dispose();
            new AuthApp();
        });

        frame.setVisible(true);
    }

    public void createEmployeeProfile() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        addLabeledField(formPanel, gbc, "Employee NRIC/Passport:", new JTextField());
        addLabeledField(formPanel, gbc, "Employee Password:", new JPasswordField());
        addLabeledField(formPanel, gbc, "Employee Bank Account (Maybank xxxxxxxxxxxx):", new JTextField());
        addLabeledField(formPanel, gbc, "Employee Fullname (as per NRIC/Passport):", new JTextField());
        addLabeledField(formPanel, gbc, "Gender:", new JTextField());
        addLabeledField(formPanel, gbc, "Employee DOB (yyyy-mm-dd):", new JTextField());
        addLabeledField(formPanel, gbc, "Employee Address:", new JTextField());
        addLabeledField(formPanel, gbc, "Employee Emergency Contact (601xxxxxxxx):", new JTextField());
        addLabeledField(formPanel, gbc, "Employee Working Experience (comma-separated):", new JTextField());
        addLabeledField(formPanel, gbc, "Position:", new JTextField());
        addLabeledField(formPanel, gbc, "Department:", new JTextField());
        addLabeledField(formPanel, gbc, "Gross Salary (RM):", new JTextField());

        Component[] components = formPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                component.setPreferredSize(new Dimension(200, 25));
            }
        }

        int result = JOptionPane.showConfirmDialog(frame, formPanel, "Create Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String NRIC = ((JTextField) components[1]).getText();
            String Password = new String(((JPasswordField) components[3]).getPassword());
            String BankAcc = ((JTextField) components[5]).getText();
            String Name = ((JTextField) components[7]).getText();
            String Gender = ((JTextField) components[9]).getText();
            LocalDate DOB = LocalDate.parse(((JTextField) components[11]).getText());
            String address = ((JTextField) components[13]).getText();
            String emergencyContact = ((JTextField) components[15]).getText();
            List<String> experience = Arrays.asList(((JTextField) components[17]).getText().split(","));
            String position = ((JTextField) components[19]).getText();
            String department = ((JTextField) components[21]).getText();
            double salary = Double.parseDouble(((JTextField) components[23]).getText());

            EmpProfile newEmployee = new EmpProfile(NRIC, Password, BankAcc, Name, Gender, DOB, address,
                    emergencyContact, experience, position, department, salary);
            hrManager.createEmployeeProfile(newEmployee);
        }
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void retrieveEmployeeProfile() {
        String empID = JOptionPane.showInputDialog(frame, "Enter Employee ID:");
    
        if (empID != null && !empID.trim().isEmpty()) {
            EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);
    
            if (emp != null) {
                JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
                profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                profilePanel.setPreferredSize(new Dimension(600, 500));
                profilePanel.add(new JLabel("Employee NRIC/Passport:"));
                profilePanel.add(new JLabel(emp.getNRIC()));
    
                profilePanel.add(new JLabel("Employee Password:"));
                profilePanel.add(new JLabel(emp.getPassword()));
    
                profilePanel.add(new JLabel("Employee Bank Account:"));
                profilePanel.add(new JLabel(emp.getBankAcc()));
    
                profilePanel.add(new JLabel("Employee Fullname (as per NRIC/Passport):"));
                profilePanel.add(new JLabel(emp.getName()));
    
                profilePanel.add(new JLabel("Employee Gender:"));
                profilePanel.add(new JLabel(emp.getGender()));
    
                profilePanel.add(new JLabel("Employee DOB:"));
                profilePanel.add(new JLabel(emp.getDOB().toString()));
    
                profilePanel.add(new JLabel("Employee Age:"));
                profilePanel.add(new JLabel(String.valueOf(emp.getAge())));
    
                profilePanel.add(new JLabel("Employee Address:"));
                profilePanel.add(new JLabel(emp.getAddress()));
    
                profilePanel.add(new JLabel("Employee Emergency Contact:"));
                profilePanel.add(new JLabel(emp.getEmergencyContact()));
    
                profilePanel.add(new JLabel("Employee Working Experience:"));
                profilePanel.add(new JLabel(String.join(", ", emp.getWorkingExperience())));
    
                profilePanel.add(new JLabel("Employee Position:"));
                profilePanel.add(new JLabel(emp.getPosition()));
    
                profilePanel.add(new JLabel("Employee Department:"));
                profilePanel.add(new JLabel(emp.getDepartment()));
    
                profilePanel.add(new JLabel("Employee Gross Salary (RM):"));
                profilePanel.add(new JLabel(String.format("%.2f", emp.getSalary())));
    
                JTextArea positionHistory = new JTextArea(hrManager.getPositionChangeHistory(empID));
                positionHistory.setEditable(false);
                JScrollPane positionScrollPane = new JScrollPane(positionHistory);
                positionScrollPane.setPreferredSize(new Dimension(550, 200));
                
                JTextArea salaryHistory = new JTextArea(hrManager.getSalaryIncrementHistory(empID));
                salaryHistory.setEditable(false);
                JScrollPane salaryScrollPane = new JScrollPane(salaryHistory);
                salaryScrollPane.setPreferredSize(new Dimension(550, 200));
    
                JTextArea leaveHistory = new JTextArea(hrManager.getLeaveEntitlementHistory(empID));
                leaveHistory.setEditable(false);
                JScrollPane leaveScrollPane = new JScrollPane(leaveHistory);
                leaveScrollPane.setPreferredSize(new Dimension(550, 200));
    
                JTabbedPane historyTabbedPane = new JTabbedPane();
                historyTabbedPane.addTab("Position History", positionScrollPane);
                historyTabbedPane.addTab("Salary History", salaryScrollPane);
                historyTabbedPane.addTab("Leave Entitlement History", leaveScrollPane);
    
                JPanel mainPanel = new JPanel(new BorderLayout());
                mainPanel.add(profilePanel, BorderLayout.CENTER);
                mainPanel.add(historyTabbedPane, BorderLayout.SOUTH);
    
                JDialog dialog = new JDialog(frame, "Employee Profile", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setContentPane(mainPanel);
                dialog.setSize(500, 600);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void updateEmployeeProfile() {
        String empID = JOptionPane.showInputDialog(frame, "Enter Employee ID:");

        if (empID != null && !empID.trim().isEmpty()) {
            EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);

            if (emp != null) {
                JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                formPanel.add(new JLabel("New Bank Account (leave blank to keep current):"));
                JTextField txtBankAcc = new JTextField(emp.getBankAcc());
                formPanel.add(txtBankAcc);

                formPanel.add(new JLabel("New Fullname (leave blank to keep current):"));
                JTextField txtName = new JTextField(emp.getName());
                formPanel.add(txtName);

                formPanel.add(new JLabel("New DOB (yyyy-mm-dd) (leave blank to keep current):"));
                JTextField txtDOB = new JTextField(emp.getDOB().toString());
                formPanel.add(txtDOB);

                formPanel.add(new JLabel("New Address (leave blank to keep current):"));
                JTextField txtAddress = new JTextField(emp.getAddress());
                formPanel.add(txtAddress);

                formPanel.add(new JLabel("New Emergency Contact (leave blank to keep current):"));
                JTextField txtEmergencyContact = new JTextField(emp.getEmergencyContact());
                formPanel.add(txtEmergencyContact);

                formPanel.add(new JLabel("New Working Experience (comma-separated, leave blank to keep current):"));
                JTextField txtExperience = new JTextField(String.join(", ", emp.getWorkingExperience()));
                formPanel.add(txtExperience);

                formPanel.add(new JLabel("New Position (leave blank to keep current):"));
                JTextField txtPosition = new JTextField(emp.getPosition());
                formPanel.add(txtPosition);

                formPanel.add(new JLabel("New Department (leave blank to keep current):"));
                JTextField txtDepartment = new JTextField(emp.getDepartment());
                formPanel.add(txtDepartment);

                formPanel.add(new JLabel("New Gross Salary (leave blank to keep current):"));
                JTextField txtSalary = new JTextField(String.valueOf(emp.getSalary()));
                formPanel.add(txtSalary);

                int result = JOptionPane.showConfirmDialog(frame, formPanel, "Update Employee Profile",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String bankAcc = txtBankAcc.getText().trim();
                    if (!bankAcc.isEmpty()) {
                        emp.updateBankAcc(bankAcc);
                    }

                    String name = txtName.getText().trim();
                    if (!name.isEmpty()) {
                        emp.updateName(name);
                    }

                    String dob = txtDOB.getText().trim();
                    if (!dob.isEmpty()) {
                        emp.updateDOB(LocalDate.parse(dob));
                    }

                    String address = txtAddress.getText().trim();
                    if (!address.isEmpty()) {
                        emp.updateAddress(address);
                    }

                    String emergencyContact = txtEmergencyContact.getText().trim();
                    if (!emergencyContact.isEmpty()) {
                        emp.updateEmergencyContact(emergencyContact);
                    }

                    String experience = txtExperience.getText().trim();
                    if (!experience.isEmpty()) {
                        emp.updateWorkingExperience(Arrays.asList(experience.split(",\\s*")));
                    }

                    String position = txtPosition.getText().trim();
                    if (!position.isEmpty()) {
                        emp.updatePosition(position);
                    }

                    String department = txtDepartment.getText().trim();
                    if (!department.isEmpty()) {
                        emp.updateDepartment(department);
                    }

                    String salaryInput = txtSalary.getText().trim();
                    if (!salaryInput.isEmpty()) {
                        emp.updateSalary(Double.parseDouble(salaryInput));
                    }

                    hrManager.updateEmployeeProfile(empID, emp);
                    JOptionPane.showMessageDialog(frame, "Employee profile updated successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void changePassword(String username, String password, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        panel.add(oldPasswordLabel);
        panel.add(oldPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.equals(password)) {
                setPassword(nric, newPassword);
                if (!newPassword.equals(confirmPassword) || newPassword.equals("")) {
                    JOptionPane.showMessageDialog(null, "New password and confirmation do not match.");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Password changed successfully.");
                frame.dispose();
                new AuthApp();
            } else {
                JOptionPane.showMessageDialog(null, "Old password is incorrect.");
            }
        }
    }

    private void setPassword(String nric, String password) {
        userPasswords.put(nric, password);
        updatePasswordInFile(nric, password);
    }

    private void updatePasswordInFile(String nric, String password) {
        File file = new File("users.txt");
        File tempFile = new File("user_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String filenric = parts[5];

                if (filenric.equals(nric)) {
                    parts[1] = password;
                    writer.write(String.join(",", parts));
                    updated = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!updated) {
                String username = "defaultUsername";
                String additionalInfo1 = "defaultInfo1";
                String additionalInfo2 = "defaultInfo2";
                String additionalInfo3 = "defaultInfo3";
                String additionalInfo4 = "defaultInfo4";
                writer.write(username + "," + password + "," + additionalInfo1 + "," + additionalInfo2 + ","
                        + additionalInfo3 + "," + additionalInfo4 + "," + nric);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.err.println("Could not delete the original file.");
        }
    }

    public static void main(String[] args) {
        String password = "";
        String nric = "";
        new hrOfficer(password, nric).runhrOfficer();
    }
}