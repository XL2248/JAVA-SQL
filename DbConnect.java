package pkg;

import java.sql.*; 
import java.sql.Connection; 
import java.sql.Statement; 
import java.sql.ResultSet; 
import java.sql.DriverManager; 
import java.util.*; 

public class DbConnect 
{ 
/////////////////////////////////////////�������C>>>���ݳ�Ա and ���캯�� 
	private Connection dbconn; 
	private Statement dbstate; 
	private ResultSet dbresult; 
	 
	DbConnect() 
	{ 
		dbconn = null; 
		dbstate = null; 
		dbresult = null; 
	} 
	 
/////////////////////////////////////////�������C>>>�෽�� 
	public void print(String str)//����� 
	{ 
		System.out.println(str); 
	}//end print(��) 
	 
	/** 
	 * ����MySql���ݿ� 
	 * @param host 
	 * @param port 
	 * @param dbaName 
	 * @param usName 
	 * @param psw 
	 * @return boolֵ�����ӳɹ������棬ʧ�ܷ��ؼ� 
	 */ 
	public boolean dbConnection(String host, String port, String dbaName, String usName, String psw) 
	{ 
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//"org.gjt.mm.mysql.Driver"���������������� 
		String dbHost = host;//���ݿ��һЩ��Ϣ 
		String dbPort = port; 
		String dbName = dbaName; 
		String enCoding = "?useUnicode=true&characterEncoding=gb2312"; //���MySql��������,Ҫ����д���ܿո� 
		String userName = usName; 
		String Psw = psw; 
		//String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + enCoding; 
		String url="jdbc:sqlserver://localhost:1433;DatabaseName=Test";
		try 
		{ 
			Class.forName(driverName).newInstance(); 
			dbconn = DriverManager.getConnection(url, userName, Psw); 
			//getConnection(url, userName, Psw)�Ӹ���driver��ѡ����ʵ�ȥ�������ݿ� 
			//return a connection to the URL 
		}catch(Exception e){ 
			print("url = " + url); //��������ʱ�����������ݿ���Ϣ��ӡ���� 
			print("userName = " + userName); 
			print("Psw" + Psw); 
			print("Exception: " + e.getMessage());//�õ�������Ϣ 
		} 
		if (dbconn != null)//dbconn != null ��ʾ�������ݿ�ɹ������쳣��֤���� 
			{
			System.out.println("lianjiechenggong");
			return true; 
			}
		else 
			return false; 
	}// end boolean dbConnection(��) 
 
 
 
	/** 
	 * �����ݿ�����ѡ������� 
	 * @param tableName ���ݿ���� 
	 * @param fieles �ֶ��� 
	 * @param selCondition ѡ������ 
	 * @return һ������map��List���б� 
	 */ 
	public ArrayList dbSelect(String tableName, ArrayList fields, String selCondition) 
	{ 
		ArrayList mapInList = new ArrayList();	 
		String selFields = ""; 
		for (int i = 0; i<fields.size(); ++i) 
			selFields += fields.get(i) + ","; 
		String selFieldsTem = selFields.substring(0, selFields.length()-2);//����String��������ȡ�Ӵ� 
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
	}//end String dbSelect(��) 
	 
	/** 
	 * �����ݿ���еļ�¼����ɾ������ 
	 * @param tableName 
	 * @param condition 
	 * @return boolֵ����ʾɾ���ɹ�����ʧ�ܡ� 
	 */ 
	public boolean dbDelete(String tableName, String condition) 
	{//�����C>>>ɾ������ 
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
	}//end dbDelete(��) 
 
 
 
	/** 
	 * �����ݿ���м�¼���и��²��� 
	 * @param tabName 
	 * @param reCount 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
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
			Values += key + "=" + "��" + value + "��" + ", "; 
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
	}//end dbUpdate(��) 
	 
	/** 
	 * �����ݿ����в������ 
	 * @param tabName 
	 * @param hm 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
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
			temValues += "��" + value + "��" + ", "; 
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
	}//end dbInsert(��) 
	 
	/** 
	 * �Ͽ����ݿ� 
	 * @return boolֵ���ɹ�����true��ʧ�ܷ���false 
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
/////////////////////////////////////////�������C>>>��������� 
	 
	public static void main(String[] args) 
	{ 
		DbConnect dc = new DbConnect(); 
		//�������ݿ��õ���һЩ����. 
		String dbHost = "localhost"; 
		String dbPort = "1433"; 
		String dbName = "Test"; 
		String dbuserName = "sa"; 
		String dbpsw = "142907"; 
		 
		boolean con = dc.dbConnection(dbHost, dbPort, dbName, dbuserName, dbpsw); //�������ݿ� 
		System.out.println("con��ֵ�ǣ�"+con);
		if (con) 
		{ 
			ArrayList fields = new ArrayList(); 
			fields.add("Name"); 
			fields.add("Sexl"); 
			Map lmap = new HashMap(); 
			 
			String selCondition = " where Province = null"; 
			ArrayList str = dc.dbSelect("Test", fields, selCondition); //��>>>ѡ���¼ 
			if (str.size()!= 0){ 
				dc.print("select OK!"); 
				dc.print("str��s size = " + str.size()); 
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
		boolean del = dc.dbDelete(tableName, condition);//��>>>ɾ������ 
		if ( del) 
			dc.print("delete ok"); 
		else 
			dc.print("delete error!"); 
 
 
 
		HashMap m = new HashMap(); 
		m.put("message_content", "ookkk"); 
		m.put("message_number", "9"); 
		String upCondition = "where City = 4"; 
		boolean ur = dc.dbUpdate("UserInfo", m, upCondition); //��>>>�������� 
		if ( ur) 
			dc.print("update OK"); 
		else 
			dc.print("update error!"); 
		 
		HashMap mm = new HashMap(); 
		mm.put("Name", "lyl"); 
		mm.put("Province", "m"); 
		mm.put("Sex", "4"); 
		boolean in = dc.dbInsert("UserInfo", mm); //��>>>���� 
		if (in) 
			dc.print("insert OK"); 
		else 
			dc.print("insert error!"); 
		 
		boolean close = dc.dbClose();//���C>�Ͽ����ݿ� 
		if (close) 
			dc.print("close OK"); 
		else 
			dc.print("close fail"); 
	}//end main() 
	 
}//end class DbConnect 

