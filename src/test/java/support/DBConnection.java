package support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    Configurations configurations = Configurations.getInstance();
    private static DBConnection dbConnection;

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public synchronized String getDBValue(String sqlQueryName , String columnName ) {
        String serverName = configurations.getProperty("DBServerName");
        String dbName = configurations.getProperty("DBdatabaseName");
        String query = configurations.getProperty(sqlQueryName);
        String url = "jdbc:sqlserver://" + serverName + ";databaseName=" + dbName + ";integratedSecurity=true";
        String actualValue = "_DEFAULT_";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                actualValue = rs.getString(columnName);
            }
            conn.close();
            return actualValue;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception e1) {
            }
            return actualValue;
        }
    }

    public synchronized void updateRecord(String queryName, String column1, String value1) {
        String dbServerName = configurations.getProperty("DBServerName");
        String dbName = configurations.getProperty("DBdatabaseName");
        String query = configurations.getProperty(queryName.toString());
        String url = "jdbc:sqlserver://" + dbServerName + ";databaseName=" + dbName + ";integratedSecurity=true";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(query);
            while (rs > 0) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception e1) {
            }
        }
    }
}