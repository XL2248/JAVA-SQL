package pkg;

import java.sql.*;

public class UpdateDemo {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            // ��̬�������ݿ������
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // ��ȡ���ݿ�����
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;DatabaseName=school",
                    "sa", "142907");
            // ����SQL���
            String sql = "UPDATE dbo.choice SET cid = '12345678' WHERE no = 11";
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