package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtillity {
	public static Connection getConnection(){
		//1.MySql database class�� �ε��Ѵ�.
		Connection con=null;
		try{
		Class.forName("com.mysql.jdbc.Driver");
		//2.�ּ�, ���̵�,��й�ȣ�� ���ؼ� ���ӿ�û�Ѵ�.
		con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/mafiadb", "root", "123456");
		
		
		}catch(Exception e) {
			RootController.callAlert("DB�������: �����ͺ��̽� ���˹ٶ�.");
			return null;
		}
		return con;
	}
}
