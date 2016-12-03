package pkg;

import java.sql.*;

public class UpdateDemo {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 动态导入数据库的驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // 获取数据库链接
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;DatabaseName=school",
                    "sa", "142907");
            // 创造SQL语句
            String sql = "UPDATE dbo.choice SET cid = '12345678' WHERE no = 11";
            // 执行SQL语句
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("更新数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            stmt.close();
            conn.close();
        }
    }
}