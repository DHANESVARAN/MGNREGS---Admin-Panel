package com.dhanesvaranindustries.mgnregs_project;

import java.sql.*;

public class Utils {
    public static boolean isWorkerRegistered(int workerId) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM workers WHERE worker_id = ?"
            );
            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("count") > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getWorkerAttendanceDays(int workerId) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(attendance_id) AS work_days FROM attendance WHERE worker_id = ? AND verified = 'Yes'"
            );
            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("work_days");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}