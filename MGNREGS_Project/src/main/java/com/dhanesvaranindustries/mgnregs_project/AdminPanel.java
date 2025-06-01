package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {
    JTextField tfWorkerId;
    JButton verifyBtn, flagBtn;

    AdminPanel() {
        setTitle("Admin Verification Panel");
        setLayout(null);

        JLabel lblWorkerId = new JLabel("Enter Worker ID:");
        lblWorkerId.setBounds(40, 20, 150, 30);
        add(lblWorkerId);

        tfWorkerId = new JTextField();
        tfWorkerId.setBounds(200, 20, 150, 30);
        add(tfWorkerId);

        verifyBtn = new JButton("Verify Attendance");
        verifyBtn.setBounds(50, 80, 180, 40);
        verifyBtn.addActionListener(this);
        add(verifyBtn);

        flagBtn = new JButton("Flag Fraud");
        flagBtn.setBounds(250, 80, 180, 40);
        flagBtn.addActionListener(this);
        add(flagBtn);

        setSize(500, 200);
        setLocation(450, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Connection conn = DBConnection.getConnection();
            int workerId = Integer.parseInt(tfWorkerId.getText());

            if (ae.getSource() == verifyBtn) {
                PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE attendance SET verified = 'Yes' WHERE worker_id = ?"
                );
                stmt.setInt(1, workerId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Attendance Verified!");
            } else {
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO fraud_detection (worker_id, flagged_date) VALUES (?, NOW())"
                );
                stmt.setInt(1, workerId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "🚨 Worker Attendance Flagged!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}