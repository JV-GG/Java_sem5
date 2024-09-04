import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ManageStatus extends javax.swing.JFrame {

    private LeaveRecord selectedLeaveRecord;
    private List<LeaveRecord> leaveRecords;
    /**
     * Creates new form ManageStatus
     */
    public ManageStatus(int recordIndex) {
        initComponents();
        leaveRecords = new ArrayList<>();
        readLeaveData();
        
        if (recordIndex >= 0 && recordIndex < leaveRecords.size()) {
            selectedLeaveRecord = leaveRecords.get(recordIndex);
            displayLeaveDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid record index", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
    private void displayLeaveDetails() {
        if (selectedLeaveRecord != null) {
            jLabel1.setText("Leave Type: " + selectedLeaveRecord.leaveType);
            jLabel2.setText("Start Date: " + selectedLeaveRecord.startDate);
            jLabel3.setText("End Date: " + selectedLeaveRecord.endDate);
            jLabel4.setText("Reason: " + selectedLeaveRecord.reason);
            jLabel5.setText("Status: " + selectedLeaveRecord.status);
        }
    }
    
    private class LeaveRecord {
        String nric;   
        String gender;
        String leaveType;
        String startDate;
        String endDate;
        String reason;
        String status;
        String totalLeaveDaysTaken;
    }
    
    private void readLeaveData() {
        String filePath = "LeaveManagement\\LeaveApplication.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            LeaveRecord currentRecord = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("NRIC :")) {
                    if (currentRecord != null) {
                        leaveRecords.add(currentRecord);
                    }
                    currentRecord = new LeaveRecord();
                    currentRecord.nric = line.substring("NRIC :".length()).trim();
                } else if (line.startsWith("Gender:")) {
                if (currentRecord != null) {
                    currentRecord.gender = line.substring("Gender:".length()).trim();
                }
                } else if (line.startsWith("Leave Type:")) {
                    if (currentRecord != null) {
                        currentRecord.leaveType = line.substring("Leave Type:".length()).trim();
                    }
                } else if (line.startsWith("Start Date:")) {
                    if (currentRecord != null) {
                        currentRecord.startDate = line.substring("Start Date:".length()).trim();
                    }
                } else if (line.startsWith("End Date:")) {
                    if (currentRecord != null) {
                        currentRecord.endDate = line.substring("End Date:".length()).trim();
                    }
                } else if (line.startsWith("Reason:")) {
                    if (currentRecord != null) {
                        currentRecord.reason = line.substring("Reason:".length()).trim();
                    }
                } else if (line.startsWith("Status:")) {
                    if (currentRecord != null) {
                        currentRecord.status = line.substring("Status:".length()).trim();
                    }
                } else if (line.startsWith("Total Leave Days Taken:")) {
                    if (currentRecord != null) {
                        currentRecord.totalLeaveDaysTaken = line.substring("Total Leave Days Taken:".length()).trim();
                    }
                }
            }

            // Add the last record if available
            if (currentRecord != null) {
                leaveRecords.add(currentRecord);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void writeLeaveData() {
        String filePath = "LeaveManagement\\LeaveApplication.txt";
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (LeaveRecord record : leaveRecords) {
                bw.write("NRIC :" + record.nric + "\n");
                bw.write("Leave Type:" + record.leaveType + "\n");
                bw.write("Start Date:" + record.startDate + "\n");
                bw.write("End Date:" + record.endDate + "\n");
                bw.write("Reason:" + record.reason + "\n");
                bw.write("Status:" + record.status + "\n");
                bw.write("Total Leave Days Taken:" + record.totalLeaveDaysTaken + "\n");
                bw.write("\n"); // Add a blank line between records
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing the file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void approveLeave() {
        if (selectedLeaveRecord != null) {
            selectedLeaveRecord.status = "Approved";
            writeLeaveData();
            JOptionPane.showMessageDialog(this, "Leave record approved.", "Approved", JOptionPane.INFORMATION_MESSAGE);
            
            ManageViewLeave viewleave = new ManageViewLeave();
            viewleave.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No leave record selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rejectLeave() {
        if (selectedLeaveRecord != null) {
            selectedLeaveRecord.status = "Rejected";
            writeLeaveData();
            JOptionPane.showMessageDialog(this, "Leave record rejected.", "Rejected", JOptionPane.INFORMATION_MESSAGE);
            
            ManageViewLeave viewleave = new ManageViewLeave();
            viewleave.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No leave record selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Backbtn = new javax.swing.JButton();
        approvebtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manager Check Leave", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setText("Leave Type:");

        jLabel2.setText("Start Date:");

        jLabel3.setText("End Date:");

        jLabel4.setText("Reason:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Status:");

        Backbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Backbtn.setText("Back");
        Backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackbtnActionPerformed(evt);
            }
        });

        approvebtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        approvebtn.setText("Approve");
        approvebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvebtnActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("Reject");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(approvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(173, 173, 173))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(approvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void BackbtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        ManageViewLeave viewleave = new ManageViewLeave();
        viewleave.setVisible(true);

        this.dispose();
    }                                       

    private void approvebtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        approveLeave();
    }                                          

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        rejectLeave();
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageStatus(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton Backbtn;
    private javax.swing.JButton approvebtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
}
