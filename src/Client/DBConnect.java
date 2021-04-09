package Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	Connection con = null;	// connection 객체
	int flag = 0;	// DB 연결 성공 여부 체크(0: 실패, 1: 성공)
	
//	public DBConnect() {
//
//		DBConnecting();
//
//	}
	
	// JDBC 드라이버 및 연결을 위한 설정
	public static Connection DBConnecting() {
		Connection con = null;	// DB와 연결을 위한 객체 생성
		try {
			// 1) DB 접속주소
			String DB_URL = "jdbc:mysql://localhost:3306/pcroom_db?useSSL=false";
			
			// 2) JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 3) MySQL 서버 연결(connection 객체 생성)
			con = DriverManager.getConnection(DB_URL, "root", "toor");
			System.out.println("DB 연결 성공!");
			
		} catch (ClassNotFoundException e){
			System.out.println("JDBC 드라이버 오류!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 오류!");
			e.printStackTrace();
		}
		return con;
	}
	
	
}
