package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterWorker extends JFrame implements ActionListener {
    JTextField tfName, tfAadhar, tfPhone, tfAddress, tfBank, tfWorkType;
    JButton registerBtn;

    RegisterWorker() {
        setTitle("Worker Registration");
        setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(40, 20, 100, 30);
        add(lblName);
        
        tfName = new JTextField();
        tfName.setBounds(150, 20, 200, 30);
        add(tfName);

        JLabel lblAadhar = new JLabel("Aadhar Number:");
        lblAadhar.setBounds(40, 70, 100, 30);
        add(lblAadhar);
        
        tfAadhar = new JTextField();
        tfAadhar.setBounds(150, 70, 200, 30);
        add(tfAadhar);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(40, 120, 100, 30);
        add(lblPhone);
        
        tfPhone = new JTextField();
        tfPhone.setBounds(150, 120, 200, 30);
        add(tfPhone);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(40, 170, 100, 30);
        add(lblAddress);
        
        tfAddress = new JTextField();
        tfAddress.setBounds(150, 170, 200, 30);
        add(tfAddress);

        JLabel lblBank = new JLabel("Bank Account:");
        lblBank.setBounds(40, 220, 100, 30);
        add(lblBank);
        
        tfBank = new JTextField();
        tfBank.setBounds(150, 220, 200, 30);
        add(tfBank);

        JLabel lblWorkType = new JLabel("Work Type:");
        lblWorkType.setBounds(40, 270, 100, 30);
        add(lblWorkType);
        
        tfWorkType = new JTextField();
        tfWorkType.setBounds(150, 270, 200, 30);
        add(tfWorkType);

        registerBtn = new JButton("Register Worker");
        registerBtn.setBounds(100, 320, 200, 40);
        registerBtn.addActionListener(this);
        add(registerBtn);

        setSize(400, 400);
        setLocation(450, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO workers (name, aadhar_number, phone, address, bank_account, work_type) VALUES (?, ?, ?, ?, ?, ?)"
            );

            stmt.setString(1, tfName.getText());
            stmt.setString(2, tfAadhar.getText());
            stmt.setString(3, tfPhone.getText());
            stmt.setString(4, tfAddress.getText());
            stmt.setString(5, tfBank.getText());
            stmt.setString(6, tfWorkType.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Worker Registered Successfully!");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RegisterWorker();
    }
}