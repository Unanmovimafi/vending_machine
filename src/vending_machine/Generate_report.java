/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vending_machine;

import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author lim
 */

public class Generate_report extends javax.swing.JDialog {
    
    public void refreshTable(Date startDate, Date endDate) {
        
        double totalSumPrice = 0;
        int totalQuantity = 0;
        int maxQuantity = 0;
        String productName = "";
        String maxProductName = "";
        
        Calculate cal = new Calculate();
        Map<Integer, String> productNames = new HashMap<>();
        Map<Integer, Integer> productQuantities = new HashMap<>();
        
        
        if ((startDate == null) && (endDate == null)){
            //If both date are null then print all the data in sales log
            try {
            DefaultTableModel model = (DefaultTableModel)tSalesList.getModel();
            model.setRowCount(0);
            BufferedReader br = new BufferedReader(new FileReader("log\\SalesLog.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                //Add row to the table
                model.addRow(record);
                int quantity = Integer.parseInt(record[5]);
                totalQuantity = totalQuantity +quantity;
                //Get the total price and the sum of it
                double totalPrice = cal.multiply(record[4], quantity);
                totalSumPrice = totalSumPrice + totalPrice;
                
                int productId = Integer.parseInt(record[2]);
                productName = record[3];
                productNames.put(productId, productName);
                productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
            
                int rowIndex = model.getRowCount() - 1;
                int columnIndex = model.getColumnCount() - 1;
                tSalesList.setValueAt(totalPrice, rowIndex, columnIndex);
            }
            br.close();
            
        }  catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        }
        
        else{
            //Select the record between the two date
            try {
                DefaultTableModel model = (DefaultTableModel)tSalesList.getModel();
                model.setRowCount(0);
                BufferedReader br = new BufferedReader(new FileReader("log\\SalesLog.txt"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String line;
                while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                   Date recordDate = dateFormat.parse(record[0]);
                   if (recordDate.compareTo(startDate) >= 0 && recordDate.compareTo(endDate) <= 0){
                       //If record between two dates then add row to the table
                        model.addRow(record);
                        int quantity = Integer.parseInt(record[5]);
                        double totalPrice = cal.multiply(record[4], quantity);
                        //Get the total price and the sum of it
                        totalSumPrice = totalSumPrice + totalPrice;
                        totalQuantity = totalQuantity +quantity;

                        int productId = Integer.parseInt(record[2]);
                        productName = record[3];
                        productNames.put(productId, productName);
                        productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
                        
                        int rowIndex = model.getRowCount() - 1;
                        int columnIndex = model.getColumnCount() - 1;
                        tSalesList.setValueAt(totalPrice, rowIndex, columnIndex);
                    }
                }
                br.close();

            }  catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please give valid date.");
            }
            
            
            
        }
        

            // Find product with maximum quantity
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                if (entry.getValue() > maxQuantity) {
                    maxQuantity = entry.getValue();
                    maxProductName = productNames.get(entry.getKey());
                    
                }
            }
            
            //Set all the details based on the result
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = currentDate.format(formatterDate);
            lCurrentDate.setText(formattedDate);
            
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatterTime);
            lCurrentTime.setText(formattedTime);
            
