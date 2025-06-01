package com.dhanesvaranindustries.mgnregs_project;

import javax.swing.*;
import java.sql.*;

public class PaymentProcessing {
    public static void processPayment() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "❌ Database connection failed!");
                return;
            }

            // Get Worker ID or Aadhar and Wage Amount from the user
            String identifier = JOptionPane.showInputDialog("Enter Worker ID or Aadhar Number:");
            String wageStr = JOptionPane.showInputDialog("Enter Daily Wage Amount:");

            // Convert inputs to appropriate data types
            double dailyWage = Double.parseDouble(wageStr);

            // Check if worker has verified attendance records
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT COUNT(attendance_id) FROM attendance WHERE worker_id = (SELECT worker_id FROM workers WHERE worker_id = ? OR aadhar_number = ?) AND verified = 'Yes'"
            );
            checkStmt.setString(1, identifier);
            checkStmt.setString(2, identifier);
            ResultSet rs = checkStmt.executeQuery();

            int verifiedDays = 0;
            if (rs.next()) {
                verifiedDays = rs.getInt(1);
            }

            if (verifiedDays > 0) {
                // Insert payment record
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO payments (worker_id, aadhar_number, verified_work_days, amount, payment_date, payment_status) " +
                    "VALUES ((SELECT worker_id FROM workers WHERE worker_id = ? OR aadhar_number = ?), " +
                    "(SELECT aadhar_number FROM workers WHERE worker_id = ? OR aadhar_number = ?), ?, ?, NOW(), 'Pending')"
                );

                stmt.setString(1, identifier);
                stmt.setString(2, identifier);
                stmt.setString(3, identifier);
                stmt.setString(4, identifier);
                stmt.setInt(5, verifiedDays);
                stmt.setDouble(6, verifiedDays * dailyWage);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "✅ Payment Processed Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "❌ No verified attendance found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error processing payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        processPayment();
    }
}