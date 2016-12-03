package pkg;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.Statement;    
    
public class Test {    
    
    public static void main(String args[]) {    
        // Create a variable for the connection string.  
    
        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=school;user=sa;password=142907";//sa身份连接    
    
        //String url2 = "jdbc:sqlserver://127.0.0.1:1368;databaseName=mydb;integratedSecurity=true;";//windows集成模式连接    
    
        // Declare the JDBC objects.    
        Connection con = null;    
        Statement stmt = null;    
        ResultSet rs = null;    
    
        try {    
            // Establish the connection.    
            System.out.println("begin.");    
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");    
            con = DriverManager.getConnection(url);    
            System.out.println("end.");    
    
            // Create and execute an SQL statement that returns some data.  
            //String sql1="insert into dbo.choice(no,sid,cid) values(19,'9','9')";
            //stmt = con.createStatement();
            //stmt.executeUpdate(sql1);
            
            String SQL = "SELECT TOP 10 * FROM dbo.choice";    
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);    
    
            // Iterate through the data in the result set and display it.    
            while (rs.next()) {    
                System.out.println(rs.getString(1) + "  " + rs.getString(2)+ "" + rs.getString(3));    
            }    
        }    
    
        // Handle any errors that may have occurred.    
        catch (Exception e) {    
            e.printStackTrace();    
        }    
    
        finally {    
            if (rs != null)    
                try {    
                    rs.close();    
                } catch (Exception e) {    
                }    
            if (stmt != null)    
                try {    
                    stmt.close();    
                } catch (Exception e) {    
                }    
            if (con != null)    
                try {    
                    con.close();    
                } catch (Exception e) {    
                }    
        }    
    }    
}    