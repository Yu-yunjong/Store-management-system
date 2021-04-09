package Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Common {
	// DB의 행 개수 계산(table 생성용)
	static int rowsCount(ResultSet rs) {
		int rowCount = 0;
		try {
			rs.last();	// 커서를 맨 끝으로 이동
			rowCount = rs.getRow();	// 현재 커서의 Row Index 값을 저장
			rs.beforeFirst();	// 뒤에서 다시 사용해야 하므로 커서를 맨 앞으로 이동
			//System.out.println(rowCount);
		} catch (SQLException e1) {
			System.out.println("행 개수 계산 오류!");
			e1.printStackTrace();
		}
		return rowCount;
	}
	
	// close
	static void closeDB(DBManager db) {
		if(db.rs != null) {
			try {
				db.rs.close();
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(db.st != null) {
			try {
				db.st.close();
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
