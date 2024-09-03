import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Selector extends JFrame {
    private JComboBox<String> employeeSelector;
    private ArrayList<String> employeeIDs;

    public Selector() {
        super("Employee Payroll Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        // Read employee IDs (ICNos) from the file
        employeeIDs = readEmployeeIDs();
        employeeSelector = new JComboBox<>(employeeIDs.toArray(new String[0]));
        
        add(new JLabel("Select Employee ICNo:"));
        add(employeeSelector);

        // Button to handle selection
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            String selectedID = (String) employeeSelector.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Selected Employee ICNo: " + selectedID);
            // You can add code to process the payroll for the selected employee here
        });
        add(selectButton);

        setVisible(true);
    }

    private ArrayList<String> readEmployeeIDs() {
        ArrayList<String> ids = new ArrayList<>();
        File file = new File("SalaryIncrement.txt");  // File is named SalaryIncrement.txt

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ICNo:")) {
                    // Extract the ICNo from the line
                    String icNo = line.substring(6).trim();  // Skip "ICNo: " and trim spaces
                    ids.add(icNo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading SalaryIncrement.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return ids;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Selector::new);
    }
}
