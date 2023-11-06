package com.nashtech.assetmanagement.repos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.nashtech.assetmanagement.utils.JdbcSQLServerConnection.getConnectionDB;

public class ManageAssetFunction {
    public static JsonArray getAssetListInDFollowLocation(String location) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String sql = "select AssetCode,AssetName,CategoryName, AssetState \n" + "from Assets as A, Categories as C\n"
                + "where locationID='%s' and isDeleted='False' and A.CategoryID=C.CategoryID".formatted(location);
        String[] state = {"Available", "Assigned", "Waiting for recycling", "Recycled", "Not available"};
        JsonArray array = new JsonArray();
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject record = new JsonObject();
            String temp = state[rs.getInt("assetState") - 1];
            if (temp.equals("Available") | temp.equals("Assigned") | temp.equals("Not available")) {
                record.addProperty("assetCode", rs.getString("assetCode"));
                record.addProperty("assetName", rs.getString("assetName"));
                record.addProperty("category", rs.getString("categoryName"));
                record.addProperty("state", state[rs.getInt("assetState") - 1]);
                array.add(record);
            }
        }
        return array;
    }

    public static JsonArray searchAssetListWithKeyWord(String location, String keyWord) throws SQLException {
        Connection connectionDB = getConnectionDB();
        String key = "'%" + "%s".formatted(keyWord) + "%'";
        String sql = "select AssetCode,AssetName,CategoryName, AssetState \n" + "from Assets as A, Categories as C\n"
                + "where locationID='%s' and isDeleted='False' and A.CategoryID=C.CategoryID and (AssetCode like %s or AssetName like %s)"
                .formatted(location, key, key);
        String[] state = {"Available", "Assigned", "Waiting for recycling", "Recycled", "Not available"};

        JsonArray array = new JsonArray();
        PreparedStatement ps = connectionDB.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject record = new JsonObject();
            record.addProperty("assetCode", rs.getString("assetCode"));
            record.addProperty("assetName", rs.getString("assetName"));
            record.addProperty("category", rs.getString("categoryName"));
            record.addProperty("state", state[rs.getInt("assetState") - 1]);
            array.add(record);
        }
        return array;
    }
}
