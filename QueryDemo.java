package pkg;

import java.sql.*;

public class QueryDemo {

    public static void main(String[] args){
        String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=school";
        String user="sa";
        String password="142907";
        try{
            Class.forName(JDriver);// ��̬�������ݿ������
            Connection conn=DriverManager.getConnection(url, user, password);// ��ȡ���ݿ����� 
            String query="SELECT * FROM dbo.choice";// ����SQL��� 
            Statement stmt=conn.createStatement();// ִ��SQL���
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("no")+":"+rs.getString(3));
            }
            System.out.println("��ѯ���ݳɹ�");
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
