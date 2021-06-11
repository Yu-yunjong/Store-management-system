package Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

class Common {
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
	
	// 비밀번호 암호화: sha256
	// throws NoSuchAlgorithmException
	public static String sha256(String pw) {
	    MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(pw.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("암호화 알고리즘 구동 실패!");
			e.printStackTrace();
		}
		return byteToHexString(md.digest());
	}
	
	public static String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
