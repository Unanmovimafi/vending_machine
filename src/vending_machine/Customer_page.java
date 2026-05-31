/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vending_machine;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author lim
 */
public class Customer_page extends javax.swing.JFrame {
    
    
    public void setCartRecord(String[][] record){
    this.cartRecords = record;
    showCart(cartRecords);
    loadProductsFromFile("All");
    
    }
    
    class DateTimeUpdater {
        public void updateDateTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            String datetime = sdf.format(new Date());
            lDateTime.setText(datetime);
        }
    }
    
    class TimerWithDatetimeUpdater extends DateTimeUpdater {
        public void updateDateTime() {
            //Every one second update the text of label lDateTime
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String datetime = sdf.format(new Date());
                    lDateTime.setText(datetime);
                }
            });
            timer.start();
        }
    }
    
    
    private void loadProductsFromFile(String category) {
        //First to removel all panel in pShowProducts
        //Remove all components in pShowProducts
        pShowProducts.removeAll();
        
        if (category.equals("All")){
            try {BufferedReader br = new BufferedReader(new FileReader("product\\product.txt")); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                if (Integer.parseInt(record[4]) > 0 && record[6].equals("Active")) {
                    //Check if available stock and status Active then createProductPanel
                    createProductPanel(record);}
                }
            } catch (Exception e) {
            e.getMessage();
            }
        }
        else {
            try {BufferedReader br = new BufferedReader(new FileReader("product\\product.txt")); 
                String line;
                while ((line = br.readLine()) != null) {
                    String[] record = line.split(",");
                    if (record[2].equals(category) && record[6].equals("Active") && Integer.parseInt(record[4]) > 0){
                         //Check if available stock and status Active and category match then createProductPanel
                        createProductPanel(record);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        
        //Refresh the Panel
        pShowProducts.revalidate();
        pShowProducts.repaint();  
    }
    
    private void createProductPanel(String[] record) {
        
        //Create panel, label and buttons
        javax.swing.JLabel imageLabel = new javax.swing.JLabel();
        javax.swing.JPanel productPanel = new javax.swing.JPanel();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
        javax.swing.JLabel priceLabel = new javax.swing.JLabel();
        javax.swing.JButton addToCartButton = new javax.swing.JButton();
        
        //Set the properties of these components
        productPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        String image = record[5];
        String imageLocation = "product\\image\\" +image;
        ImageIcon imageIcon = new ImageIcon(imageLocation);
        Image setIcon = imageIcon.getImage().getScaledInstance(90,100, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(setIcon);

        imageLabel.setIcon(img);
        productPanel.add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        nameLabel.setText(record[1]); // Assuming record[1] is the product name
        productPanel.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        priceLabel.setText("MYR" +record[3]); // Assuming record[2] is the product price
        productPanel.add(priceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        addToCartButton.setText("Add to cart");
        productPanel.add(addToCartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));
        
        //Give action to user when user click the button
        addToCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToCartActionPerformed(evt, record);
            }
        }
        );
    //Refresh and show the panel    
    pShowProducts.add(productPanel);
    spShowProducts.setViewportView(pShowProducts);
}

    private void showCart(String[][] record) {
        double totalSum = 0;
        Calculate cal = new Calculate();
        int totalQuantity= 0;
        
        if (record.length == 0){
            //Remove the components in panel
            pCart.removeAll();
            spCart.setViewportView(pCart);
        }
        else {
            //First to remove all component in jPanel2
            pCart.removeAll();

            for (int i = 0; i < record.length; i++) {
                String valueAsString = record[i][3];
                totalQuantity = totalQuantity + Integer.parseInt(record[i][3]);
                double totalPrice = cal.multiply(valueAsString, record[i][2]);
                totalSum += totalPrice;

                //Create and set the properterties of the composition
                javax.swing.JPanel productPanel = new javax.swing.JPanel();

                javax.swing.JLabel nameLabel = new javax.swing.JLabel();
                javax.swing.JLabel quantity = new javax.swing.JLabel();
                javax.swing.JLabel price = new javax.swing.JLabel();

                javax.swing.JButton cancel = new javax.swing.JButton();
                javax.swing.JButton plus = new javax.swing.JButton();
                javax.swing.JButton minus = new javax.swing.JButton();

                productPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                nameLabel.setText(record[i][1]);
                quantity.setText(record[i][3]);
                price.setText("MYR" +Double.toString(totalPrice));

                cancel.setText("x");
                plus.setText("+");
                minus.setText("-");
                
                //Add the composition to the panel
                productPanel.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 25, -1, -1));
                productPanel.add(cancel,new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, -1) );
                productPanel.add(plus,new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 35, 35) );
                productPanel.add(minus,new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 35, 35) );
                productPanel.add(quantity,new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 25, -1, -1) );
                productPanel.add(price,new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1) );

                int index = i;
                cancel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        cancelActionPerformed(evt, record, index);
                    }
                });

                plus.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        plusActionPerformed(evt, record, index);
                    }
                });

                minus.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                        minusActionPerformed(evt, record, index);
                    }
                });

                //Add the component to the Panel and set view
                pCart.add(productPanel);
                spCart.setViewportView(pCart); 
            }
        }
        //Set the Total Quantity and total sum price
        lCartItemQuantity.setText("("+ totalQuantity +"/5)");
        lSumTotalPrice.setText("MYR "+Double.toString(totalSum));
    }
    
    private void addToCartActionPerformed(java.awt.event.ActionEvent evt, String[] record) {                                         
        // TODO add your handling code here:
        if (cartRecords == null) {
            // Initialize the double array with one row and the length of the record array
            cartRecords = new String[1][4];
            String[] cartPrd = {record[0], record[1],record[3], "1"};
            // Copy the values from the record array to the first row of cartRecords
            System.arraycopy(cartPrd, 0, cartRecords[0], 0, cartPrd.length);
            showCart(cartRecords);
        }  else {
            boolean flag = false;
            boolean anoFlag = false;
            boolean flag2 = false;
            int totalQuantity =  0;
            for (int i = 0; i < cartRecords.length; i++) {
                String curQua = cartRecords[i][3];
                int currentQuan = Integer.parseInt(curQua);
                int stock = Integer.parseInt(record[4]);
                
                totalQuantity = currentQuan + totalQuantity;
                if ((totalQuantity + 1) > 5){
                    //Check if total product exceed 5
                    anoFlag = true;
                }
                if (cartRecords[i][0].equals(record[0])) {
                    //Check if repeat product
                    flag = true;
                }
                if (currentQuan + 1 > stock){
                    //Cannot if the stock is available for add to cart
                    flag2 = true;
                }
            }
            
            if (anoFlag){
                //Cannot add to cart because of reach maximum
               JOptionPane.showMessageDialog(null, "Maximum of product is 5");
            }
            else if (flag2){
                //The stock of the product is not enough
                JOptionPane.showMessageDialog(null, "The available stock for this item is " + record[4]);
            }
            else if (flag) {
                for (int i = 0; i < cartRecords.length; i++) {
                    if (cartRecords[i][0].equals(record[0])) {
                        String currentQuantityStr = cartRecords[i][3];
                        int currentQuantity = Integer.parseInt(currentQuantityStr);
                        int newQuantity = currentQuantity + 1;
                        cartRecords[i][3] = Integer.toString(newQuantity);   
                   }
               }
            }
            else {
                // Create a new double array with an increased number of rows
                String[][] newCartRecords = new String[cartRecords.length + 1][];

                // Copy existing rows to the new double array
                for (int i = 0; i < cartRecords.length; i++) {
                    newCartRecords[i] = cartRecords[i];
                }
            
                String[] cartPrd = {record[0], record[1], record[3],"1"};
                // Copy the new record array to the last row of the new double array
                newCartRecords[cartRecords.length] = new String[cartPrd.length];
                System.arraycopy(cartPrd, 0, newCartRecords[cartRecords.length], 0, cartPrd.length);

                // Update the cartRecords reference to point to the new double array
                   cartRecords = newCartRecords;
                    
           }
        }
        showCart(cartRecords);
    }
    
    
    
           
    
    
    private void cancelActionPerformed(java.awt.event.ActionEvent evt, String[][] record, int index) {
            
        int newSize = record.length - 1;
        
        //Eliminate the specify array in cartRecords
        String[][] newRecord = new String[newSize][];
        System.arraycopy(record, 0, newRecord, 0, index);
        System.arraycopy(record, index + 1, newRecord, index, newSize - index);
        cartRecords = newRecord;
        showCart(cartRecords);
    }
            
    private void plusActionPerformed(java.awt.event.ActionEvent evt, String[][] record, int index){
        
        int totalQuantity =  0;
        String stock = "";
        int intStock = 0;
        int currentQuan = 0;
        int currentPro = Integer.parseInt(cartRecords[index][3]);

        for (int i = 0; i < cartRecords.length; i++) {
            String curQua = cartRecords[i][3];
            currentQuan = Integer.parseInt(curQua);
            totalQuantity = currentQuan + totalQuantity;
        }

        try {BufferedReader br = new BufferedReader(new FileReader("product\\product.txt")); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] prdRecord = line.split(",");
                if (record[index][0].equals(prdRecord[0])){
                    //Get the stock of the product
                    stock = prdRecord[4];
                    intStock = Integer.parseInt(stock);
                }
            }
            br.close();
        }
            catch (Exception e) {
             e.getMessage();
        }

        if ((totalQuantity + 1) > 5){
            //Exceed the total product limit of cart
            JOptionPane.showMessageDialog(null, "Maximum of product is 5");}
        else if (currentPro + 1 > intStock){
            //No enough stock of the product
            JOptionPane.showMessageDialog(null, "The available stock for this item is " + stock);
        }

        else {
            //Plus one to the quantity in cartRecord
            int currentQuantity = Integer.parseInt(record[index][3]);
            int newQuantity =currentQuantity + 1;
            cartRecords[index][3] = Integer.toString(newQuantity);
            showCart(cartRecords);
        }
    }        
    
    private void minusActionPerformed(java.awt.event.ActionEvent evt, String[][] record, int index)
    {
        int currentQuantity = Integer.parseInt(record[index][3]);
        int newQuantity =currentQuantity - 1;
        
        if (newQuantity <= 0){
            //Ask is user want to remove the item from cart
            int option=JOptionPane.showConfirmDialog(null,"Do you want to remove this item from your cart?",
            "Remove Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if(option==JOptionPane.YES_OPTION){  
            int newSize = record.length - 1;
            String[][] newRecord = new String[newSize][];
            //Remove the item from cartRecords
            System.arraycopy(record, 0, newRecord, 0, index);
            System.arraycopy(record, index + 1, newRecord, index, newSize - index);
            cartRecords = newRecord;
            showCart(cartRecords);}
        } else {
            //Add one more quantity to the item in cart
            cartRecords[index][3] = Integer.toString(newQuantity);
            showCart(cartRecords);
        }
    }
    
    
    /**
     * Creates new form Customer_page
     */
    
    public Customer_page() {
        initComponents();
        loadProductsFromFile("All");
        TimerWithDatetimeUpdater timerWithDatetimeUpdater = new TimerWithDatetimeUpdater();
        timerWithDatetimeUpdater.updateDateTime();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spShowProducts = new javax.swing.JScrollPane();
        pShowProducts = new javax.swing.JPanel();
        spCart = new javax.swing.JScrollPane();
        pCart = new javax.swing.JPanel();
        bPay = new javax.swing.JButton();
        lDateTime = new javax.swing.JLabel();
        bClearCart = new javax.swing.JButton();
        lSumTotal = new javax.swing.JLabel();
        lSumTotalPrice = new javax.swing.JLabel();
        bCategoryFood = new javax.swing.JButton();
        bCategoryDrink = new javax.swing.JButton();
        bCategoryAll = new javax.swing.JButton();
        bAdminAccess = new javax.swing.JButton();
        lCartItemQuantity = new javax.swing.JLabel();
        lMyCart = new javax.swing.JLabel();
        lTitle = new javax.swing.JLabel();
        lTitleBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vending Machine");
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(800, 900));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pShowProducts.setLayout(new java.awt.GridLayout(0, 3));
        spShowProducts.setViewportView(pShowProducts);

        getContentPane().add(spShowProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 178, 573, 610));

        pCart.setLayout(new javax.swing.BoxLayout(pCart, javax.swing.BoxLayout.PAGE_AXIS));
        spCart.setViewportView(pCart);

        getContentPane().add(spCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 180, 200, 267));

        bPay.setBackground(new java.awt.Color(102, 255, 102));
        bPay.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        bPay.setForeground(new java.awt.Color(255, 255, 255));
        bPay.setText("Pay");
        bPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPayActionPerformed(evt);
            }
        });
        getContentPane().add(bPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 490, -1, -1));

        lDateTime.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lDateTime.setText("Datetime");
        getContentPane().add(lDateTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        bClearCart.setBackground(new java.awt.Color(255, 0, 0));
        bClearCart.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        bClearCart.setForeground(new java.awt.Color(255, 255, 255));
        bClearCart.setText("Clear Cart");
        bClearCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearCartActionPerformed(evt);
            }
        });
        getContentPane().add(bClearCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 490, -1, -1));

        lSumTotal.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        lSumTotal.setText("Sum Total:");
        getContentPane().add(lSumTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 460, -1, -1));

        lSumTotalPrice.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lSumTotalPrice.setText("MYR 0.0");
        getContentPane().add(lSumTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 460, 81, 28));

        bCategoryFood.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        bCategoryFood.setText("Food");
        bCategoryFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCategoryFoodActionPerformed(evt);
            }
        });
        getContentPane().add(bCategoryFood, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, -1, -1));

        bCategoryDrink.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        bCategoryDrink.setText("Drink");
        bCategoryDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCategoryDrinkActionPerformed(evt);
            }
        });
        getContentPane().add(bCategoryDrink, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, -1, -1));

        bCategoryAll.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        bCategoryAll.setText("All");
        bCategoryAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCategoryAllActionPerformed(evt);
            }
        });
        getContentPane().add(bCategoryAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, -1, -1));

        bAdminAccess.setText("Admin Access");
        bAdminAccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdminAccessActionPerformed(evt);
            }
        });
        getContentPane().add(bAdminAccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, -1, -1));

        lCartItemQuantity.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lCartItemQuantity.setText("(0/5)");
        getContentPane().add(lCartItemQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, -1, -1));

        lMyCart.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        lMyCart.setText("My Cart");
        getContentPane().add(lMyCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, -1, -1));

        lTitle.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        lTitle.setForeground(new java.awt.Color(144, 12, 63));
        lTitle.setText("Anywhere Vending Machine");
        getContentPane().add(lTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        lTitleBackground.setBackground(new java.awt.Color(255, 255, 153));
        lTitleBackground.setOpaque(true);
        getContentPane().add(lTitleBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 780, 110));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPayActionPerformed
        // TODO add your handling code here:
        if (cartRecords == null || cartRecords.length == 0){
            //Check if user has add item to cart
            JOptionPane.showMessageDialog(null, "Please select at least a product to buy.");
        }
        else{
        //Direct to purchase page
        
        Customer_purchase cp = new Customer_purchase(this, true);
    
        cp.pack();
        cp.setLocationRelativeTo(null);
        cp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cp.setAdminPageInstance(this);
        cp.setCartRecords(cartRecords);
        cp.setVisible(true);
        }
    }//GEN-LAST:event_bPayActionPerformed

    private void bClearCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearCartActionPerformed
        // TODO add your handling code here:
        //Clear the variable of cartRecords and show the cart
        cartRecords = new String[0][0];
        showCart(cartRecords);
        
        
    }//GEN-LAST:event_bClearCartActionPerformed

    private void bCategoryFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCategoryFoodActionPerformed
        // TODO add your handling code here:
        loadProductsFromFile("Food");
    }//GEN-LAST:event_bCategoryFoodActionPerformed

    private void bCategoryDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCategoryDrinkActionPerformed
        // TODO add your handling code here:
        loadProductsFromFile("Drink");
    }//GEN-LAST:event_bCategoryDrinkActionPerformed

    private void bCategoryAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCategoryAllActionPerformed
        // TODO add your handling code here:
        loadProductsFromFile("All");
    }//GEN-LAST:event_bCategoryAllActionPerformed

    private void bAdminAccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdminAccessActionPerformed
        // TODO add your handling code here:
        Admin_login al = new Admin_login(this,true);
        
        al.setVisible(true);
        
    }//GEN-LAST:event_bAdminAccessActionPerformed
    
    
    
    
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
            java.util.logging.Logger.getLogger(Customer_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer_page().setVisible(true);
            }
        });
    }

    private String[][] cartRecords;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdminAccess;
    private javax.swing.JButton bCategoryAll;
    private javax.swing.JButton bCategoryDrink;
    private javax.swing.JButton bCategoryFood;
    private javax.swing.JButton bClearCart;
    private javax.swing.JButton bPay;
    private javax.swing.JLabel lCartItemQuantity;
    private javax.swing.JLabel lDateTime;
    private javax.swing.JLabel lMyCart;
    private javax.swing.JLabel lSumTotal;
    private javax.swing.JLabel lSumTotalPrice;
    private javax.swing.JLabel lTitle;
    private javax.swing.JLabel lTitleBackground;
    private javax.swing.JPanel pCart;
    private javax.swing.JPanel pShowProducts;
    private javax.swing.JScrollPane spCart;
    private javax.swing.JScrollPane spShowProducts;
    // End of variables declaration//GEN-END:variables
}
