package pkg;

import java.sql.*;

public class DeleteDemo {

    public static void main(String[] args){
        String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=school";
        String user="sa";
        String password="142907";
        try{
            Class.forName(JDriver);// ��̬�������ݿ������
            Connection conn=DriverManager.getConnection(url, user, password);// ��ȡ���ݿ����� 
            String query="DELETE FROM dbo.choice WHERE no=11";// ����SQL��� 
            Statement stmt=conn.createStatement();// ִ��SQL���
            stmt.executeUpdate(query);
            System.out.println("ɾ�����ݳɹ�");
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}