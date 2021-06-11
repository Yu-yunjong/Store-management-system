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
	
	// 클라이언트 창_회원정보 조회
	static DBManager memberInfo_SQL(String id) {
		DBManager db = new DBManager();
		String SQL = "SELECT 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, pc상태.pc번호, 남은시간 FROM 회원, pc상태, 시간 WHERE 회원.아이디 = 시간.회원아이디 AND 회원.아이디 = pc상태.사용자ID AND 회원.아이디 = '" + id + "';";
		
		try {
			// 1) Statement 객체 생성
			db.st = con.createStatement();
			
			// 2) SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("회원정보 조회_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 회원 정보 수정
	public static int memberInfoChange(String id, String phone, Date birthday, String email) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 회원 SET 휴대폰 = ?, 생년월일 = ?, 이메일 = ? WHERE 아이디 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setString(1, phone);
			db.pst.setDate(2, (java.sql.Date) birthday);	// 날짜 형식에 맞게 캐스팅 필요
			db.pst.setString(3, email);
			db.pst.setString(4, id);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("회원 정보 수정 SQL(클라이언트) 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 로그인 성공 시 최근 로그인 시간 남기기
	public static int memberLoginTimeUpdate(String id) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 회원 SET 최근로그인일자=now() WHERE 아이디 = ?;";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setString(1, id);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("로그인 성공 시 최근 로그인 시간 업데이트 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 사용중인 시간을 DB에 반영
	public static int timeUpdate(String id, int timeSec) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 시간 SET 남은시간=? WHERE 회원아이디 = ?;";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setInt(1, timeSec);
			db.pst.setString(2, id);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("사용중인 시간을 DB에 반영 업데이트 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 다음 주문번호 조회
	static DBManager selectNextSerial_SQL(String serial) {
		DBManager db = new DBManager();
		String SQL = "SELECT * FROM 다음시리얼 WHERE 항목 = '" + serial + "';";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("다음 주문번호 조회_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 다음 주문번호 업데이트
	public static int updateOrderNo(String serial) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 다음시리얼 SET 시리얼=시리얼+1 WHERE 항목 = ?;";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setString(1, serial);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("다음 시리얼 업데이트 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 주문항목 추가
	static int insertOrderSQL(String ono, String pno, String id, String pcNo, int count, int price, int payMethod, String status, String memo) throws Exception {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "INSERT INTO 주문  VALUES(?, ?, ?, ?, now(), ?, ?, ?, ?, ?);";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, ono);
			pst.setString(2, pno);
			pst.setString(3, id);
			pst.setString(4, pcNo);
			pst.setInt(5, count);
			pst.setInt(6, price);
			pst.setInt(7, payMethod);
			pst.setString(8, status);
			pst.setString(9, memo);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("주문항목 추가 SQL 실행 중 오류!");
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
	
	// 상품번호로 상품정보 조회
	static DBManager selectProductInfo_SQL(String pno) {
		DBManager db = new DBManager();
		String SQL = "SELECT * FROM 상품 WHERE 상품코드 = '" + pno + "';";
		
		try {
			// 1) Statement 객체 생성
			db.st = con.createStatement();
			
			// 2) SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("상품번호로 상품정보 조회_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 상품 수량 업데이트
	public static int updateProductAmount(String pno, int amount) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 상품 SET 수량=수량-?, WHERE 상품코드 = ?;";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setInt(1, amount);
			db.pst.setString(2, pno);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("상품 수량 업데이트 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 시간 구입을 DB에 반영
	public static int timeOrderUpdate(String id, int timeSec) {
		DBManager db = new DBManager();
		int returnValue = 0;
		
		String SQL = "UPDATE 시간 SET 남은시간=남은시간+? WHERE 회원아이디 = ?;";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			db.pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			db.pst.setInt(1, timeSec);
			db.pst.setString(2, id);
			
			returnValue = db.pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("시간 구입을 DB에 반영 업데이트 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(db.pst != null) {
				try {
					db.pst.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returnValue;
	}
	
	// 사용중이 아니고, 고장이 아닌 pc번호 번호 오름차순 조회
	static DBManager selectPC_SQL() {
		DBManager db = new DBManager();
		String SQL = "SELECT * FROM pc상태 WHERE pc상태 = 1 AND 사용자ID = '' ORDER BY PC번호*1;;";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
	
		} catch(Exception e) {
			System.out.println("사용중이 아니고, 고장이 아닌 pc번호 번호 오름차순 조회_SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// pc상태 변경
	public static int pcStateChange(String uid, String pcNum) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE pc상태 SET 사용자ID = ? WHERE pc번호 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, uid);
			pst.setString(2, pcNum);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("PC 상태 변경 SQL 실행 중 오류!");
			e.printStackTrace();
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
	
	// 비밀번호 초기화
	public static int memberPwResetSQL(String id, String pw) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE 회원 SET 비밀번호 = ? WHERE 아이디 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, pw);
			pst.setString(2, id);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("비밀번호 초기화 SQL 실행 중 오류!");
			e.printStackTrace();
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
}
