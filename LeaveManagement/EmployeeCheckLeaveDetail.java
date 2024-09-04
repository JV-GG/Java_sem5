/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package LeaveManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author keewe
 */
public class EmployeeCheckLeaveDetail extends javax.swing.JFrame {
    
    private LeaveRecord leaveRecord;
    private List<LeaveRecord> leaveRecords;
    /**
     * Creates new form EmployeeCheckLeaveDetail
     */
    public EmployeeCheckLeaveDetail(int recordIndex) {
        initComponents();
        leaveRecords = new ArrayList<>();
        readLeaveData(); // Read data from file
        if (recordIndex >= 0 && recordIndex < leaveRecords.size()) {
            leaveRecord = leaveRecords.get(recordIndex); // Display the record at the given index
            displayLeaveDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid record index", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
    private void displayLeaveDetails() {
        if (leaveRecord != null) {
            jLabel1.setText("Leave Type: " + leaveRecord.leaveType);
            jLabel2.setText("Start Date: " + leaveRecord.startDate);
            jLabel3.setText("End Date: " + leaveRecord.endDate);
            jLabel4.setText("Reason: " + leaveRecord.reason);
            jLabel5.setText("Status: " + leaveRecord.status); 
        }
    }
    
    private class LeaveRecord {
        String nric;
        String leaveType;
        String startDate;
        String endDate;
        String reason;
        String status;
    }
    
    private void readLeaveData() {
        String filePath = "C:\\Users\\keewe\\OneDrive\\Documents\\NetBeansProjects\\JavaProject\\src\\LeaveManagement\\LeaveApplication.txt";

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
        String filePath = "C:\\Users\\keewe\\OneDrive\\Documents\\NetBeansProjects\\JavaProject\\src\\LeaveManagement\\LeaveApplication.txt";
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (LeaveRecord record : leaveRecords) {
                bw.write("NRIC :" + record.nric + "\n");
                bw.write("Leave Type:" + record.leaveType + "\n");
                bw.write("Start Date:" + record.startDate + "\n");
                bw.write("End Date:" + record.endDate + "\n");
                bw.write("Reason:" + record.reason + "\n");
                bw.write("Status:" + record.status + "\n");
                bw.write("\n"); // Add a blank line between records
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing the file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteLeaveRecord(int recordIndex) {
        if (recordIndex >= 0 && recordIndex < leaveRecords.size()) {
            leaveRecords.remove(recordIndex);
            writeLeaveData(); // Update the file with the new records
        } else {
            JOptionPane.showMessageDialog(this, "Invalid record index", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Backbtn = new javax.swing.JButton();
        Editbtn = new javax.swing.JButton();
        Cancelbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Check Leave Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

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

        Editbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Editbtn.setText("Edit");
        Editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditbtnActionPerformed(evt);
            }
        });

        Cancelbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Cancelbtn.setText("Cancel");
        Cancelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(173, 173, 173))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addGap(55, 55, 55)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackbtnActionPerformed
        EmployeeCheckLeave employeecheckleave = new EmployeeCheckLeave();
        employeecheckleave.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_BackbtnActionPerformed

    private void EditbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditbtnActionPerformed
        if (leaveRecord != null) {
            System.out.println("Editing leave record: " + leaveRecord.nric);
            try {
                EmployeeEditLeave employeeeditleave = new EmployeeEditLeave(leaveRecord);
                employeeeditleave.setVisible(true);
                this.dispose(); // Dispose of the current form if you want to close it
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening edit form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No leave record selected for editing.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EditbtnActionPerformed

    private void CancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelbtnActionPerformed
        int recordIndex = leaveRecords.indexOf(leaveRecord);
        deleteLeaveRecord(recordIndex);

        // Optionally, show a confirmation message
        JOptionPane.showMessageDialog(this, "Record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        LeaveMenu leavemenu = new LeaveMenu();
        leavemenu.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_CancelbtnActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeCheckLeaveDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeCheckLeaveDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeCheckLeaveDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeCheckLeaveDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeCheckLeaveDetail(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Backbtn;
    private javax.swing.JButton Cancelbtn;
    private javax.swing.JButton Editbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
