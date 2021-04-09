package Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class SQLExecute {
	static Connection con = DBConnect.DBConnecting();
	
	// 로그인 시 아이디 비번 맞는 행 개수 반환 Select_SQL
	static int orderSelectMonthSQL(String id, String pw) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT 아이디, 비밀번호 FROM 회원 WHERE 아이디 = '" + id + "' AND 비밀번호 = '" + pw + "'";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("로그인 시 아이디 비번 맞는 행 개수 반환 Select_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		
		int rowsCount = Common.rowsCount(db.rs);	// 행 개수 반환
		Common.closeDB(db);
		return rowsCount;
	}
	
	// 회원가입 SQL
	static int signupSQL(String id, String name, String pw, String phone, Date birth, String email) throws Exception {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "INSERT INTO 회원(아이디, 이름, 비밀번호, 휴대폰, 생년월일, 이메일) " + 
				"VALUES(?, ?, ?, ?, ?, ?);";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, id);
			pst.setString(2, name);
			pst.setString(3, pw);
			pst.setString(4, phone);
			pst.setDate(5, (java.sql.Date) birth);
			pst.setString(6, email);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("회원가입 SQL 실행 중 오류!");
//			e.printStackTrace();
			throw e;
		} finally {
			// 사용순서와 반대로 close
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(pst != null) {
				try {
					pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 아이디 찾기 SQL
	static DBManager findID_SQL(String name, String phoneOrEmail, String 조건) {
		DBManager db = new DBManager();
		String SQL = null;
		
		switch(조건) {
		case "휴대폰": 
			SQL = "SELECT 아이디 FROM 회원 WHERE 이름 = '" + name + "' AND 휴대폰 = '" + phoneOrEmail + "'";
			break;
		case "이메일":
			SQL = "SELECT 아이디 FROM 회원 WHERE 이름 = '" + name + "' AND 이메일= '" + phoneOrEmail + "'";
			break;
		default:
			System.out.println("아이디 찾기 SQL 조건 오류!!");
			break;
		}
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("아이디 찾기_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 비밀번호 찾기 SQL
	static DBManager findPW_SQL(String name, String id, String phoneOrEmail, String 조건) {
		DBManager db = new DBManager();
		String SQL = null;
		
		switch(조건) {
		case "휴대폰": 
			SQL = "SELECT 비밀번호 FROM 회원 WHERE 이름 = '" + name + "' AND 아이디 = '" + id + "' AND 휴대폰 = '" + phoneOrEmail + "'";
			break;
		case "이메일":
			SQL = "SELECT 비밀번호 FROM 회원 WHERE 이름 = '" + name + "' AND 아이디 = '" + id + "' AND 이메일 = '" + phoneOrEmail + "'";
			break;
		default:
			System.out.println("비밀번호 찾기 SQL 조건 오류!!");
			break;
		}
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("비밀번호 찾기_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
}
