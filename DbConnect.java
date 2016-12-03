package pkg;

import java.sql.*; 
import java.sql.Connection; 
import java.sql.Statement; 
import java.sql.ResultSet; 
import java.sql.DriverManager; 
import java.util.*; 

public class DbConnect 
{ 
/////////////////////////////////////////———–>>>数据成员 and 构造函数 
	private Connection dbconn; 
	private Statement dbstate; 
	private ResultSet dbresult; 
	 
	DbConnect() 
	{ 
		dbconn = null; 
		dbstate = null; 
		dbresult = null; 
	} 
	 
/////////////////////////////////////////———–>>>类方法 
	public void print(String str)//简化输出 
	{ 
		System.out.println(str); 
	}//end print(…) 
	 
	/** 
	 * 连接MySql数据库 
	 * @param host 
	 * @param port 
	 * @param dbaName 
	 * @param usName 
	 * @param psw 
	 * @return bool值，连接成功返回真，失败返回假 
	 */ 
	public boolean dbConnection(String host, String port, String dbaName, String usName, String psw) 
	{ 
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//"org.gjt.mm.mysql.Driver"两个驱动都可以用 
		String dbHost = host;//数据库的一些信息 
		String dbPort = port; 
		String dbName = dbaName; 
		String enCoding = "?useUnicode=true&characterEncoding=gb2312"; //解决MySql中文问题,要连续写不能空格 
		String userName = usName; 
		String Psw = psw; 
		//String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + enCoding; 
		String url="jdbc:sqlserver://localhost:1433;DatabaseName=Test";
		try 
		{ 
			Class.forName(driverName).newInstance(); 
			dbconn = DriverManager.getConnection(url, userName, Psw); 
			//getConnection(url, userName, Psw)从给的driver中选择合适的去连接数据库 
			//return a connection to the URL 
		}catch(Exception e){ 
			print("url = " + url); //发生错误时，将连接数据库信息打印出来 
			print("userName = " + userName); 
			print("Psw" + Psw); 
			print("Exception: " + e.getMessage());//得到出错信息 
		} 
		if (dbconn != null)//dbconn != null 表示连接数据库成功，由异常保证！？ 
			{
			System.out.println("lianjiechenggong");
			return true; 
			}
		else 
			return false; 
	}// end boolean dbConnection(…) 
 
 
 
	/** 
	 * 对数据库表进行选择操作！ 
	 * @param tableName 数据库表名 
	 * @param fieles 字段名 
	 * @param selCondition 选择条件 
	 * @return 一个含有map的List（列表） 
	 */ 
	public ArrayList dbSelect(String tableName, ArrayList fields, String selCondition) 
	{ 
		ArrayList mapInList = new ArrayList();	 
		String selFields = ""; 
		for (int i = 0; i<fields.size(); ++i) 
			selFields += fields.get(i) + ","; 
		String selFieldsTem = selFields.substring(0, selFields.length()-2);//根据String的索引提取子串 
		try{ 
			dbstate = dbconn.createStatement(); 
			String sql = "select" + selFieldsTem + " from " + tableName + selCondition; 
			print("sql = " + sql); 
			try{ 
				dbresult = dbstate.executeQuery(sql); 
			}catch(Exception err){ 
				print("Sql = " + sql); 
				print("Exception: " + err.getMessage()); 
			} 
			while(dbresult.next()){ 
				Map selResult = new HashMap(); 
				selResult.put("message_type", dbresult.getString("message_type")); 
				selResult.put("message_content", dbresult.getString("message_content")); 
				mapInList.add(selResult); 
			} 
		}catch(Exception e){ 
			print("Exception: " + e.getMessage()); 
		} 
		return mapInList; 
	}//end String dbSelect(…) 
	 
	/** 
	 * 对数据库表中的记录进行删除操作 
	 * @param tableName 
	 * @param condition 
	 * @return bool值，表示删除成功或者失败。 
	 */ 
	public boolean dbDelete(String tableName, String condition) 
	{//——–>>>删除操作 
		boolean delResult = false; 
		String sql = "delete from " + tableName + " " + condition; 
		try{ 
			dbstate.executeUpdate(sql);	//return int // int delRe = ?? 
			delResult = true; 
		}catch(Exception e){ 
			print ("sql = " + sql); 
			print ("Exception: " + e.getMessage()); 
		} 
		if (delResult) 
			return true; 
		else 
			return false; 
	}//end dbDelete(…) 
 
 
 
