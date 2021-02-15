package Server;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.BorderLayout;
import java.sql.*;



public class ServerMain extends JFrame {
	// frame
	JFrame f = new JFrame();
	
	public static void main(String[] args) {
		DBConnect db = new DBConnect();
		
		
	}
	public ServerMain() {
		
	}
	
	public ServerMain(Connection con) {
		//******** setting
		// 창 이름
		super("PC방 관리 프로그램(서버)");
		// 창 크기(너비, 높이)
		f.setSize(1500, 1000);
		// 창 보이기
		f.setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		//setLocation(500, 300);
		// 창 크기 고정
		f.setResizable(false);
		// 종료 이벤트(종료 시 메모리에서도 사라지도록)
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		
		// panel
		// 1: 메인, 2: 상품 관리, 3: 회원 관리, 4: 매출 관리, 5: PC 상태
		// 메인 패널
		JPanel panel1 = new JPanel();
		//mainPanel(panel1);
		
		// 상품 관리 패널
		JPanel panel2 = new JPanel();
		//productPanel(panel2);
		
		// 회원 관리 패널
		JPanel panel3 = new JPanel();
		panel3.add(memberPanel(panel3, con));
		
		// 매출 관리 패널
		JPanel panel4 = new JPanel();
		//salesPanel(panel4);
		
		// PC상태 패널
		JPanel panel5 = new JPanel();
		//pcPanel(panel5);
		
		// add
		f.add(panel1);
		f.add(panel2);
		f.add(panel3);
		f.add(panel4);
		f.add(panel5);
		
		
		
		// Tab
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(50, 50, 200, 200);
		tab.add("메인", panel1);
		tab.add("상품 관리", panel2);
		tab.add("회원 관리", panel3);
		tab.add("매출 관리", panel4);
		tab.add("PC 상태", panel5);
		f.add(tab);
		
		
	}
	// 메인
	public void mainPanel(JPanel p) {
		
	}
	
	// 상품 관리
	public void productPanel(JPanel p) {
		
	}
	
	// 회원 관리
	public JTable memberPanel(JPanel p, Connection con) {
		ResultSet rs;
		SQLExecute sql = new SQLExecute();
		rs = sql.memberSelectSQL(con);
		
		// rs 행의 개수 계산
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
		

		String[][] data = new String[rowCount][8];
		int dataCount = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("아이디");
				data[dataCount][1] = rs.getString("이름");
				data[dataCount][2] = rs.getString("휴대폰");
				data[dataCount][3] = rs.getString("생년월일");
				data[dataCount][4] = rs.getString("이메일");
				data[dataCount][5] = rs.getString("회원가입일자");
				data[dataCount][6] = rs.getString("최근로그인일자");
				data[dataCount][7] = rs.getString("남은시간");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String column[] = {"아이디", "이름", "휴대폰", "생년월일", "이메일", "회원가입", "최근로그인", "남은시간(단위:분)"};	// 열 이름
		
		JTable table = new JTable(data, column);
		table.setCellSelectionEnabled(true);
		ListSelectionModel select = table.getSelectionModel();	// 리스트 모델
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	// 하나의 셀만 선택
		
		select.addListSelectionListener(new ListSelectionListener() {	// 리스너 붙이기
			public void valueChanged(ListSelectionEvent e) {
				String Data = null;
				int[] row = table.getSelectedRows();
				int[] columns = table.getSelectedColumns();
				
				for(int i = 0; i < row.length; i++) {	// 데이터 삽입
					for(int j = 0; j < columns.length; j++) {
						Data = (String)table.getValueAt(row[i], columns[j]);
					}
				}
				// 선택 결과 보이기
				JOptionPane.showMessageDialog(p, "선택된 테이블은 " + Data + " 입니다.");
			}
		});
		JScrollPane scroll = new JScrollPane(table);	// 스크롤 바 붙이기
		p.add(scroll, BorderLayout.CENTER);
		
		return table;
	}
	
	// 매출 관리
	public void salesPanel(JPanel p) {
		
	}
	
	// PC 상태
	public void pcPanel(JPanel p) {
		
	}
}

class JPanel01 {

}