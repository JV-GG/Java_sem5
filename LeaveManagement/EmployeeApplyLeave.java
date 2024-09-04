/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package LeaveManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author keewe
 */
public class EmployeeApplyLeave extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeApplyLeave
     */
    public EmployeeApplyLeave() {
        initComponents();
        updateLeaveTypes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Genderbtngroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Datetxt = new javax.swing.JLabel();
        LeaveTypeCB = new javax.swing.JComboBox<>();
        LeaveTypetxt = new javax.swing.JLabel();
        StartDateChoose = new com.toedter.calendar.JDateChooser();
        Starttxt = new javax.swing.JLabel();
        Untiltxt = new javax.swing.JLabel();
        EndDateChoose = new com.toedter.calendar.JDateChooser();
        Reasontxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Reasontxtarea = new javax.swing.JTextArea();
        Backbtn = new javax.swing.JButton();
        Savebtn = new javax.swing.JButton();
        NRICtxt = new javax.swing.JLabel();
        NRICtxtfield = new javax.swing.JTextField();
        NRICtxt1 = new javax.swing.JLabel();
        Malebtn = new javax.swing.JRadioButton();
        Femalebtn = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Apply Leave", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        Datetxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Datetxt.setText("Select Date:");

        LeaveTypeCB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LeaveTypeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Annual Leave", "Medical Leave", "Unpaid Leave", "Maternity Leave" }));
        LeaveTypeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveTypeCBActionPerformed(evt);
            }
        });

        LeaveTypetxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LeaveTypetxt.setText("Select type of leave:");

        Starttxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Starttxt.setText("From-");

        Untiltxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Untiltxt.setText("Until-");

        Reasontxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Reasontxt.setText("Reason:");

        Reasontxtarea.setColumns(20);
        Reasontxtarea.setRows(5);
        jScrollPane1.setViewportView(Reasontxtarea);

        Backbtn.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Backbtn.setText("Back");
        Backbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackbtnActionPerformed(evt);
            }
        });

        Savebtn.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Savebtn.setText("Save");
        Savebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavebtnActionPerformed(evt);
            }
        });

        NRICtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NRICtxt.setText("NRIC:");

        NRICtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NRICtxtfieldActionPerformed(evt);
            }
        });

        NRICtxt1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NRICtxt1.setText("Gender:");

        Genderbtngroup.add(Malebtn);
        Malebtn.setText("Male");
        Malebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MalebtnActionPerformed(evt);
            }
        });

        Genderbtngroup.add(Femalebtn);
        Femalebtn.setText("Female");
        Femalebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FemalebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Starttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StartDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(Untiltxt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EndDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Reasontxt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LeaveTypetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(NRICtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(NRICtxtfield))
                                        .addComponent(LeaveTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(NRICtxt1)
                                        .addGap(18, 18, 18)
                                        .addComponent(Malebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Femalebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(45, 45, 45))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NRICtxt)
                    .addComponent(NRICtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NRICtxt1)
                    .addComponent(Malebtn)
                    .addComponent(Femalebtn))
                .addGap(18, 18, 18)
                .addComponent(LeaveTypetxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LeaveTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Datetxt)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Starttxt)
                    .addComponent(StartDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Untiltxt)
                    .addComponent(EndDateChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(Reasontxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Backbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LeaveTypeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveTypeCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeaveTypeCBActionPerformed

    private void BackbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackbtnActionPerformed
        LeaveMenu leavemenu = new LeaveMenu();
        leavemenu.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_BackbtnActionPerformed

    private void updateLeaveTypes() {
        if (Femalebtn.isSelected()) {
            LeaveTypeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Annual Leave", "Medical Leave", "Unpaid Leave", "Maternity Leave"
            }));
        } else {
            LeaveTypeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Annual Leave", "Medical Leave", "Unpaid Leave"
            }));
        }
    }

    
    private void SavebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavebtnActionPerformed
        String nric = NRICtxtfield.getText();
        String gender = Malebtn.isSelected() ? "Male" : (Femalebtn.isSelected() ? "Female" : "Not Specified");
        String leaveType = (String) LeaveTypeCB.getSelectedItem();
        Date startDate = StartDateChoose.getDate();
        Date endDate = EndDateChoose.getDate();
        String reason = Reasontxtarea.getText();
        String status = "Pending";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = (startDate != null) ? dateFormat.format(startDate) : "N/A";
        String formattedEndDate = (endDate != null) ? dateFormat.format(endDate) : "N/A";
        
        long durationInMillisecond = endDate.getTime() - startDate.getTime();
        long durationInDays = (durationInMillisecond / (1000 * 60 * 60 * 24)) + 1;
        
        String data = String.format("NRIC : %s%nGender: %s%nLeave Type: %s%nStart Date: %s%nEnd Date: %s%nReason: %s%nStatus: %s%n", nric, gender, leaveType, formattedStartDate, formattedEndDate, reason, status);
        
        String filePath = "C:\\Users\\keewe\\OneDrive\\Documents\\NetBeansProjects\\JavaProject\\src\\LeaveManagement\\LeaveApplication.txt";
    
    // Write data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.write(String.format("Total Leave Days Taken: %d%n", durationInDays));
            writer.newLine(); // Add a newline for separation
            JOptionPane.showMessageDialog(this, "You successfully apllied for the leave!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred when applying for the leave, please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        LeaveMenu leavemenu = new LeaveMenu();
        leavemenu.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_SavebtnActionPerformed

    private void NRICtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NRICtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NRICtxtfieldActionPerformed

    private void MalebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MalebtnActionPerformed
        updateLeaveTypes();
    }//GEN-LAST:event_MalebtnActionPerformed

    private void FemalebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FemalebtnActionPerformed
        updateLeaveTypes();
    }//GEN-LAST:event_FemalebtnActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeApplyLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeApplyLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeApplyLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeApplyLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeApplyLeave().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Backbtn;
    private javax.swing.JLabel Datetxt;
    private com.toedter.calendar.JDateChooser EndDateChoose;
    private javax.swing.JRadioButton Femalebtn;
    private javax.swing.ButtonGroup Genderbtngroup;
    private javax.swing.JComboBox<String> LeaveTypeCB;
    private javax.swing.JLabel LeaveTypetxt;
    private javax.swing.JRadioButton Malebtn;
    private javax.swing.JLabel NRICtxt;
    private javax.swing.JLabel NRICtxt1;
    private javax.swing.JTextField NRICtxtfield;
    private javax.swing.JLabel Reasontxt;
    private javax.swing.JTextArea Reasontxtarea;
    private javax.swing.JButton Savebtn;
    private com.toedter.calendar.JDateChooser StartDateChoose;
    private javax.swing.JLabel Starttxt;
    private javax.swing.JLabel Untiltxt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
