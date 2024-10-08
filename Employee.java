import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Employee {
    private JFrame frame1;

    private static class AttendanceRecord {
        private String username;
        private LocalDateTime clockInTime;
        private LocalDateTime clockOutTime;

        public AttendanceRecord(String username, LocalDateTime clockInTime, LocalDateTime clockOutTime) {
            this.username = username;
            this.clockInTime = clockInTime;
            this.clockOutTime = clockOutTime;
        }

        public String getUsername() {
            return username;
        }

        public LocalDateTime getClockInTime() {
            return clockInTime;
        }

        public LocalDateTime getClockOutTime() {
            return clockOutTime;
        }

        public void setClockOutTime(LocalDateTime clockOutTime) {
            this.clockOutTime = clockOutTime;
        }
    }

    private List<AttendanceRecord> records = new ArrayList<>();
    private Map<String, String> userPasswords = new HashMap<>();
    private JLabel statusLabel;
    private String username;
    private String password;
    private String nric;

    public Employee(String username, String password, String nric) {
        this.username = username;
        this.password = password;
        this.nric = nric;
    }

    public void createAndShowGUI() {
        System.setProperty("sun.java2d.uiScale", "1.0");
        loadAttendanceRecords(nric);
        JFrame frame = new JFrame("Employee Attendance System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up full-screen mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            frame.setSize(1920, 1080);
        } else {
            frame.setUndecorated(true);
            gd.setFullScreenWindow(frame);
        }

        frame.setLayout(null);

        // Username display
        JLabel usernameLabel = new JLabel("Username: " + username, SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 60));
        usernameLabel.setBounds(0, 150, 1920, 70);
        frame.add(usernameLabel);
        JLabel usernameLabel2 = new JLabel("[Employee]", SwingConstants.LEFT);
        usernameLabel2.setFont(new Font("Arial", Font.BOLD, 40));
        usernameLabel2.setBounds(100, 100, 1920, 50);
        frame.add(usernameLabel2);

        // Create buttons
        JButton takeleaveButton = new JButton("Take Leave");
        JButton retrieveButton = new JButton("View Profile");
        JButton clockInButton = new JButton("Clock In");
        JButton clockOutButton = new JButton("Clock Out");
        JButton monthlyReportButton = new JButton("Monthly Report");
        JButton annualReportButton = new JButton("Annual Report");
        JButton changePasswordButton = new JButton("Change Password");
        JButton supportTicketButton = new JButton("Support Ticket");

        // Set button positions
        takeleaveButton.setBounds(680, 950, 150, 40);
        retrieveButton.setBounds(880, 950, 150, 40);
        clockInButton.setBounds(480, 300, 150, 50);
        clockOutButton.setBounds(680, 300, 150, 50);
        monthlyReportButton.setBounds(880, 300, 150, 50);
        annualReportButton.setBounds(1080, 300, 150, 50);
        changePasswordButton.setBounds(1280, 300, 200, 50);
        supportTicketButton.setBounds(1080, 950, 150, 40);

        // Add buttons to the frame
        frame.add(takeleaveButton);
        frame.add(retrieveButton);
        frame.add(clockInButton);
        frame.add(clockOutButton);
        frame.add(monthlyReportButton);
        frame.add(annualReportButton);
        frame.add(changePasswordButton);
        frame.add(supportTicketButton);

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        statusLabel.setBounds(0, 700, 1920, 50);
        frame.add(statusLabel);

        // Create a logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 40));
        logoutButton.setBounds(760, 800, 400, 100);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new AuthApp();
        });
        frame.add(logoutButton);

        // Button actions
        takeleaveButton.addActionListener(e -> takeleave());
        retrieveButton.addActionListener(e -> viewProfile(nric));
        clockInButton.addActionListener(e -> clockIn(username));
        clockOutButton.addActionListener(e -> clockOut(username));
        monthlyReportButton.addActionListener(e -> generateMonthlyReport());
        annualReportButton.addActionListener(e -> generateAnnualReport());
        changePasswordButton.addActionListener(e -> changePassword(username, password, frame));
        supportTicketButton.addActionListener(e -> supportTicket(username));

        frame.setVisible(true);
    }

    private void takeleave(){
        LeaveMenu leavemenu = new LeaveMenu();
        leavemenu.setVisible(true);
    }

    private void viewProfile(String nric) {
        ProfileManagement hrManager = new ProfileManagement();
        EmpProfile emp = hrManager.retrieveEmployeeProfile(nric);

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
            JOptionPane.showMessageDialog(frame1, profilePanel, "Employee Profile", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame1, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadAttendanceRecords(String nric) {
        String filename = "Employees/" + nric + "/" + nric + "_attendance.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String lastLine = null;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                lastLine = currentLine;
            }

            if (lastLine != null) {
                String[] parts = lastLine.split(", ");

                // Ensure the parts array has at least 3 elements (username, clockIn, clockOut
                // or "Still Clocked In")
                if (parts.length >= 3) {
                    String user = parts[0];
                    LocalDateTime clockIn = LocalDateTime.parse(parts[1],
                            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
                    LocalDateTime clockOut = null;

                    // Check if the clock out time is available
                    if (!parts[2].equals("Still Clocked In")) {
                        clockOut = LocalDateTime.parse(parts[2], DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
                    }

                    // Check if there's already a clock-in record for this user
                    boolean alreadyClockedIn = records.stream()
                            .anyMatch(record -> record.getUsername().equals(user) && record.getClockOutTime() == null);

                    // Only add the record if it's not already present
                    if (!alreadyClockedIn) {
                        AttendanceRecord record = new AttendanceRecord(user, clockIn, clockOut);
                        records.add(record);
                    }
                } else {
                    System.err.println("Invalid record format: " + lastLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clockIn(String username) {
        LocalDateTime now = LocalDateTime.now();
        boolean alreadyClockedIn = false;
        LocalDateTime lateThreshold = now.withHour(9).withMinute(31).withSecond(0).withNano(0); // Define the late
                                                                                                // threshold
        Long workingHours = (long) 0;

        // Check if the user is already clocked in
        for (AttendanceRecord record : records) {
            if (record.getUsername().equals(username) && record.getClockOutTime() == null) {
                alreadyClockedIn = true;
                break;
            }
        }

        // If the user is already clocked in, show a message
        if (alreadyClockedIn) {
            statusLabel.setText(username + " is already clocked in.");
        } else {
            // Create a new attendance record for the clock-in
            AttendanceRecord record = new AttendanceRecord(username, now, null);
            records.add(record);
            writeAttendanceToFile(record, workingHours); // Write the new clock-in record to the file

            // Check if the clock-in time is after the late threshold and update the status
            if (now.isAfter(lateThreshold)) {
                statusLabel.setText(username + " has clocked in at "
                        + now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")) + " (Late)");
            } else {
                statusLabel.setText(username + " has clocked in at "
                        + now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")) + " (Ontime)");
            }
        }
    }

    public void clockOut(String username) {
        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        for (AttendanceRecord record : records) {
            if (record.getUsername().equals(username) && record.getClockOutTime() == null) {
                record.setClockOutTime(now);

                // Calculate working hours
                long workingHours = java.time.Duration.between(record.getClockInTime(), now).toHours();
                writeAttendanceToFile(record, workingHours);
                statusLabel.setText(username + " has clocked out at "
                        + now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss")) + " (Hours: " + workingHours
                        + ")");
                found = true;
                break;
            }
        }
        if (!found) {
            statusLabel.setText(username + " is already clocked out.");
        }
    }

    private void writeAttendanceToFile(AttendanceRecord record, long workingHours) {

        String directoryPath = "Employees/" + nric;
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = directoryPath + File.separator + nric + "_attendance.txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        LocalDateTime lateThreshold = record.getClockInTime().withHour(9).withMinute(31).withSecond(0).withNano(0); // time

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            String attendanceLine = String.format("%s, %s, %s, Hours: %d",
                    record.getUsername(),
                    record.getClockInTime().format(formatter),
                    record.getClockOutTime() != null ? record.getClockOutTime().format(formatter) : "Still Clocked In",
                    workingHours);

            if (record.getClockInTime().isAfter(lateThreshold)) {
                attendanceLine += ", Late";
            } else {
                attendanceLine += ", Ontime";
            }

            writer.write(attendanceLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMonthlyReport() {
        String[] months = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        JComboBox<String> monthSelector = new JComboBox<>(months);

        monthSelector.setPreferredSize(new Dimension(500, 70));

        // Show a dialog for month selection
        int option = JOptionPane.showConfirmDialog(null, monthSelector, "Select Month",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedMonth = monthSelector.getSelectedIndex() + 1;
            String directoryPath = "Employees/" + nric;
            String filename = directoryPath + File.separator + nric + "_attendance.txt";
            List<Integer> workingHoursList = new ArrayList<>();
            int lateCount = 0;
            int penalty = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts.length >= 4) {
                        String hoursPart = parts[3];
                        String[] hoursArray = hoursPart.split(": ");
                        if (hoursArray.length == 2) {
                            try {
                                LocalDateTime clockInTime = LocalDateTime.parse(parts[1],
                                        DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
                                if (clockInTime.getMonthValue() == selectedMonth) {
                                    int hours = Integer.parseInt(hoursArray[1]);
                                    workingHoursList.add(hours);

                                    if (parts[4].equals("Late")) {
                                        lateCount++;

                                        if (lateCount % 6 == 0) {
                                            penalty += 100;
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Calculate the total hours worked in the selected month
            long totalHoursWorked = workingHoursList.stream().mapToInt(Integer::intValue).sum();

            String reportFilename = directoryPath + File.separator + nric + "_monthly_report_"
                    + months[selectedMonth - 1] + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
                writer.write("Monthly Report for " + username + " - " + months[selectedMonth - 1] + "\n");
                writer.write("Working Hours: \n");
                for (int hours : workingHoursList) {
                    if (hours != 0) {
                        writer.write(hours + "\n");
                    }
                }
                writer.write("Total Hours Worked: " + totalHoursWorked + "\n");
                if (penalty > 0) {
                    writer.write("Penalty: RM" + penalty + "\n"); // Write the penalty amount
                }
                JOptionPane.showMessageDialog(null, "Monthly report generated!", "Report Status",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Desktop.getDesktop().open(new File(reportFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateAnnualReport() {
        String[] years = { "2023", "2024", "2025", "2026", "2027" };
        JComboBox<String> yearSelector = new JComboBox<>(years);

        yearSelector.setPreferredSize(new Dimension(500, 70));

        int option = JOptionPane.showConfirmDialog(null, yearSelector, "Select Year",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String selectedYear = (String) yearSelector.getSelectedItem();
            String directoryPath = "Employees/" + nric;
            String filename = directoryPath + File.separator + nric + "_attendance.txt";
            List<Integer> workingHoursList = new ArrayList<>();
            int lateCount = 0;
            int penalty = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts.length >= 4) {
                        String hoursPart = parts[3];
                        String[] hoursArray = hoursPart.split(": ");
                        if (hoursArray.length == 2) {
                            try {
                                LocalDateTime clockInTime = LocalDateTime.parse(parts[1],
                                        DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
                                if (Integer.toString(clockInTime.getYear()).equals(selectedYear)) {
                                    int hours = Integer.parseInt(hoursArray[1]);
                                    workingHoursList.add(hours);

                                    if (parts[4].equals("Late")) {
                                        lateCount++;

                                        if (lateCount % 6 == 0) {
                                            penalty += 100;
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            long totalHoursWorked = workingHoursList.stream().mapToInt(Integer::intValue).sum();
            String reportFilename = directoryPath + File.separator + nric + "_annual_report_" + selectedYear
                    + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
                writer.write("Annual Report for " + username + " - " + selectedYear + "\n");
                writer.write("Working Hours: \n");
                for (int hours : workingHoursList) {
                    if (hours != 0) {
                        writer.write(hours + "\n");
                    }
                }
                writer.write("Total Hours Worked: " + totalHoursWorked + "\n");
                if (penalty > 0) {
                    writer.write("Penalty: RM" + penalty + "\n");
                }
                JOptionPane.showMessageDialog(null, "Annual report generated!", "Report Status",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Desktop.getDesktop().open(new File(reportFilename));
            } catch (IOException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }

        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.err.println("Could not delete the original file.");
        }
    }

    private void supportTicket(String username) {
        String[] reasons = { "Forget to clock in/out", "System problem", "Others" };
        JComboBox<String> reasonDropdown = new JComboBox<>(reasons);
        JTextArea detailsTextArea = new JTextArea(10, 40);
        int result = JOptionPane.showConfirmDialog(null,
                new Object[] {
                        new JLabel("Select a reason:"),
                        reasonDropdown,
                        new JLabel("Details (Date/Time/Reason):"),
                        new JScrollPane(detailsTextArea)
                },
                "Submit Support Ticket", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String selectedReason = (String) reasonDropdown.getSelectedItem();
            String details = detailsTextArea.getText();

            if (!details.trim().isEmpty()) {
                saveTicketToFile(username, selectedReason, details);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter the details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveTicketToFile(String username, String option, String reason) {
        try (FileWriter writer = new FileWriter("Employees/Employee_ticket.txt", true)) {
            writer.write("Username: " + username + ", NRIC: " + nric + ", " + option + ", Reason: " + reason + "\n");
            JOptionPane.showMessageDialog(null, "Ticket submitted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
