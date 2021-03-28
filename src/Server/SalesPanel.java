package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SalesPanel {
	// 분류별, 회원별, 상품별, 결제방식별 매출(처리 완료된 건만)
	// 선언부
	JTable table;
	DefaultTableModel model;
	private String[][] dataArray;
	
	JTable table_UsrID;
	DefaultTableModel modelUsrID;
	private String[][] dataArray_UsrID;
	
	JTable table_PName;
	DefaultTableModel modelPName;
	private String[][] dataArray_PName;
	
	JTable table_PayPlan;
	DefaultTableModel modelPayPlan;
	private String[][] dataArray_PayPlan;
	
	// setter
	public void setDataArray(String[][] dataArray) {
		this.dataArray = dataArray;
	}
	public void setModelUsrID(DefaultTableModel modelUsrID) {
		this.modelUsrID = modelUsrID;
	}
	public void setTable_PName(JTable table_PName) {
		this.table_PName = table_PName;
	}
	public void setTable_PayPlan(JTable table_PayPlan) {
		this.table_PayPlan = table_PayPlan;
	}
	public void setDataArray_PayPlan(String[][] dataArray_PayPlan) {
		this.dataArray_PayPlan = dataArray_PayPlan;
	}
	public void setDataArray_PName(String[][] dataArray_PName) {
		this.dataArray_PName = dataArray_PName;
	}
	public void setDataArray_UsrID(String[][] dataArray_UsrID) {
		this.dataArray_UsrID = dataArray_UsrID;
	}
	
	Common comm = new Common();
	
	SalesPanel(Connection con, JPanel p, SQLExecute sql){
		// table 추가
		String column[] = {"주문연도-월", "월별 총 매출"};	// 열 이름
		
		model = new DefaultTableModel(dataArray, column) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);	// 스크롤 바 붙이기
		sp.setBounds(0, 80, 250, 650);	// 크기 설정
		p.add(sp);
		
		// 위의 테이블 클릭하면  값 변경되도록 이벤트 설정
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String date = table.getValueAt(table.getSelectedRow(), 0).toString();
				salesTableLoad(con, p, sql, "회원별조회", date);
				salesTableLoad(con, p, sql, "상품별조회", date);
				salesTableLoad(con, p, sql, "결제방식별조회", date);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// 사용x 지우면 err
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// 사용x 지우면 err
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// 사용x 지우면 err
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 사용x 지우면 err
			}
		});
		
		// 회원아이디별 table 추가
		String columnUsrID[] = {"주문연도-월", "회원아이디", "총 매출"};	// 열 이름
		
		modelUsrID = new DefaultTableModel(dataArray_UsrID, columnUsrID) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table_UsrID = new JTable(modelUsrID);
		JScrollPane sp1 = new JScrollPane(table_UsrID);	// 스크롤 바 붙이기
		sp1.setBounds(260, 80, 250, 650);	// 크기 설정
		p.add(sp1);
		
		// 상품명별 table 추가
		String columnPName[] = {"주문연도-월", "상품명", "총 매출"};	// 열 이름
		
		modelPName = new DefaultTableModel(dataArray_PName, columnPName) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table_PName = new JTable(modelPName);
		JScrollPane sp2 = new JScrollPane(table_PName);	// 스크롤 바 붙이기
		sp2.setBounds(520, 80, 250, 650);	// 크기 설정
		p.add(sp2);
		
		// 결제방식별 table 추가
		String columnPayPlan[] = {"주문연도-월", "결제방식", "총 매출"};	// 열 이름
		
		modelPayPlan = new DefaultTableModel(dataArray_PayPlan, columnPayPlan) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table_PayPlan = new JTable(modelPayPlan);
		JScrollPane sp3 = new JScrollPane(table_PayPlan);	// 스크롤 바 붙이기
		sp3.setBounds(780, 80, 250, 650);	// 크기 설정
		p.add(sp3);
		
		salesBtn(con, p, sql);
		salesTableLoad(con, p, sql, "조회", "");
		salesTableLoad(con, p, sql, "회원별조회", "");
		salesTableLoad(con, p, sql, "상품별조회", "");
		salesTableLoad(con, p, sql, "결제방식별조회", "");
	}
	
	// 메인 table 조회 - 마지막 date는 필요한 경우(조회할 날짜가 있는 경우)만 삽입
	private void salesTableLoad(Connection con, JPanel p, SQLExecute sql, String 조건, String date) {
		ResultSet rs = null;
		DBManager db = null;
		switch(조건) {
			case "조회": 
				db = sql.salesMonthSelectSQL(con);
				rs = db.rs;
				
				int rowCount = comm.rowsCount(rs);
				String[][] data = new String[rowCount][10];
				String column[] = {"주문연도-월", "월별 총 매출"};	// 열 이름
				int dataCount = 0;
				
				// ResultSet에 저장된 데이터 얻어오기
				try {
					while(rs.next()) {
						data[dataCount][0] = rs.getString("주문일자");
						data[dataCount][1] = rs.getString("SUM(주문수량*금액)");
						dataCount++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				setDataArray(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

				model.setDataVector(dataArray, column);
				table.setModel(model);	// table 업데이트
				break;
			case "회원별조회": 
				db = sql.salesSelectSQL(con, date, "회원아이디");
				rs = db.rs;
				salesMemberTableLoad(con, p, rs);
				break;
			case "상품별조회": 
				db = sql.salesSelectSQL(con, date, "상품명");
				rs = db.rs;
				salesProductTableLoad(con, p, rs);
				break;
			case "결제방식별조회": 
				db = sql.salesSelectSQL(con, date, "결제방식");
				rs = db.rs;
				salesPayPlanTableLoad(con, p, rs);
				break;
			default:
				System.out.println("salesTableLoad 오류!!");
				break;
		}
		

			comm.closeDB(db);
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}

	}
	
	// 회원별 table 조회
	private void salesMemberTableLoad(Connection con, JPanel p, ResultSet rs) {
		int rowCount = comm.rowsCount(rs);
		String[][] data = new String[rowCount][10];
		String column[] = {"회원아이디", "총 매출"};	// 열 이름
		int dataCount = 0;
		
		// ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("회원아이디");
				data[dataCount][1] = rs.getString("총금액");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDataArray_UsrID(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		modelUsrID.setDataVector(dataArray_UsrID, column);
		table_UsrID.setModel(modelUsrID);	// table 업데이트
	}
	
	// 상품별 table 조회
	private void salesProductTableLoad(Connection con, JPanel p, ResultSet rs) {
		int rowCount = comm.rowsCount(rs);
		String[][] data = new String[rowCount][10];
		String column[] = {"상품명", "총 매출"};	// 열 이름
		int dataCount = 0;
		
		// ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("상품명");
				data[dataCount][1] = rs.getString("총금액");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDataArray_PName(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		modelPName.setDataVector(dataArray_PName, column);
		table_PName.setModel(modelPName);	// table 업데이트
	}
	
	// 결제방식별 table 조회
	private void salesPayPlanTableLoad(Connection con, JPanel p, ResultSet rs) {
		int rowCount = comm.rowsCount(rs);
		String[][] data = new String[rowCount][10];
		String column[] = {"결제방식", "총 매출"};	// 열 이름
		int dataCount = 0;
		
		// ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				if(rs.getInt("결제방식") == 0)
					data[dataCount][0] = "현금";
				else
					data[dataCount][0] = "카드";
				data[dataCount][1] = rs.getString("총금액");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setDataArray_PayPlan(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		modelPayPlan.setDataVector(dataArray_PayPlan, column);
		table_PayPlan.setModel(modelPayPlan);	// table 업데이트
	}
	
	private void salesBtn(Connection con, JPanel p, SQLExecute sql) {
		// 전체조회(새로고침) 버튼
		JButton btnTableLoad = new JButton("전체조회");
		btnTableLoad.setBounds(10, 10, 100, 25);
		
		btnTableLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salesTableLoad(con, p, sql, "조회", "");
				salesTableLoad(con, p, sql, "회원별조회", "");
				salesTableLoad(con, p, sql, "상품별조회", "");
				salesTableLoad(con, p, sql, "결제방식별조회", "");
			}
		});
		p.add(btnTableLoad);
		
		// 설명
		JLabel explainLabel = new JLabel("사용방법 - 월별 항목을 보기 위해서는 [월별 매출] 테이블의 값을 선택해주세요.");
		explainLabel.setBounds(140, 10, 500, 30);
		p.add(explainLabel);
		
		// 전체 상품
		JLabel MonthLabel = new JLabel("< 월별 매출 >");
		MonthLabel.setBounds(10, 50, 80, 30);
		p.add(MonthLabel);
		
		JLabel memberLabel = new JLabel("< 회원별 매출 >");
		memberLabel.setBounds(270, 50, 120, 30);
		p.add(memberLabel);
		
		JLabel productLabel = new JLabel("< 상품별 매출 >");
		productLabel.setBounds(530, 50, 120, 30);
		p.add(productLabel);
		
		JLabel payPlanLabel = new JLabel("< 결제방식별 매출 >");
		payPlanLabel.setBounds(790, 50, 120, 30);
		p.add(payPlanLabel);
	}
}
