package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecute {
	Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
	ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
	
	// 회원 select
	public ResultSet memberSelectSQL(Connection con) {
//		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
//		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int updateRowsCount = -1;	// 기본값 -1
		
		String SQL = "SELECT 아이디, 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, 남은시간 FROM 회원, 시간 WHERE 회원.아이디 = 시간.회원아이디";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴

				rs = st.executeQuery(SQL);

		} catch(Exception e) {
			System.out.println("회원 select SQL 실행 중 오류!");
			e.printStackTrace();
			
			// 사용순서와 반대로 close
//			if(rs != null) {
//				try {
//					rs.close();
//				} catch(SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			
//			if(st != null) {
//				try {
//					st.close();
//				} catch(SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
			
//			if(con != null) {
//				try {
//					con.close();
//				} catch(SQLException e) {
//					e.printStackTrace();
//				}
//			}
		} finally {

		}
		return rs;

	}
	
	// 비밀번호 초기화
	public ResultSet memberPwResetSQL(Connection con) {
//		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
//		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int updateRowsCount = -1;	// 기본값 -1
		
		String SQL = "UPDATE 회원 SET 비밀번호 = (비번) WHERE 아이디 = (아이디)";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
				rs = st.executeQuery(SQL);

		} catch(Exception e) {
			System.out.println("비밀번호 초기화 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {

		}
		return rs;

	}
}