            lActualTotalSales.setText(Double.toString(totalSumPrice));
            lActualValueTotalProduct.setText(Integer.toString(totalQuantity));
            lActualBestSeller.setText(maxProductName + "(" + maxQuantity + ")");
    }
    
    public void setDate(){
        //Set the current date to the DateChooser
        LocalDate currentDate = LocalDate.now();
        currentDateAsDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dcStartDate.setDate(currentDateAsDate);
        dcEndDate.setDate(currentDateAsDate);
        
}

    /**
     * Creates new form Generate_report
     */
    public Generate_report(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDate();
        refreshTable(currentDateAsDate, currentDateAsDate);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spSalesList = new javax.swing.JScrollPane();
        tSalesList = new javax.swing.JTable();
        bPrintTable = new javax.swing.JButton();
        bSearch = new javax.swing.JButton();
        lGeneratedDate = new javax.swing.JLabel();
        lGeneratedTime = new javax.swing.JLabel();
        lTotalSales = new javax.swing.JLabel();
        lTotalProductSold = new javax.swing.JLabel();
        lCurrentDate = new javax.swing.JLabel();
        lCurrentTime = new javax.swing.JLabel();
        lActualTotalSales = new javax.swing.JLabel();
        lActualValueTotalProduct = new javax.swing.JLabel();
        lStartDate = new javax.swing.JLabel();
        lEndDate = new javax.swing.JLabel();
        lBestSeller = new javax.swing.JLabel();
        lActualBestSeller = new javax.swing.JLabel();
        lTitle = new javax.swing.JLabel();
        bAll = new javax.swing.JButton();
        dcStartDate = new com.toedter.calendar.JDateChooser();
        dcEndDate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Report");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tSalesList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Time", "Item ID", "Name", "Unit Price(MYR)", "Quantity", "Total Price(MYR)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spSalesList.setViewportView(tSalesList);

        getContentPane().add(spSalesList, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 128, 555, 530));

        bPrintTable.setText("Print Table");
        bPrintTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrintTableActionPerformed(evt);
            }
        });
        getContentPane().add(bPrintTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 93, -1, -1));

        bSearch.setText("Search");
        bSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSearchActionPerformed(evt);
            }
        });
        getContentPane().add(bSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 93, -1, -1));

        lGeneratedDate.setText("Generated Date:");
        getContentPane().add(lGeneratedDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(596, 163, -1, -1));

        lGeneratedTime.setText("Generated Time:");
        getContentPane().add(lGeneratedTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(596, 191, -1, -1));

        lTotalSales.setText("Total Sales:");
        getContentPane().add(lTotalSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 250, -1, -1));

        lTotalProductSold.setText("Total Product sold:");
        getContentPane().add(lTotalProductSold, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 221, -1, -1));
        getContentPane().add(lCurrentDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 161, -1, -1));
        getContentPane().add(lCurrentTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 191, -1, -1));

        lActualTotalSales.setText("a");
        getContentPane().add(lActualTotalSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 30, -1));
        getContentPane().add(lActualValueTotalProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 221, -1, -1));

        lStartDate.setText("Start Date:");
        getContentPane().add(lStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        lEndDate.setText("End Date:");
        getContentPane().add(lEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, -1));

        lBestSeller.setText("Beset Seller:");
        getContentPane().add(lBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, -1, -1));
        getContentPane().add(lActualBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 270, 30, -1));

        lTitle.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        lTitle.setText("Report");
        getContentPane().add(lTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        bAll.setText("All");
        bAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAllActionPerformed(evt);
            }
        });
        getContentPane().add(bAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 50, -1));

        dcStartDate.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(dcStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 130, -1));

        dcEndDate.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(dcEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 130, -1));

        setSize(new java.awt.Dimension(814, 707));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bPrintTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrintTableActionPerformed
        // TODO add your handling code here:
        //Print the table including pdf format
        MessageFormat header = new MessageFormat("Report");
        MessageFormat footer = new MessageFormat("Page{0, number, integer}of ");
        
        try{
            
            tSalesList.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        }
        catch (Exception e)
        {
        
        System.err.format(e.getMessage());
        }
        
    }//GEN-LAST:event_bPrintTableActionPerformed

    private void bSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSearchActionPerformed
        // TODO add your handling code here:
        refreshTable(dcStartDate.getDate(), dcEndDate.getDate());
    }//GEN-LAST:event_bSearchActionPerformed

    private void bAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAllActionPerformed
        // TODO add your handling code here:
        refreshTable(null,null);
    }//GEN-LAST:event_bAllActionPerformed

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
            java.util.logging.Logger.getLogger(Generate_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Generate_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Generate_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Generate_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Generate_report dialog = new Generate_report(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    
    
    private Date currentDateAsDate;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAll;
    private javax.swing.JButton bPrintTable;
    private javax.swing.JButton bSearch;
    private com.toedter.calendar.JDateChooser dcEndDate;
    private com.toedter.calendar.JDateChooser dcStartDate;
    private javax.swing.JLabel lActualBestSeller;
    private javax.swing.JLabel lActualTotalSales;
    private javax.swing.JLabel lActualValueTotalProduct;
    private javax.swing.JLabel lBestSeller;
    private javax.swing.JLabel lCurrentDate;
    private javax.swing.JLabel lCurrentTime;
    private javax.swing.JLabel lEndDate;
    private javax.swing.JLabel lGeneratedDate;
    private javax.swing.JLabel lGeneratedTime;
    private javax.swing.JLabel lStartDate;
    private javax.swing.JLabel lTitle;
    private javax.swing.JLabel lTotalProductSold;
    private javax.swing.JLabel lTotalSales;
    private javax.swing.JScrollPane spSalesList;
    private javax.swing.JTable tSalesList;
    // End of variables declaration//GEN-END:variables
}
