import java.awt.*;
import java.awt.desktop.UserSessionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class PROfficer extends JFrame {
    public PROfficer() {
        super("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize by default
        setLayout(new BorderLayout());

        // Welcome message at the top left
        JLabel welcomeLabel = new JLabel("Welcome Lengzai Payroll Officer", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Time New Roman", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(70, 130, 180));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Button for Employee Pay Slip
        JButton employeePaySlipButton = new RoundedButton("Employee Pay Slip");
        employeePaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SalaryCalculator(); // Open SalaryCalculator window
                dispose(); // Close the current window
            }
        });
        buttonPanel.add(employeePaySlipButton, gbc);

        // Button for Edit Employee Pay Slip
        JButton editPaySlipButton = new RoundedButton("Edit Employee Pay Slip");
        editPaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Payslipupdate(); // Open Payslipupdate window
                dispose(); // Close the current window
            }
        });
        gbc.gridx = 1;
        buttonPanel.add(editPaySlipButton, gbc);

        // Button for Overview All Employee Pay Slips
        JButton overviewPaySlipsButton = new RoundedButton("Overview All Employee Pay Slips");
        overviewPaySlipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PROfficer.this, "Overview All Employee Pay Slips functionality is not yet implemented.");
            }
        });
        gbc.gridx = 2;
        buttonPanel.add(overviewPaySlipsButton, gbc);

        // Add the Change Password Button
        JButton changePasswordButton = new RoundedButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the change password dialog
                new ChangePasswordDialog(PROfficer.this); // Assuming this is similar to the one in hrManager
            }
        });
        gbc.gridx = 3;
        buttonPanel.add(changePasswordButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Define the ChangePasswordDialog class similar to the hrManager
    public class ChangePasswordDialog extends JDialog {
        private static final String USERS_FILE = "users.txt"; // File path for users data
    
        public ChangePasswordDialog(JFrame parent) {
            super(parent, "Change Password", true);
            setLayout(new GridLayout(3, 2));
    
            JLabel oldPasswordLabel = new JLabel("Old Password:");
            JPasswordField oldPasswordField = new JPasswordField();
            JLabel newPasswordLabel = new JLabel("New Password:");
            JPasswordField newPasswordField = new JPasswordField();
            JButton changeButton = new JButton("Change Password");
            JButton cancelButton = new JButton("Cancel");
    
            add(oldPasswordLabel);
            add(oldPasswordField);
            add(newPasswordLabel);
            add(newPasswordField);
            add(changeButton);
            add(cancelButton);
    
            changeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String oldPassword = new String(oldPasswordField.getPassword());
                    String newPassword = new String(newPasswordField.getPassword());
                    
                    // Assuming username is retrieved from a method or passed to the dialog
                    String username = getCurrentUsername();
                    
                    if (changePassword(username, oldPassword, newPassword)) {
                        JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Password changed successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Old password is incorrect.");
                    }
                }
            });
    
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
    
            pack();
            setLocationRelativeTo(parent);
            setVisible(true);
        }
    
        private boolean changePassword(String username, String oldPassword, String newPassword) {
            List<String> fileLines = new ArrayList<>();
            boolean passwordChanged = false;
    
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(username)) {
                        if (parts[1].equals(oldPassword)) {
                            // Update password
                            fileLines.add(username + "," + newPassword + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5]);
                            passwordChanged = true;
                        } else {
                            // Incorrect old password
                            fileLines.add(line);
                        }
                    } else {
                        // Not the user we're looking for
                        fileLines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    
            if (passwordChanged) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
                    for (String fileLine : fileLines) {
                        writer.write(fileLine);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return passwordChanged;
        }

        private String getCurrentUsername() {
            // Placeholder for actual implementation to retrieve the current username
            // For instance, this can be passed to the dialog or retrieved from user session
            return "JV"; // Example static username for demonstration purposes
        }
    }

    // RoundedButton class as before
    private static class RoundedButton extends JButton {
        private static final int ARC_WIDTH = 20;
        private static final int ARC_HEIGHT = 20;

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.BOLD, 16));
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(220, 60));
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (isEnabled()) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

                g2d.setColor(getForeground());
                FontMetrics fm = g2d.getFontMetrics();
                String text = getText();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(text, x, y);

                g2d.dispose();
            } else {
                super.paintComponent(g);
            }
        }

        @Override
        protected void paintBorder(Graphics g) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PROfficer::new);
    }
}
