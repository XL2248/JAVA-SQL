package pkg;

import java.sql.*;

public class QueryDemo {

    public static void main(String[] args){
        String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=school";
        String user="sa";
        String password="142907";
        try{
            Class.forName(JDriver);// 动态导入数据库的驱动
            Connection conn=DriverManager.getConnection(url, user, password);// 获取数据库链接 
            String query="SELECT * FROM dbo.choice";// 创造SQL语句 
            Statement stmt=conn.createStatement();// 执行SQL语句
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("no")+":"+rs.getString(3));
            }
            System.out.println("查询数据成功");
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
