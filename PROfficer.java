import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PROfficer extends JFrame {
    public PROfficer() {
        super("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  
        setLayout(new BorderLayout());

       
        JLabel welcomeLabel = new JLabel("Welcome Lengzai Payroll Officer", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Time New Roman", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(70, 130, 180));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(welcomeLabel, BorderLayout.NORTH);

   
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;


        JButton employeePaySlipButton = new RoundedButton("Employee Pay Slip");
        employeePaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SalaryCalculator(); 
                dispose(); 
            }
        });
        buttonPanel.add(employeePaySlipButton, gbc);

      
        JButton editPaySlipButton = new RoundedButton("Edit Employee Pay Slip");
        editPaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Payslipupdate(); 
                dispose(); 
            }
        });
        gbc.gridx = 1;
        buttonPanel.add(editPaySlipButton, gbc);

        // Button for Overview All Employee Pay Slips

JButton overviewPaySlipsButton = new RoundedButton("Overview All Employee Pay Slips");
overviewPaySlipsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new PayslipViewer(); 
        dispose();
    }
});
gbc.gridx = 2;
buttonPanel.add(overviewPaySlipsButton, gbc);


 
        JButton changePasswordButton = new RoundedButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       
                new ChangePasswordDialog(PROfficer.this, PROfficer.this); 
            }
        });
        gbc.gridx = 3;
        buttonPanel.add(changePasswordButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public class ChangePasswordDialog extends JDialog {
        private static final String USERS_FILE = "users.txt"; 
        private JFrame parentFrame; 

        public ChangePasswordDialog(JFrame parent, JFrame profficer) {
            super(parent, "Change Password", true);
            this.parentFrame = profficer; 
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

            
                    String username = getCurrentUsername();

                    if (changePassword(username, oldPassword, newPassword)) {
                        JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Password changed successfully!");
                        dispose();
                        parentFrame.dispose();
                        AuthApp.main(null); 
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
                         
                            fileLines.add(username + "," + newPassword + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5]);
                            passwordChanged = true;
                        } else {
                           
                            fileLines.add(line);
                        }
                    } else {
                       
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
   
            return "JV"; 
        }
    }

  
    private static class RoundedButton extends JButton {
        private static final int ARC_WIDTH = 20;
        private static final int ARC_HEIGHT = 20;

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.BOLD, 16));
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(220, 60));  // Set preferred size for buttons
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
