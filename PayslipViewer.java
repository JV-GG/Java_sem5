import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class PayslipViewer extends JFrame {
    private JComboBox<String> fileDropdown;
    private JTextArea fileContentArea;
    private JButton viewButton;

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

        // Dropdown for file selection
        fileDropdown = new JComboBox<>(getPayslipFiles());
        fileDropdown.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Select Payslip File:"), gbc);
        gbc.gridx = 1;
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
        gbc.gridx = 2;
        topPanel.add(viewButton, gbc);

        // Create a panel for file content
        JPanel contentPanel = new JPanel(new BorderLayout());
        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setLineWrap(true);
        fileContentArea.setWrapStyleWord(true);
        fileContentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Payslip Content"));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private String[] getPayslipFiles() {
        File folder = new File("Payslips");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return new String[0];
        
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }
        return fileNames;
    }
    
    private void viewFileContent() {
        String selectedFile = (String) fileDropdown.getSelectedItem();
        if (selectedFile != null) {
            File file = new File("Payslips/" + selectedFile);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                fileContentArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayslipViewer::new);
    }
}
