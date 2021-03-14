package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainPanel {
	// 선언부
	JTable table;
	DefaultTableModel model;
	private String[][] dataArray;
	private int Check_btnOrderInfoChange = 0;
	JLabel seatNumLabel1;
	JLabel productNameLabel1;
	JLabel orderAmountLabel1;
	JLabel orderPriceLabel1;
	JLabel orderNumberLabel1;
	JLabel orderDatetimeLabel1;
	JLabel orderMemberIDLabel1;
	JComboBox paymentOptionCombo, processingCombo;
	JComboBox yearCombo, monthCombo;
	JTextArea memoTextArea;
	private String orderNum;
	
	//setter
	public void setDataArray(String[][] dataArray) {
		this.dataArray = dataArray;
	}
	public void setCheck_btnOrderInfoChange(int check_btnOrderInfoChange) {
		Check_btnOrderInfoChange = check_btnOrderInfoChange;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	Common comm = new Common();
	
	MainPanel(Connection con, JPanel p, SQLExecute sql){
		// table 추가
		String column[] = {"주문번호", "주문시간", "회원 ID", "좌석번호", "상품명", "주문수량", "결제금액", "결제방식", "처리현황", "메모"};	// 열 이름
		
		model = new DefaultTableModel(dataArray, column) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);	// 스크롤 바 붙이기
		sp.setBounds(0, 40, 800, 400);;	// 크기 설정
		p.add(sp);
		
		// 위의 테이블 클릭하면  값 변경되도록 이벤트 설정
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seatNumLabel1.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
				productNameLabel1.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
				orderAmountLabel1.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
				orderPriceLabel1.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
				paymentOptionCombo.setSelectedItem(table.getValueAt(table.getSelectedRow(), 7));
				processingCombo.setSelectedItem(table.getValueAt(table.getSelectedRow(), 8));
				try {
					memoTextArea.setText(table.getValueAt(table.getSelectedRow(), 9).toString());
				} catch(java.lang.NullPointerException e1) {
					System.out.println("메모 칸이 null값임.");
				}
				setOrderNum(table.getValueAt(table.getSelectedRow(), 0).toString());
				orderNumberLabel1.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				orderDatetimeLabel1.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				orderMemberIDLabel1.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
				setCheck_btnOrderInfoChange(1);
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
		
		orderTableLoad(con, p, sql, "조회");
		orderLoad(p, sql, con);
		orderBtnLoad(p, sql, con);
	}
	
	private void orderTableLoad(Connection con, JPanel p, SQLExecute sql, String 조건) {
		ResultSet rs = null;
		DBManager db = null;
		switch(조건) {
			case "조회": 
				db = sql.orderSelectSQL(con);
				rs = db.rs;
				break;
			case "월별조회": 
				db = sql.orderSelectMonthSQL(con, yearCombo.getSelectedItem().toString(), monthCombo.getSelectedItem().toString());
				rs = db.rs;
				break;
			default:
				System.out.println("orderTableLoad 오류!!");
				break;
		}
		
		int rowCount = comm.rowsCount(rs);
		
		String[][] data = new String[rowCount][10];
		
		String column[] = {"주문번호", "주문시간", "회원 ID", "좌석번호", "상품명", "주문수량", "결제금액", "결제방식", "처리현황", "메모"};	// 열 이름
		
		int dataCount = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("주문번호");
				data[dataCount][1] = rs.getString("주문시간");
				data[dataCount][2] = rs.getString("회원아이디");
				data[dataCount][3] = rs.getString("pc번호");
				data[dataCount][4] = rs.getString("상품명");
				data[dataCount][5] = rs.getString("주문수량");
				data[dataCount][6] = rs.getString("금액");
				if(rs.getInt("결제방식") == 0)
					data[dataCount][7] = "현금";
				else
					data[dataCount][7] = "카드";
				data[dataCount][8] = rs.getString("처리현황");
				data[dataCount][9] = rs.getString("메모");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			comm.closeDB(db);
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		setDataArray(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		model.setDataVector(dataArray, column);
		table.setModel(model);	// table 업데이트
		// 각 셀의 너비
//		table.getColumn("분류").setPreferredWidth(3);
//		table.getColumn("상품코드").setPreferredWidth(7);
//		table.getColumn("수량").setPreferredWidth(7);
//		table.getColumn("단가").setPreferredWidth(7);
//		table.getColumn("유통기한").setPreferredWidth(10);
		
		setCheck_btnOrderInfoChange(0);// 회원수정 버튼이 동작 불가하도록 변경
	}
	
	private void orderLoad(JPanel p, SQLExecute sql, Connection con) {
		// Label
		JLabel orderLabel = new JLabel("< 주문내역 >");
		orderLabel.setBounds(10, 10, 90, 25);
		p.add(orderLabel);
		
		JLabel orderDetailLabel = new JLabel("< 주문내역 상세보기 >");
		orderDetailLabel.setBounds(10, 450, 150, 25);
		p.add(orderDetailLabel);
		
		JLabel seatNumLabel = new JLabel("좌석번호");
		seatNumLabel.setBounds(10, 480, 70, 25);
		p.add(seatNumLabel);
		
		seatNumLabel1 = new JLabel("(null)");
		seatNumLabel1.setBounds(70, 480, 70, 25);
		p.add(seatNumLabel1);
		
		JLabel productNameLabel = new JLabel("상  품  명");
		productNameLabel.setBounds(10, 510, 70, 25);
		p.add(productNameLabel);
		
		productNameLabel1 = new JLabel("(null)");
		productNameLabel1.setBounds(70, 510, 300, 25);
		p.add(productNameLabel1);
		
		JLabel orderAmountLabel = new JLabel("주문수량");
		orderAmountLabel.setBounds(10, 540, 70, 25);
		p.add(orderAmountLabel);
		
		orderAmountLabel1 = new JLabel("(null)");
		orderAmountLabel1.setBounds(70, 540, 70, 25);
		p.add(orderAmountLabel1);
		
		JLabel orderPriceLabel = new JLabel("결제금액");
		orderPriceLabel.setBounds(10, 570, 70, 25);
		p.add(orderPriceLabel);
		
		orderPriceLabel1 = new JLabel("(null)");
		orderPriceLabel1.setBounds(70, 570, 70, 25);
		p.add(orderPriceLabel1);
		
		JLabel paymentOptionLabel = new JLabel("결제방법");
		paymentOptionLabel.setBounds(10, 600, 70, 25);
		p.add(paymentOptionLabel);
		
		JLabel processingLabel = new JLabel("처리현황");
		processingLabel.setBounds(10, 630, 70, 25);
		p.add(processingLabel);
		
		JLabel memoLabel = new JLabel("메        모");
		memoLabel.setBounds(10, 660, 70, 25);
		p.add(memoLabel);
		
		JLabel orderNumberLabel = new JLabel("주문번호");
		orderNumberLabel.setBounds(170, 480, 70, 25);
		p.add(orderNumberLabel);
		
		orderNumberLabel1 = new JLabel("(null)");
		orderNumberLabel1.setBounds(230, 480, 150, 25);
		p.add(orderNumberLabel1);
		
		JLabel orderDatetimeLabel = new JLabel("주문시간");
		orderDatetimeLabel.setBounds(170, 540, 70, 25);
		p.add(orderDatetimeLabel);
		
		orderDatetimeLabel1 = new JLabel("(null)");
		orderDatetimeLabel1.setBounds(230, 540, 150, 25);
		p.add(orderDatetimeLabel1);
		
		JLabel orderMemberIDLabel = new JLabel("회원 ID");
		orderMemberIDLabel.setBounds(170, 570, 70, 25);
		p.add(orderMemberIDLabel);
		
		orderMemberIDLabel1 = new JLabel("(null)");
		orderMemberIDLabel1.setBounds(230, 570, 150, 25);
		p.add(orderMemberIDLabel1);
		
		// ComboBox
		String [] select = {"현금", "카드"};
		paymentOptionCombo = new JComboBox(select);
		paymentOptionCombo.setBounds(70, 600, 100, 25);
		p.add(paymentOptionCombo);
		
		String [] select1 = {"주문", "준비중", "처리완료", "주문취소"};
		processingCombo = new JComboBox(select1);
		processingCombo.setBounds(70, 630, 100, 25);
		p.add(processingCombo);
		
		// TextArea
		memoTextArea = new JTextArea();
		memoTextArea.setBounds(70, 660, 250, 65);
		memoTextArea.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		memoTextArea.setEditable(false);
		p.add(memoTextArea);
		
		// JButton
		JButton btnStateChange = new JButton("상태 변경");
		btnStateChange.setBounds(180, 600, 140, 25);
		
		btnStateChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Check_btnOrderInfoChange == 0)
					JOptionPane.showMessageDialog(null, "상단 테이블에서 변경할 항목을 선택해주세요.", "주문 상태 변경", JOptionPane.ERROR_MESSAGE);
				else {
					int chk = sql.orderStateChange(con, paymentOptionCombo.getSelectedIndex(), processingCombo.getSelectedItem().toString(), orderNum);
					if(chk == 1) {// 1개 행의 값이 변경(update)되었으면
						JOptionPane.showMessageDialog(null, "주문 상태 변경 완료!", "주문 상태 변경", 1);
						orderTableLoad(con, p, sql, "조회");// 상단 테이블 다시 로드
						setCheck_btnOrderInfoChange(0);// 주문 상태 변경 버튼 눌리지 않도록 변경
					}
					else {
						JOptionPane.showMessageDialog(null, "SQL 처리 중 오류 발생! 콘솔창을 확인해주세요.", "주문 상태 변경", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		p.add(btnStateChange);
	}
	
	private void orderBtnLoad(JPanel p, SQLExecute sql, Connection con) {
		// 조회(새로고침) 버튼
		JButton btnTableLoad = new JButton("전체조회");
		btnTableLoad.setBounds(130, 10, 100, 25);
		
		btnTableLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orderTableLoad(con, p, sql, "조회");
			}
		});
		p.add(btnTableLoad);
		
		
		// 월별 조회 기능
		// ComboBox
		int thisYear = Calendar.getInstance().get(Calendar.YEAR) + 3;	// 현재 연도보다 3년 후까지 표기
		String [] year = new String[thisYear - 2019];
		int cnt = 0;	// index값
		for(int i = 2020; i <= thisYear; i++) {
			year[cnt++] = Integer.toString(i);
		}
		yearCombo = new JComboBox(year);
		yearCombo.setBounds(550, 10, 60, 25);
		p.add(yearCombo);
		yearCombo.setSelectedIndex(Calendar.getInstance().get(Calendar.YEAR) - 2020); // 올해 연도로 값 변경
		
		String [] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		monthCombo = new JComboBox(month);
		monthCombo.setBounds(620, 10, 60, 25);
		p.add(monthCombo);
		monthCombo.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH)); // 이번달로 값 변경(월은 -1을 해야 하므로, 아래 인덱스 값과 동일함.)
		
		// button
		JButton btnStateChange = new JButton("월별 조회");
		btnStateChange.setBounds(700, 10, 100, 25);
		
		btnStateChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orderTableLoad(con, p, sql, "월별조회");
			}
		});
		p.add(btnStateChange);
	}
}
