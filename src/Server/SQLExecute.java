package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SQLExecute {
//	Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
//	ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
	
	// 회원 select
	public ResultSet memberSelectSQL(Connection con) {
		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
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
	public int memberPwResetSQL(Connection con, String id, String birthday) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE 회원 SET 비밀번호 = ? WHERE 아이디 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, birthday);
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
	
	// 사용자 검색(비밀번호 초기화용 - 아이디 검색건수와 생년월일만 검색 후 생년월일 return(String))
	public String memberBirthday(Connection con, String id) {
		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		String SQL = "SELECT COUNT(아이디) AS 아이디, 생년월일 FROM 회원 WHERE 아이디='" + id + "'";
		String birthdayNew = null;
		
		try {
			// 1) Statement 객체 생성
			st = con.createStatement();
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			rs = st.executeQuery(SQL);
			
			// 3) ResultSet에 저장된 데이터 얻어오기
			int isSearch = -1;
			Date birthday = null;
			
			try {
				while(rs.next()) {
					isSearch = rs.getInt("아이디");
					birthday = rs.getDate("생년월일");
				}
				if(isSearch == 1) {	// 1이 아니면 return null
					SimpleDateFormat changeFormat = new SimpleDateFormat("yyMMdd");
					birthdayNew = changeFormat.format(birthday);	// String으로 반환..
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			System.out.println("사용자 검색 SQL 실행 중 오류!");
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
			
			if(st != null) {
				try {
					st.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return birthdayNew;
	}
	
	// 사용자 검색(조건: 아이디, 이름, 휴대폰, 이메일)
	public ResultSet memberSearchSQL(Connection con, JPanel p, int select, String searchWord) {
		Statement st = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		
		final String SQL_ID = "SELECT 아이디, 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, 남은시간 FROM 회원, 시간 WHERE 회원.아이디 = 시간.회원아이디 AND 아이디 LIKE '%" + searchWord + "%'";
		final String SQL_NAME = "SELECT 아이디, 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, 남은시간 FROM 회원, 시간 WHERE 회원.아이디 = 시간.회원아이디 AND 이름 LIKE '%" + searchWord + "%'";
		final String SQL_PHONE = "SELECT 아이디, 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, 남은시간 FROM 회원, 시간 WHERE 회원.아이디 = 시간.회원아이디 AND 휴대폰 LIKE '%" + searchWord + "%'";
		final String SQL_EMAIL = "SELECT 아이디, 이름, 휴대폰, 생년월일, 이메일, 회원가입일자, 최근로그인일자, 남은시간 FROM 회원, 시간 WHERE 회원.아이디 = 시간.회원아이디 AND 이메일 LIKE '%" + searchWord + "%'";
		String SQL = null;

		switch(select) {
			case 0: SQL = SQL_ID;
				break;
			case 1: SQL = SQL_NAME;
				break;
			case 2: SQL = SQL_PHONE;
				break;
			case 3: SQL = SQL_EMAIL;
				break;
			default: System.out.println("Switch문 에러!!");
				break;
		}
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			rs = st.executeQuery(SQL);

		} catch(Exception e) {
			System.out.println("사용자 검색 SQL 실행 중 오류!");
			e.printStackTrace();
		} finally {
			// 사용순서와 반대로 close
			// 여기껀 닫으면 진행안됨..
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
		}
		return rs;
	}
	
	// 회원 정보 수정
	public int memberInfoChange(Connection con, String id, String phone, Date birthday, String email) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE 회원 SET 휴대폰 = ?, 생년월일 = ?, 이메일 = ? WHERE 아이디 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, phone);
			pst.setDate(2, (java.sql.Date) birthday);	// 날짜 형식에 맞게 캐스팅 필요
			pst.setString(3, email);
			pst.setString(4, id);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("회원 정보 수정 SQL 실행 중 오류!");
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
	
	// 상품 전체 출력
	public DBManager productSelectSQL(Connection con) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT * FROM 상품 ORDER BY 분류";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("상품 전체 SQL 실행 중 오류!");
			e.printStackTrace();
		}
		return db;
	}
	
	// 상품 유통기한 있는 제품만, 유통기한 순으로 정렬후 출력
	public DBManager productSelectExpSQL(Connection con) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT * FROM 상품 WHERE 유통기한 IS NOT NULL ORDER BY 유통기한";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("유통기한 있는 상품 조회 SQL 실행 중 오류!");
			e.printStackTrace();
		}
		return db;
	}
	
	// 이용권이 아닌 제품을 낮은 수량순으로 조회
	public DBManager productAmountSelectSQL(Connection con) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT * FROM 상품 WHERE 분류 != '이용권' ORDER BY 수량";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("낮은 수량순 상품 조회 SQL 실행 중 오류!");
			e.printStackTrace();
		}
		return db;
	}
	
	// 상품 정보 수정
	public int productInfoChange(Connection con, String productName, int amount, int price, Date exp, String productID) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE 상품 SET 상품명 = ?, 수량 = ?, 단가 = ?, 유통기한 = ? WHERE 상품코드 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, productName);
			pst.setInt(2, amount);
			pst.setInt(3, price);
			pst.setDate(4, (java.sql.Date) exp);	// 날짜 형식에 맞게 캐스팅 필요
			pst.setString(5, productID);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("상품 정보 수정 SQL 실행 중 오류!");
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
	
	// 상품 삭제
	public int productDeleteSQL(Connection con, String productID) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "DELETE FROM 상품 WHERE 상품코드 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, productID);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("상품 삭제 SQL 실행 중 오류!");
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
	
	// (상품 삭제를 위한)상품 검색
	public DBManager productSelectSQL(Connection con, String productID) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT * FROM 상품 WHERE 상품코드 = '" + productID + "'";
		
		try {
			// 1) Statement 객체 생성
			db.st = con.createStatement();
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("(상품 삭제를 위한)상품 검색 SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 상품 추가
	public int productAddSQL(Connection con, String pGroup, String pID, String pName, int pAmount, int pPrice, Date pExp) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "INSERT INTO 상품 VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setString(1, pGroup);
			pst.setString(2, pID);
			pst.setString(3, pName);
			pst.setInt(4, pAmount);
			pst.setInt(5, pPrice);
			pst.setDate(6, (java.sql.Date) pExp);
			
			returnValue = pst.executeUpdate();

		} catch(java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "이미 존재하는 상품코드입니다!", "상품 추가", JOptionPane.ERROR_MESSAGE);
		} catch(Exception e) {
			System.out.println("상품 추가 SQL 실행 중 오류!");
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
	
	// 주문 Select SQL
	public DBManager orderSelectSQL(Connection con) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT 주문번호, 주문시간, 회원아이디, pc상태.pc번호, 상품명, 주문수량, 금액, 결제방식, 처리현황, 주문.메모 FROM 주문, pc상태, 상품 WHERE 주문.회원아이디 = pc상태.사용자ID AND 주문.상품코드 = 상품.상품코드 ORDER BY 주문시간";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("주문 Select SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
	
	// 주문 상태 변경
	public int orderStateChange(Connection con, int payment, String processing, String orderNum) {
		PreparedStatement pst = null;	// SQL문을 DB 서버로 보내기 위한 객체
		ResultSet rs = null;	// SQL 질의에 의해 생성된 테이블을 저장하는 객체
		int returnValue = 0;
		
		String SQL = "UPDATE 주문 SET 결제방식 = ?, 처리현황 = ? WHERE 주문번호 = ?";
		
		try {
			// 1) PreparedStatement 객체 생성, SQL 추가
			pst = con.prepareStatement(SQL);
			
			// 2) ? 매개변수에 값 지정
			pst.setInt(1, payment);
			pst.setString(2, processing);
			pst.setString(3, orderNum);
			
			returnValue = pst.executeUpdate();

		} catch(Exception e) {
			System.out.println("주문 상태 변경 SQL 실행 중 오류!");
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
	
	// 주문 Select_월별 검색 SQL
	public DBManager orderSelectMonthSQL(Connection con, String year, String month) {
		DBManager db = new DBManager();
		
		String SQL = "SELECT 주문번호, 주문시간, 회원아이디, pc상태.pc번호, 상품명, 주문수량, 금액, 결제방식, 처리현황, 주문.메모 FROM 주문, pc상태, 상품 WHERE 주문.회원아이디 = pc상태.사용자ID AND 주문.상품코드 = 상품.상품코드 AND 주문.주문시간 LIKE '" + year + "-" + month + "-%' ORDER BY 주문시간";
		
		try {
			// 1) Statement 객체 생성(열 개수 계산 시 에러 방지를 위한 파라미터 추가)
			db.st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// 2) Test SQL 문장 실행 후 결과 리턴
			db.rs = db.st.executeQuery(SQL);
		} catch(Exception e) {
			System.out.println("주문 Select_월별 검색 SQL 실행 중 오류!");
			//e.printStackTrace();
		}
		return db;
	}
}
