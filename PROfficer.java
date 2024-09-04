import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PROfficer extends JFrame {
    public PROfficer() {
        super("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);  // Increased size for better visibility
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
                // Open SalaryCalculator window
                new SalaryCalculator().setVisible(true); // Make sure SalaryCalculator is visible
                dispose(); // Close the current window
            }
        });
        buttonPanel.add(employeePaySlipButton, gbc);

        // Button for Edit Employee Pay Slip
        JButton editPaySlipButton = new RoundedButton("Edit Employee Pay Slip");
        editPaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Payslipupdate window
                new Payslipupdate().setVisible(true); // Make sure Payslipupdate is visible
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
                // Open PayslipViewer window
                new PayslipViewer().setVisible(true); // Make sure PayslipViewer is visible
                dispose(); // Close the current window
            }
        });
        gbc.gridx = 2;
        buttonPanel.add(overviewPaySlipsButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Custom Rounded Button Class
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

                // Paint gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

                // Paint the button text
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
            // Override to remove default border
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PROfficer::new);
    }
}
