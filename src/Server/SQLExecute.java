package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecute {
	public SQLExecute(Connection con) {
		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int updateRowsCount = -1;
		
		String SQL = "SELECT * FROM 회원";
		
		try {
			// 1) Statement 객체 생성
			st = con.createStatement();
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			if(SQL.substring(0, 5).equalsIgnoreCase("SELECT")) {
				rs = st.executeQuery(SQL);
				// 3) ResultSet에 저장된 데이터 얻어오기
				while(rs.next()) {
					String id = rs.getString("아이디");
					
					System.out.println(id);
				}	
			}
			else
				updateRowsCount = st.executeUpdate(SQL);
			
		} catch(Exception e) {
			
		} finally {
			// 사용순서와 반대로 close
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(st != null) {
				try {
					st.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
//			if(con != null) {
//				try {
//					con.close();
//				} catch(SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}

	}
	
	public void SQL() {
		
	}
}
