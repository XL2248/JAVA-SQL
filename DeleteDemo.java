package pkg;

import java.sql.*;

public class DeleteDemo {

    public static void main(String[] args){
        String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=school";
        String user="sa";
        String password="142907";
        try{
            Class.forName(JDriver);// 动态导入数据库的驱动
            Connection conn=DriverManager.getConnection(url, user, password);// 获取数据库链接 
            String query="DELETE FROM dbo.choice WHERE no=11";// 创造SQL语句 
            Statement stmt=conn.createStatement();// 执行SQL语句
            stmt.executeUpdate(query);
            System.out.println("删除数据成功");
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}