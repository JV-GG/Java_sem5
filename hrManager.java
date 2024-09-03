import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class hrManager {
    private JFrame frame;
    ProfileManagement hrManager = new ProfileManagement();

    public void runhrManager() {
        // Create the frame
        frame = new JFrame("HR Manager");
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the panel
        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Set up the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 10, 20, 10); // Top, Left, Bottom, Right padding
        gbc.anchor = GridBagConstraints.CENTER;

        // Add the title label
        JLabel titleLabel = new JLabel("HR Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Create buttons with the same styling
        JButton btnCreate = new JButton("Create Employee Profile");
        JButton btnRetrieve = new JButton("Retrieve Employee Profile");
        JButton btnUpdate = new JButton("Update Employee Profile");
        JButton btnExit = new JButton("Exit");

        JButton[] buttons = {btnCreate, btnRetrieve, btnUpdate, btnExit};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            buttons[i].setPreferredSize(new Dimension(300, 50));
            gbc.gridy = i + 1;
            panel.add(buttons[i], gbc);
        }

        // Add action listeners to handle button clicks
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createEmployeeProfile();
            }
        });

        btnRetrieve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retrieveEmployeeProfile();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmployeeProfile();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AuthApp();
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }


    public void createEmployeeProfile() {
        // Create the form panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Helper method to add labels and text fields
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
        addLabeledField(formPanel, gbc, "Salary (RM):", new JTextField());
        
        // Adjust field sizes
        Component[] components = formPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                component.setPreferredSize(new Dimension(200, 25));
            }
        }
        
        // Display the dialog
        int result = JOptionPane.showConfirmDialog(frame, formPanel, "Create Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Collect the data from the fields
            String NRIC = ((JTextField) components[1]).getText();
            String Password = ((JTextField) components[3]).getText();
            String BankAcc = ((JTextField) components[5]).getText();
            String Name = ((JTextField) components[7]).getText();
            String Gender = ((JTextField) components[9]).getText();
            LocalDate DOB = LocalDate.parse(((JTextField) components[11]).getText());
            String address = ((JTextField) components[13]).getText();
            String emergencyContact = ((JTextField) components[15]).getText();
            List<String> experience = Arrays.asList(((JTextField) components[17]).getText().split(","));
            String department = ((JTextField) components[19]).getText();
            String position = ((JTextField) components[21]).getText();
            double salary = Double.parseDouble(((JTextField) components[23]).getText());
    
            // Create and register the new employee profile
            EmpProfile newEmployee = new EmpProfile(NRIC, Password, BankAcc, Name, Gender, DOB, address, emergencyContact, experience, department, position, salary);
            hrManager.createEmployeeProfile(newEmployee);
        }
    }
    
    // Helper method to add labeled fields
    private void addLabeledField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }
    
    private void retrieveEmployeeProfile() {
        // Input dialog for Employee ID
        String empID = JOptionPane.showInputDialog(frame, "Enter Employee ID:");
    
        if (empID != null && !empID.trim().isEmpty()) {
            EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);
    
            if (emp != null) {
                // Create a panel to display the employee profile
                JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
                profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
                // Add profile details to the panel
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
    
                profilePanel.add(new JLabel("Employee Salary (RM):"));
                profilePanel.add(new JLabel(String.format("%.2f", emp.getSalary())));
    
                // Display the profile information in a dialog
                JOptionPane.showMessageDialog(frame, profilePanel, "Employee Profile", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateEmployeeProfile() {
        // Input dialog for Employee ID
        String empID = JOptionPane.showInputDialog(frame, "Enter Employee NRIC/Passport:");
    
        if (empID != null && !empID.trim().isEmpty()) {
            EmpProfile emp = hrManager.retrieveEmployeeProfile(empID);
    
            if (emp != null) {
                // Create a panel with GridLayout for the update form
                JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
                // Add input fields with existing data as placeholder text
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
    
                formPanel.add(new JLabel("New Salary (leave blank to keep current):"));
                JTextField txtSalary = new JTextField(String.valueOf(emp.getSalary()));
                formPanel.add(txtSalary);
    
                // Display the form in a dialog
                int result = JOptionPane.showConfirmDialog(frame, formPanel, "Update Employee Profile", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Update employee data based on user input
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
    
                    // Save the updated profile
                    hrManager.updateEmployeeProfile(empID, emp);
                    JOptionPane.showMessageDialog(frame, "Employee profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new hrManager().runhrManager();
    }
}
