package Server;

import java.sql.*;

public class DBManager {
	//Connection conn = null;
	PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체(UPDATE문)
	Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체(SELECT문)
	ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
}
