package com.nashtech.assetmanagement.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.nashtech.assetmanagement.utils.JdbcSQLServerConnection.getConnectionDB;

public class ManageAssignmentFunction {
    public static ResultSet getAssignmentListInDFollowLocation(String location) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String sql = "SELECT asm.AssetCode\n" +
                "FROM Assignments asm JOIN Assets asset ON asm.AssetCode = asset.AssetCode\n" +
                "\t\t\t\t\t JOIN Users userAssigned ON asm.AssignedToUserId = userAssigned.Id\n" +
                "\t\t\t\t\t JOIN Users userAssign ON asm.AssignedByUserId = userAssign.Id\n" +
                "WHERE userAssign.LocationID = '%s' AND \n".formatted(location) +
                "\t  asm.IsDeleted = 0 AND \n" +
                "\t  userAssigned.LockoutEnabled = 0 AND\n" +
                "\t  userAssign.LockoutEnabled = 0 AND \n" +
                "\t  asset.IsDeleted = 0 AND\n" +
                " \t  (asm.AssignmentState = 1 OR asm.AssignmentState = 2)\n" +
                "ORDER BY asm.AssetCode";
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static ResultSet searchAssignmentListInDFollowLocation(String location, String keyWord) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String key = "'%" + "%s".formatted(keyWord) + "%'";
        String sql = "SELECT asm.AssetCode\n" +
                "FROM Assignments asm JOIN Assets asset ON asm.AssetCode = asset.AssetCode\n" +
                "\t\t\t\t\t JOIN Users userAssigned ON asm.AssignedToUserId = userAssigned.Id\n" +
                "\t\t\t\t\t JOIN Users userAssign ON asm.AssignedByUserId = userAssign.Id\n" +
                "WHERE userAssign.LocationID = '%s' AND \n".formatted(location) +
                "\t  asm.IsDeleted = 0 AND \n" +
                "\t  userAssigned.LockoutEnabled = 0 AND\n" +
                "\t  userAssign.LockoutEnabled = 0 AND \n" +
                "\t  asset.IsDeleted = 0 AND\n" +
                " \t  (asm.AssignmentState = 1 OR asm.AssignmentState = 2) and (asm.AssetCode like %s or asset.AssetName like %s or userAssigned.UserName like %s)\n".formatted(key, key, key)
                + "ORDER BY asm.AssetCode";
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static int getSize(ResultSet resultSet) throws SQLException {
        int i = 0;
        while (resultSet.next()) {
            i++;
        }
        return i;
    }

}
