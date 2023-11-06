package com.nashtech.assetmanagement.repos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.nashtech.assetmanagement.utils.JdbcSQLServerConnection.getConnectionDB;

public class ManageUserFunction {
    public static JsonArray getUserListInDFollowLocation(String location) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String sql = "select StaffCode,FirstName+' '+LastName as FullName,UserName, convert(varchar, JoinedDate, 103) as JoinedDate,R.Name as Type\n" +
                "from dbo.Users US inner join UserRoles UR on US.id = UR.UserId\n" +
                "\tinner join Roles R on UR.RoleId = R.Id\n" +
                "where LocationID='%s' and LockoutEnabled='False'\n".formatted(location) +
                "order by StaffCode";
        JsonArray array = new JsonArray();
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject record = new JsonObject();
            record.addProperty("staffCode", rs.getString("staffCode"));
            record.addProperty("fullName", rs.getString("fullName"));
            record.addProperty("userName", rs.getString("userName"));
            record.addProperty("joinedDate", rs.getString("joinedDate"));
            record.addProperty("type", rs.getString("type"));
            array.add(record);
        }
        return array;
    }

    public static JsonArray getUserList() throws SQLException {
        Connection connectionDB = getConnectionDB();
        String sql = "select StaffCode,FirstName+' '+LastName as FullName,UserName, convert(varchar, JoinedDate, 103) as JoinedDate,R.Name as Type\n"
                + "from dbo.Users as US,UserRoles as UR, Roles as R \n"
                + "where US.id = UR.UserId and UR.RoleId = R.id "
                + "order by StaffCode";
        JsonArray array = new JsonArray();
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject record = new JsonObject();
            record.addProperty("staffCode", rs.getString("staffCode"));
            record.addProperty("fullName", rs.getString("fullName"));
            record.addProperty("userName", rs.getString("userName"));
            record.addProperty("joinedDate", rs.getString("joinedDate"));
            record.addProperty("type", rs.getString("type"));
            array.add(record);
        }
        return array;
    }

    public static JsonArray searchUserListWithKeyWord(String keyWord) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String key = "'%" + "%s".formatted(keyWord) + "%'";
        String sql = "select StaffCode,FirstName+' '+LastName as FullName,UserName, convert(varchar, JoinedDate, 103) as JoinedDate,R.Name as Type\n"
                + "from dbo.Users as US,UserRoles as UR, Roles as R \n"
                + "where LocationID='HN' and LockoutEnabled='False' and US.id = UR.UserId and UR.RoleId = R.id and (LastName+' '+FirstName like %s or StaffCode like %s)"
                .formatted(key, key);
        JsonArray array = new JsonArray();
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject record = new JsonObject();
            record.addProperty("staffCode", rs.getString("staffCode"));
            record.addProperty("fullName", rs.getString("fullName"));
            record.addProperty("userName", rs.getString("userName"));
            record.addProperty("joinedDate", rs.getString("joinedDate"));
            record.addProperty("type", rs.getString("type"));
            array.add(record);
        }
        return array;
    }
}
