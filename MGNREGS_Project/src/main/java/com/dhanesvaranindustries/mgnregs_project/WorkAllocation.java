package com.dhanesvaranindustries.mgnregs_project;

import java.sql.*;

public class WorkAllocation {

    // 📌 Assign Work to a Worker
    public static void assignWork(int workerId, String taskDescription) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO tasks (worker_id, task_description, assigned_date, completion_status) " +
                "VALUES (?, ?, NOW(), 'Pending')"
            );

            stmt.setInt(1, workerId);
            stmt.setString(2, taskDescription);
            stmt.executeUpdate();
            System.out.println("✅ Work Assigned Successfully!");

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📌 View Assigned Tasks for a Worker
    public static void viewAssignedTasks(int workerId) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT task_id, task_description, assigned_date, completion_status FROM tasks WHERE worker_id = ?"
            );

            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("📋 Assigned Tasks for Worker ID " + workerId);
            while (rs.next()) {
                System.out.println("📝 Task ID: " + rs.getInt("task_id") +
                                   " | Description: " + rs.getString("task_description") +
                                   " | Assigned: " + rs.getDate("assigned_date") +
                                   " | Status: " + rs.getString("completion_status"));
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📌 Main Method (Example Usage)
    public static void main(String[] args) {
        assignWork(1, "Build a water reservoir");
        viewAssignedTasks(1);
    }
}