package pkg;

import java.sql.*;

public class InsertDemo {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";// 
        String connectDB="jdbc:sqlserver://localhost:1433;DatabaseName=school";//
        String user="sa";
        String pwd="142907";
        try {
            // 动态导入数据库的驱动 
            Class.forName(JDriver);
            // 获取数据库链接 
            conn = DriverManager.getConnection(connectDB,user,pwd);
            // 创造SQL语句 
            String sql = "INSERT INTO dbo.choice ( no,sid, cid ) VALUES (11, 'balabala', '123456' )";
            // 执行SQL语句 
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("插入数据成功");
        } catch (Exception e) {    
            e.printStackTrace();
            stmt.close();
            conn.close();
        }
    }
}
