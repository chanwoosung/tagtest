package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtillity {
	public static Connection getConnection(){
		//1.MySql database class를 로드한다.
		Connection con=null;
		try{
		Class.forName("com.mysql.jdbc.Driver");
		//2.주소, 아이디,비밀번호를 통해서 접속요청한다.
		con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/mafiadb", "root", "123456");
		
		
		}catch(Exception e) {
			RootController.callAlert("DB연결실패: 데이터베이스 점검바람.");
			return null;
		}
		return con;
	}
}