	/** 
	 * 对数据库表中记录进行更新操作 
	 * @param tabName 
	 * @param reCount 
	 * @return bool值，成功返回true，失败返回false 
	 */ 
	public boolean dbUpdate(String tabName, HashMap reCount, String upCondition) 
	{ 
		boolean updateResult = false; 
		String Values = ""; 
		Iterator keyValues = reCount.entrySet().iterator(); 
		for(int i = 0; i<reCount.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			Values += key + "=" + "’" + value + "’" + ", "; 
		} 
		String updateValues = Values.substring(0, Values.length()-2); 
		String sql = "update " + tabName + " set " + updateValues + " " + upCondition; 
		try 
		{ 
			dbstate.executeUpdate(sql); 
			updateResult = true; 
		}catch(Exception err){ 
			print("sql = " + sql); 
			print("Exception: " + err.getMessage()); 
		} 
		if (updateResult) 
			return true; 
		else 
			return false; 
	}//end dbUpdate(…) 
	 
	/** 
	 * 对数据库表进行插入操作 
	 * @param tabName 
	 * @param hm 
	 * @return bool值，成功返回true，失败返回false 
	 */ 
	public boolean dbInsert(String tabName, HashMap values) 
	{ 
		String sql = ""; 
		String insertFields = "", temFields = ""; 
		String insertValues = "", temValues = ""; 
		boolean insertResult = false; 
		Iterator keyValues = values.entrySet().iterator(); 
		for(int i = 0; i<values.size(); ++i) 
		{ 
			Map.Entry entry = (Map.Entry) keyValues.next(); 
			Object key = entry.getKey(); 
			Object value = entry.getValue(); 
			temFields += key + ", "; 
			temValues += "’" + value + "’" + ", "; 
		} 
		insertFields = temFields.substring(0, temFields.length()-2); 
		insertValues = temValues.substring(0, temValues.length()-2); 
		sql += "insert into " + tabName + " (" + insertFields + ") values" + "(" + insertValues + ")"; 
		try 
		{ 
			dbstate.executeUpdate(sql); 
			insertResult = true; 
		}catch(Exception e){ 
			print("Sql = " + sql); 
			print("Exception: " + e.getMessage()); 
		} 
		if (insertResult) 
			return true; 
		else 
			return false; 
	}//end dbInsert(…) 
	 
	/** 
	 * 断开数据库 
	 * @return bool值，成功返回true，失败返回false 
	 */ 
	public boolean dbClose() 
	{ 
		boolean closeResult = false; 
		try 
		{ 
			dbconn.close(); 
			closeResult = true; 
		}catch(Exception e){ 
			print("Exception: " + e.getMessage()); 
		} 
		if (closeResult) 
			return true; 
		else 
			return false; 
	}//end dbClose() 
/////////////////////////////////////////———–>>>入口主函数 
	 
	public static void main(String[] args) 
	{ 
		DbConnect dc = new DbConnect(); 
		//连接数据库用到的一些参数. 
		String dbHost = "localhost"; 
		String dbPort = "1433"; 
		String dbName = "Test"; 
		String dbuserName = "sa"; 
		String dbpsw = "142907"; 
		 
		boolean con = dc.dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw); //连接数据库 
		System.out.println("con的值是："+con);
		if (con) 
		{ 
			ArrayList fields = new ArrayList(); 
			fields.add("Name"); 
			fields.add("Sexl"); 
			Map lmap = new HashMap(); 
			 
			String selCondition = " where Province = null"; 
			ArrayList str = dc.dbSelect("Test", fields, selCondition); //—>>>选择记录 
			if (str.size()!= 0){ 
				dc.print("select OK!"); 
				dc.print("str’s size = " + str.size()); 
				for(int i = 0; i<str.size(); ++i){ 
					lmap = (HashMap)str.get(i); 
					dc.print("lmap = " + lmap); 
				} 
			} 
		} 
		else 
			dc.print("fail"); 
		 
		String tableName = "dbo.UserInfo"; 
		String condition = "where City = 5"; 
		boolean del = dc.dbDelete(tableName, condition);//—>>>删除数据 
		if ( del) 
			dc.print("delete ok"); 
		else 
			dc.print("delete error!"); 
 
 
 
		HashMap m = new HashMap(); 
		m.put("message_content", "ookkk"); 
		m.put("message_number", "9"); 
		String upCondition = "where City = 4"; 
		boolean ur = dc.dbUpdate("UserInfo", m, upCondition); //—>>>更新数据 
		if ( ur) 
			dc.print("update OK"); 
		else 
			dc.print("update error!"); 
		 
		HashMap mm = new HashMap(); 
		mm.put("Name", "lyl"); 
		mm.put("Province", "m"); 
		mm.put("Sex", "4"); 
		boolean in = dc.dbInsert("UserInfo", mm); //—>>>插入 
		if (in) 
			dc.print("insert OK"); 
		else 
			dc.print("insert error!"); 
		 
		boolean close = dc.dbClose();//—–>断开数据库 
		if (close) 
			dc.print("close OK"); 
		else 
			dc.print("close fail"); 
	}//end main() 
	 
}//end class DbConnect 

