import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        userManagement = new UserManagement("users.txt");

        // Adding panels
        mainPanel.add(new LoginPanel(), "Login");
        mainPanel.add(new RegisterPanel(), "Register");
        mainPanel.add(new DashboardPanel(), "Dashboard");
        mainPanel.add(new AdminPanel(userManagement, cardLayout, mainPanel), "Admin");

        add(mainPanel);
        setVisible(true);
    }

    public String[] getUserDetails(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void lockAccount(String username) {
        updateUser(username, 3, "true");
    }

    public void resetFailedAttempts(String username) {
        updateUser(username, 4, "0");
    }

    public int incrementFailedAttempts(String username) {
        String[] userDetails = getUserDetails(username);
        int failedAttempts = Integer.parseInt(userDetails[4]) + 1;
        updateUser(username, 4, String.valueOf(failedAttempts));
        return failedAttempts;
    }

    public void resetPassword(String username, String newPassword) {
        updateUser(username, 1, newPassword);
    }

    public void updateUser(String username, int indexToUpdate, String newValue) {
        File tempFile = new File("temp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    parts[indexToUpdate] = newValue;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public boolean registerUser(String username, String password, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + "," + password + "," + role + ",false,0");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    class LoginPanel extends JPanel {
        JTextField usernameField;
        JPasswordField passwordField;

        public LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();
            usernameField.setPreferredSize(new Dimension(200, 30));

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(200, 30));

            JButton loginButton = new JButton("Login");

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = String.valueOf(passwordField.getPassword());

                    String[] userDetails = getUserDetails(username);
                    if (userDetails != null) {
                        if (Boolean.parseBoolean(userDetails[3])) {
                            JOptionPane.showMessageDialog(null, "Account is locked. Contact the System Administrator.");
                        } else if (userDetails[1].equals(password)) {
                            resetFailedAttempts(username);
                            currentUserRole = userDetails[2];
                            currentUsername = username;
                            JOptionPane.showMessageDialog(null, "Login successful!");
                            if (currentUserRole.equals("System Administrator")) {
                                cardLayout.show(mainPanel, "Admin");
                            } else if (currentUserRole.equals("Employee")) {
                                runEmployeeClass(username);
                            } else {
                                cardLayout.show(mainPanel, "Dashboard");
                            }

                        } else {
                            int failedAttempts = incrementFailedAttempts(username);
                            if (failedAttempts >= 3) {
                                lockAccount(username);
                                JOptionPane.showMessageDialog(null, "Account locked due to 3 failed login attempts.");
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid password. " + (3 - failedAttempts) + " attempts remaining.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username.");
                    }
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(usernameLabel, gbc);
            gbc.gridx = 1;
            add(usernameField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            add(passwordField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2; // span across both columns
            add(loginButton, gbc);
        }

        private void runEmployeeClass(String username) {
            SwingUtilities.invokeLater(() -> {
                Employee employee = new Employee(username);
                employee.createAndShowGUI();
            });
        }
    }

    class RegisterPanel extends JPanel {
        JTextField usernameField;
        JPasswordField passwordField;
        JComboBox<String> roleBox;

        public RegisterPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();

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
                String password = String.valueOf(passwordField.getPassword());
                String role = roleBox.getSelectedItem().toString();

                if (registerUser(username, password, role)) {
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
            add(passwordLabel, gbc);
            gbc.gridx = 1;
            add(passwordField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(roleLabel, gbc);
            gbc.gridx = 1;
            add(roleBox, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
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
                    System.exit(0); // Exit the application
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
            setLayout(new GridLayout(6, 1));

            add(createButton("Create User", this::showCreateUserPanel));
            add(createButton("View Users", this::showUserList));
            add(createButton("Update User", this::updateUser));
            add(createButton("Delete User", this::deleteUser));
            add(createButton("Unlock User", this::unlockUser));
            add(createButton("Logout", e -> cardLayout.show(mainPanel, "Login")));
        }

        private JButton createButton(String text, ActionListener actionListener) {
            JButton button = new JButton(text);
            button.addActionListener(actionListener);
            return button;
        }

        private void showCreateUserPanel(ActionEvent e) {
            JPanel createUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);

            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(20);

            JLabel roleLabel = new JLabel("Role:");
            String[] roles = { "Employee", "System Administrator", "Human Resource Officer", "Department Manager",
                    "Payroll Officer" };
            JComboBox<String> roleBox = new JComboBox<>(roles);

            JButton createButton = new JButton("Create");
            JButton cancelButton = new JButton("Cancel");

            createButton.addActionListener(e1 -> {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String role = roleBox.getSelectedItem().toString();

                if (userManagement.registerUser(username, password, role)) {
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
            createUserPanel.add(roleLabel, gbc);
            gbc.gridx = 1;
            createUserPanel.add(roleBox, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            createUserPanel.add(createButton, gbc);
            gbc.gridy = 4;
            createUserPanel.add(cancelButton, gbc);

            mainPanel.add(createUserPanel, "CreateUser");
            cardLayout.show(mainPanel, "CreateUser");
        }

        private void showUserList(ActionEvent e) {
            JTextArea userListArea = new JTextArea(20, 30);
            userListArea.setEditable(false);
            userListArea.setText(userManagement.getAllUsers());
            JOptionPane.showMessageDialog(null, new JScrollPane(userListArea), "User List",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void updateUser(ActionEvent e) {
            JPanel updateUserPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);

            JLabel newPasswordLabel = new JLabel("New Password:");
            JPasswordField newPasswordField = new JPasswordField(20);

            JButton updateButton = new JButton("Update Password");
            JButton cancelButton = new JButton("Cancel");

            updateButton.addActionListener(e1 -> {
                String username = usernameField.getText();
                String newPassword = String.valueOf(newPasswordField.getPassword());

                // Call the updateUser method to update the password (index 1 is for password)
                userManagement.updateUser(username, 1, newPassword);
                JOptionPane.showMessageDialog(null, "Password updated successfully.");
                cardLayout.show(mainPanel, "Admin");
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            updateUserPanel.add(usernameLabel, gbc);
            gbc.gridx = 1;
            updateUserPanel.add(usernameField, gbc);
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

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);

            JButton deleteButton = new JButton("Delete");
            JButton cancelButton = new JButton("Cancel");

            deleteButton.addActionListener(e1 -> {
                String username = usernameField.getText();
                userManagement.deleteUser(username);
                JOptionPane.showMessageDialog(null, "User deleted successfully.");
                cardLayout.show(mainPanel, "Admin");
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            deleteUserPanel.add(usernameLabel, gbc);
            gbc.gridx = 1;
            deleteUserPanel.add(usernameField, gbc);
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

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);

            JButton unlockButton = new JButton("Unlock");
            JButton cancelButton = new JButton("Cancel");

            unlockButton.addActionListener(e1 -> {
                String username = usernameField.getText();
                userManagement.updateUser(username, 3, "false"); // Unlock account
                userManagement.updateUser(username, 4, "0"); // Reset failed attempts
                JOptionPane.showMessageDialog(null, "User unlocked successfully.");
                cardLayout.show(mainPanel, "Admin");
            });

            cancelButton.addActionListener(e1 -> cardLayout.show(mainPanel, "Admin"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            unlockUserPanel.add(usernameLabel, gbc);
            gbc.gridx = 1;
            unlockUserPanel.add(usernameField, gbc);
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

    public UserManagement(String filePath) {
        this.userFile = new File(filePath);
    }

    public boolean registerUser(String username, String password, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + "," + password + "," + role + ",false,0");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public String[] getUserDetails(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(String username, int indexToUpdate, String newValue) {
        File tempFile = new File("temp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    parts[indexToUpdate] = newValue;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userFile.delete()) {
            if (!tempFile.renameTo(userFile)) {
                System.out.println("Failed to rename temp file to user file");
            }
        } else {
            System.out.println("Failed to delete the original user file");
        }
    }

    public void deleteUser(String username) {
        File tempFile = new File("temp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(userFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(username)) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

            String[][] userList = new String[userCount][4];
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(userFile));
            int index = 0;

            while ((line = br2.readLine()) != null) {
                String[] parts = line.split(",");
                userList[index][0] = parts[0];
                userList[index][1] = parts[2];
                userList[index][2] = parts[3];
                userList[index][3] = parts[4];
                index++;
            }
            br2.close();
            return userList;

        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return userList.toString();
    }

}
