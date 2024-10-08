import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class AuthApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private File userFile = new File("users.txt");
    private String currentUserRole;
    private String currentUsername;
    private UserManagement userManagement;

    public AuthApp() {

        setTitle("HRMS System");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        userManagement = new UserManagement("users.txt");

        mainPanel.add(new LoginPanel(), "Login");
        mainPanel.add(new RegisterPanel(), "Register");
        mainPanel.add(new DashboardPanel(), "Dashboard");
        mainPanel.add(new AdminPanel(userManagement, cardLayout, mainPanel), "Admin");

        add(mainPanel);
        setVisible(true);
    }

    public String[] getUserDetails(String nric) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void lockAccount(String nric) {
        updateUser(nric, 3, "true");
    }

    public void resetFailedAttempts(String nric) {
        updateUser(nric, 4, "0");
    }

    public int incrementFailedAttempts(String nric) {
        String[] userDetails = getUserDetails(nric);
        int failedAttempts = Integer.parseInt(userDetails[4]) + 1;
        updateUser(nric, 4, String.valueOf(failedAttempts));
        return failedAttempts;
    }

    public void updateUser(String nric, int indexToUpdate, String newValue) {
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    parts[indexToUpdate] = newValue;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public boolean registerUser(String username, String password, String role, String nric) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + "," + password + "," + role + ",false,0," + nric);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        return true;
    }

    class LoginPanel extends JPanel {
        JTextField nricField;
        JPasswordField passwordField;

        public LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel nricLabel = new JLabel("NRIC:");
            nricField = new JTextField();
            nricField.setPreferredSize(new Dimension(300, 40));

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(300, 40));

            JButton loginButton = new JButton("Login");
            loginButton.setPreferredSize(new Dimension(200, 50));

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nric = nricField.getText();
                    String password = String.valueOf(passwordField.getPassword());

                    String[] userDetails = getUserDetails(nric);
                    if (userDetails != null) {
                        if (Boolean.parseBoolean(userDetails[3])) {
                            JOptionPane.showMessageDialog(null, "Account is locked. Contact the System Administrator.");
                        } else if (userDetails[1].equals(password)) {
                            resetFailedAttempts(nric);
                            currentUserRole = userDetails[2];
                            currentUsername = nric;
                            JOptionPane.showMessageDialog(null, "Login successful!");
                            nricField.setText("");
                            passwordField.setText("");

                            switch (currentUserRole) {
                                case "System Administrator" -> {
                                    JOptionPane.showMessageDialog(null, "Welcome System Administrator!");
                                    cardLayout.show(mainPanel, "Admin");
                                }
                                case "Human Resource Officer" -> {
                                    JOptionPane.showMessageDialog(null, "Welcome Human Resource Officer!");
                                    nricField.setText("");
                                    passwordField.setText("");
                                    hrOfficer manager = new hrOfficer(password, nric);
                                    manager.runhrOfficer();
                                    SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                                }
                                case "Department Manager" -> {
                                    JOptionPane.showMessageDialog(null, "Welcome Department Manager!");
                                    nricField.setText("");
                                    passwordField.setText("");

                                    ManagerMenu managermenu = new ManagerMenu();
                                    managermenu.setVisible(true);
                                    SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                                }
                                case "Payroll Officer" -> {
                                    JOptionPane.showMessageDialog(null, "Welcome Payroll Officer!");
                                    nricField.setText("");
                                    passwordField.setText("");
        
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new PROfficer();
                                        }
                                    });
                                    SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                                }
                                case "Employee" -> {
                                    JOptionPane.showMessageDialog(null, "Welcome Employee!");
                                    nricField.setText("");
                                    passwordField.setText("");
                                    runEmployeeClass(nric, password);
                                    SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                                }
                                default -> cardLayout.show(mainPanel, "Dashboard");
                            }
                        } else {
                            int failedAttempts = incrementFailedAttempts(nric);
                            if (failedAttempts >= 3) {
                                lockAccount(nric);
                                JOptionPane.showMessageDialog(null, "Account locked due to 3 failed login attempts.");
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid password. " + (3 - failedAttempts) + " attempts remaining.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid NRIC.");
                    }
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(nricLabel, gbc);
            gbc.gridx = 1;
            add(nricField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            add(passwordField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            add(loginButton, gbc);
        }

        private void runEmployeeClass(String nric, String password) {
            String[] userDetails = getUserDetails(nric);
            if (userDetails != null) {
                String username = userDetails[0];
                SwingUtilities.invokeLater(() -> {
                    Employee employee = new Employee(username, password, nric);
                    employee.createAndShowGUI();
                });
            } else {
                JOptionPane.showMessageDialog(null, "Invalid NRIC. Please try again.");
            }
        }
    }

    class RegisterPanel extends JPanel {
        JTextField usernameField;
        JTextField nricField;
        JPasswordField passwordField;
        JComboBox<String> roleBox;

        public RegisterPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();

            JLabel nricLabel = new JLabel("NRIC:");
            nricField = new JTextField();

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();

            JLabel roleLabel = new JLabel("Role:");
            String[] roles = { "Employee", "System Administrator", "Human Resource Officer", "Department Manager",
                    "Payroll Officer" };
            roleBox = new JComboBox<>(roles);

            JButton registerButton = new JButton("Register");
            JButton goToLoginButton = new JButton("Back to Login");

            registerButton.addActionListener(e -> {
                String username = usernameField.getText();
                String nric = nricField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String role = roleBox.getSelectedItem().toString();

                if (registerUser(username, password, role, nric)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    cardLayout.show(mainPanel, "Login");
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists.");
                }
            });

            goToLoginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(usernameLabel, gbc);
            gbc.gridx = 1;
            add(usernameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(nricLabel, gbc);
            gbc.gridx = 1;
            add(nricField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            add(passwordField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            add(roleLabel, gbc);
            gbc.gridx = 1;
            add(roleBox, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(registerButton, gbc);
            gbc.gridx = 1;
            add(goToLoginButton, gbc);
        }
    }

    class DashboardPanel extends JPanel {
        public DashboardPanel() {
            setLayout(new BorderLayout());

            JLabel welcomeLabel = new JLabel("Welcome to the Dashboard!", SwingConstants.CENTER);
            JButton logoutButton = new JButton("Logout");
            JButton quitButton = new JButton("Quit Application");

            logoutButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "You have been logged out.");
                cardLayout.show(mainPanel, "Login");
            });

            quitButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Application",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); 
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(logoutButton);
            buttonPanel.add(quitButton);

            add(welcomeLabel, BorderLayout.CENTER);
            add(logoutButton, BorderLayout.SOUTH);
        }
    }

    class AdminPanel extends JPanel {
        private UserManagement userManagement;
        private CardLayout cardLayout;
        private JPanel mainPanel;

        public AdminPanel(UserManagement userManagement, CardLayout cardLayout, JPanel mainPanel) {
            this.userManagement = userManagement;
            this.cardLayout = cardLayout;
            this.mainPanel = mainPanel;

            setLayout(new GridBagLayout());

            JLabel titleLabel = new JLabel("Admin Panel");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setBounds(50, 10, 400, 70);
            add(titleLabel);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20);
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.anchor = GridBagConstraints.CENTER;

            add(createButton("Create User", this::showCreateUserPanel), gbc);
            add(createButton("View Users", this::showUserList), gbc);
            add(createButton("Update User", this::updateUser), gbc);
            add(createButton("Delete User", this::deleteUser), gbc);
            add(createButton("Unlock User", this::unlockUser), gbc);
            add(createButton("View Support Ticket", this::viewsupport), gbc);
            add(createButton("Logout", e -> cardLayout.show(mainPanel, "Login")), gbc);
        }

        private JButton createButton(String text, ActionListener actionListener) {
            JButton button = new JButton(text);
            button.addActionListener(actionListener);
            button.setPreferredSize(new Dimension(300, 60));
            button.setFont(new Font("Arial", Font.BOLD, 18));

            return button;
        }

        private void showCreateUserPanel(ActionEvent e) {
            String[] roles = { "Employee", "System Administrator", "Human Resource Officer", "Department Manager",
                    "Payroll Officer" };
            String selectedRole = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a role for the new user:",
                    "Role Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    roles,
                    roles[0]);

            if (selectedRole == null) {
                return;
            } else if (selectedRole.equals("Employee")) {
                hrOfficer hrManager = new hrOfficer("", "");
                hrManager.createEmployeeProfile();
            } else if (!selectedRole.equals("Employee")) {
                JPanel createUserPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                JLabel usernameLabel = new JLabel("Username:");
                JTextField usernameField = new JTextField(20);

                JLabel passwordLabel = new JLabel("Password:");
                JPasswordField passwordField = new JPasswordField(20);

                JLabel nricLabel = new JLabel("NRIC:");
                JTextField nricField = new JTextField(20);

                JButton createButton = new JButton("Create");
                JButton cancelButton = new JButton("Cancel");

                createButton.addActionListener(e1 -> {
                    String username = usernameField.getText();
                    String password = String.valueOf(passwordField.getPassword());
                    String nric = nricField.getText();

                    if (userManagement.registerUser(username, password, selectedRole, nric)) {
                        JOptionPane.showMessageDialog(null, "User created successfully.");
                        cardLayout.show(mainPanel, "Admin");
                    } else {
                        JOptionPane.showMessageDialog(null, "Username already exists.");
                    }
                });

                cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

                gbc.gridx = 0;
                gbc.gridy = 0;
                createUserPanel.add(usernameLabel, gbc);
                gbc.gridx = 1;
                createUserPanel.add(usernameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                createUserPanel.add(passwordLabel, gbc);
                gbc.gridx = 1;
                createUserPanel.add(passwordField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                createUserPanel.add(nricLabel, gbc);
                gbc.gridx = 1;
                createUserPanel.add(nricField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 2;
                createUserPanel.add(createButton, gbc);

                gbc.gridy = 5;
                createUserPanel.add(cancelButton, gbc);

                mainPanel.add(createUserPanel, "CreateUser");
                cardLayout.show(mainPanel, "CreateUser");
            }
        }

        private void showUserList(ActionEvent e) {
            JTextArea userListArea = new JTextArea(20, 60);
            userListArea.setEditable(false);
            userListArea.setText(userManagement.getAllUsers());
            JOptionPane.showMessageDialog(null, new JScrollPane(userListArea), "User List",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void viewsupport(ActionEvent e) {
            JTextArea userListArea = new JTextArea(20, 60);
            userListArea.setEditable(false);
            userListArea.setText(userManagement.getAllSupportTicket());
            JOptionPane.showMessageDialog(null, new JScrollPane(userListArea), "Support Ticket List",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void updateUser(ActionEvent e) {
            JPanel updateUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel nricLabel = new JLabel("NRIC:");
            JTextField nricField = new JTextField(20);

            JLabel newPasswordLabel = new JLabel("New Password:");
            JPasswordField newPasswordField = new JPasswordField(20);

            JButton updateButton = new JButton("Update Password");
            JButton cancelButton = new JButton("Cancel");

            updateButton.addActionListener(e1 -> {
                String nric = nricField.getText();
                String newPassword = String.valueOf(newPasswordField.getPassword());

                if (newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Password Invalid");
                } else {
                    userManagement.updateUser(nric, 1, newPassword);
                    cardLayout.show(mainPanel, "Admin");
                }
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            updateUserPanel.add(nricLabel, gbc);
            gbc.gridx = 1;
            updateUserPanel.add(nricField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            updateUserPanel.add(newPasswordLabel, gbc);
            gbc.gridx = 1;
            updateUserPanel.add(newPasswordField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            updateUserPanel.add(updateButton, gbc);
            gbc.gridy = 3;
            updateUserPanel.add(cancelButton, gbc);

            mainPanel.add(updateUserPanel, "UpdateUser");
            cardLayout.show(mainPanel, "UpdateUser");
        }

        private void deleteUser(ActionEvent e) {
            JPanel deleteUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel nricLabel = new JLabel("NRIC:");
            JTextField nricField = new JTextField(20);

            JButton deleteButton = new JButton("Delete");
            JButton cancelButton = new JButton("Cancel");

            deleteButton.addActionListener(e1 -> {
                String nric = nricField.getText();
                userManagement.deleteUser(nric);
                cardLayout.show(mainPanel, "Admin");
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            deleteUserPanel.add(nricLabel, gbc);
            gbc.gridx = 1;
            deleteUserPanel.add(nricField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            deleteUserPanel.add(deleteButton, gbc);
            gbc.gridy = 2;
            deleteUserPanel.add(cancelButton, gbc);

            mainPanel.add(deleteUserPanel, "DeleteUser");
            cardLayout.show(mainPanel, "DeleteUser");
        }

        private void unlockUser(ActionEvent e) {
            JPanel unlockUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel nricLabel = new JLabel("NRIC:");
            JTextField nricField = new JTextField(20);

            JButton unlockButton = new JButton("Unlock");
            JButton cancelButton = new JButton("Cancel");

            unlockButton.addActionListener(e1 -> {
                String nric = nricField.getText();
                int[] indicesToUpdate = { 3, 4 };
                String[] newValues = { "false", "0" };
                userManagement.updateUser2(nric, indicesToUpdate, newValues);
                cardLayout.show(mainPanel, "Admin");
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            unlockUserPanel.add(nricLabel, gbc);
            gbc.gridx = 1;
            unlockUserPanel.add(nricField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            unlockUserPanel.add(unlockButton, gbc);
            gbc.gridy = 2;
            unlockUserPanel.add(cancelButton, gbc);

            mainPanel.add(unlockUserPanel, "UnlockUser");
            cardLayout.show(mainPanel, "UnlockUser");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthApp());
    }
}

class UserManagement {
    private File userFile;
    private File supportFile = new File("Employees/Employee_ticket.txt");

    public UserManagement(String filePath) {
        this.userFile = new File(filePath);
    }

    public boolean registerUser(String username, String password, String role, String nric) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + "," + password + "," + role + ",false,0," + nric);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        return true;
    }

    public String[] getUserDetails(String nric) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void updateUser2(String nric, int[] indicesToUpdate, String[] newValues) {
        File tempFile = new File("temp.txt");
        boolean nricFound = false;

        if (indicesToUpdate.length != newValues.length) {
            throw new IllegalArgumentException("Indices and values arrays must be of the same length.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    for (int i = 0; i < indicesToUpdate.length; i++) {
                        parts[indicesToUpdate[i]] = newValues[i];
                    }
                    line = String.join(",", parts);
                    nricFound = true;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        if (!nricFound) {
            JOptionPane.showMessageDialog(null, "No NRIC found: " + nric);
        } else {
            JOptionPane.showMessageDialog(null, "User with NRIC " + nric + " has unlocked.");
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public void updateUser(String nric, int indexToUpdate, String newValue) {
        File tempFile = new File("temp.txt");
        boolean nricFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric)) {
                    parts[indexToUpdate] = newValue;
                    line = String.join(",", parts);
                    nricFound = true;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        if (!nricFound) {
            JOptionPane.showMessageDialog(null, "No NRIC found: " + nric);
        } else {
            JOptionPane.showMessageDialog(null, "User with NRIC " + nric + " password updated");
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public void deleteUser(String nric) {
        File tempFile = new File("temp.txt");
        boolean nricFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[5].equals(nric) && !parts[5].equals("000000000000")) {
                    nricFound = true;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        if (!nricFound) {
            JOptionPane.showMessageDialog(null, "No NRIC found: " + nric);
        } else {
            JOptionPane.showMessageDialog(null, "User with NRIC " + nric + " deleted successfully.");
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public String[][] getUserList() {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            int userCount = 0;

            while ((line = br.readLine()) != null) {
                userCount++;
            }

            String[][] userList = new String[userCount][5];
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(userFile));
            int index = 0;

            while ((line = br2.readLine()) != null) {
                String[] parts = line.split(",");
                userList[index][0] = parts[0];
                userList[index][1] = parts[2];
                userList[index][2] = parts[3];
                userList[index][3] = parts[4];
                userList[index][4] = parts[5];
                index++;
            }
            br2.close();
            return userList;

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return new String[0][0];
    }

    public String getAllUsers() {
        StringBuilder userList = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                userList.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return userList.toString();
    }

    public String getAllSupportTicket() {
        StringBuilder supportList = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(supportFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                supportList.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return supportList.toString();
    }

}