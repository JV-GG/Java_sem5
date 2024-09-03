import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PR extends JFrame {
    public MainMenu() {
        super("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridBagLayout());
        
        // Create GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Button for Employee Pay Slip
        JButton employeePaySlipButton = new JButton("Employee Pay Slip");
        employeePaySlipButton.setFont(new Font("Arial", Font.BOLD, 14));
        employeePaySlipButton.setBackground(new Color(70, 130, 180));
        employeePaySlipButton.setForeground(Color.WHITE);
        employeePaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Selector(); // Open the Selector window to choose an employee
            }
        });
        add(employeePaySlipButton, gbc);

        // Button for Edit Employee Pay Slip
        JButton editPaySlipButton = new JButton("Edit Employee Pay Slip");
        editPaySlipButton.setFont(new Font("Arial", Font.BOLD, 14));
        editPaySlipButton.setBackground(new Color(70, 130, 180));
        editPaySlipButton.setForeground(Color.WHITE);
        editPaySlipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the functionality to edit an employee's payslip
                JOptionPane.showMessageDialog(MainMenu.this, "Edit Employee Pay Slip functionality is not yet implemented.");
            }
        });
        gbc.gridy = 1;
        add(editPaySlipButton, gbc);

        // Button for Overview All Employee Pay Slips
        JButton overviewPaySlipsButton = new JButton("Overview All Employee Pay Slips");
        overviewPaySlipsButton.setFont(new Font("Arial", Font.BOLD, 14));
        overviewPaySlipsButton.setBackground(new Color(70, 130, 180));
        overviewPaySlipsButton.setForeground(Color.WHITE);
        overviewPaySlipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the functionality to overview all employee payslips
                JOptionPane.showMessageDialog(MainMenu.this, "Overview All Employee Pay Slips functionality is not yet implemented.");
            }
        });
        gbc.gridy = 2;
        add(overviewPaySlipsButton, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
