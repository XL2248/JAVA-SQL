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
            // ��̬�������ݿ������ 
            Class.forName(JDriver);
            // ��ȡ���ݿ����� 
            conn = DriverManager.getConnection(connectDB,user,pwd);
            // ����SQL��� 
            String sql = "INSERT INTO dbo.choice ( no,sid, cid ) VALUES (11, 'balabala', '123456' )";
            // ִ��SQL��� 
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("�������ݳɹ�");
        } catch (Exception e) {    
            e.printStackTrace();
            stmt.close();
            conn.close();
        }
    }
}
