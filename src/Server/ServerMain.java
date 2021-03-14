package Server;

import javax.swing.*;
import java.sql.*;



public class ServerMain {
	// frame
	JFrame f = new JFrame("PC방 관리 프로그램(서버)");
	
	public static void main(String[] args) {
		DBConnect db = new DBConnect();
		
		
	}
	public ServerMain() {
		
	}
	
	public ServerMain(Connection con) {
		// panel
		// 1: 메인, 2: 상품 관리, 3: 회원 관리, 4: 매출 관리, 5: PC 상태
		// 메인 패널
		JPanel panel1 = new JPanel();
		mainPanel(panel1, con);
		
		// 상품 관리 패널
		JPanel panel2 = new JPanel();
		productPanel(panel2, con);
		
		// 회원 관리 패널
		JPanel panel3 = new JPanel();
		memberPanel(panel3, con);
		//f.setLayout(null);
		//f.setBounds(0, 0, 1700, 1000);
		
		
		// 매출 관리 패널
		JPanel panel4 = new JPanel();
		//salesPanel(panel4);
		
		// PC상태 패널
		JPanel panel5 = new JPanel();
		//pcPanel(panel5);
		
		// add
//		f.add(panel1);
//		f.add(panel2);
//		f.add(panel3);
//		f.add(panel4);
//		f.add(panel5);
		
		// Tab
		JTabbedPane tab = new JTabbedPane();
		//tab.setBounds(50, 50, 200, 200);
		tab.add("메인", panel1);
		tab.add("상품 관리", panel2);
		tab.add("회원 관리", panel3);
		tab.add("매출 관리", panel4);
		tab.add("PC 상태", panel5);
		f.add(tab);
		
		//******** setting	이부분 마지막에 써야 창이 바로 뜸...
		// 창 이름
		//super("PC방 관리 프로그램(서버)");
		// 창 크기(너비, 높이)
		f.setSize(1200, 800);
		// 창 보이기
		f.setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		//setLocation(500, 300);
		// 창 크기 고정
		f.setResizable(false);
		// 종료 이벤트(종료 시 메모리에서도 사라지도록)
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setLocationRelativeTo(null);
	}
	// 메인
	public void mainPanel(JPanel p, Connection con) {
		p.setLayout(null);	// 배치관리자 없이
		SQLExecute sql = new SQLExecute();
		MainPanel main = new MainPanel(con, p, sql);
	}
	
	// 상품 관리
	public void productPanel(JPanel p, Connection con) {
		p.setLayout(null);	// 배치관리자 없이
		SQLExecute sql = new SQLExecute();
		Product product = new Product(con, p, sql);
		//product.productTableLoad(sql, p, con, "조회");
		
	}
	
	// 회원 관리
	public void memberPanel(JPanel p, Connection con) {
		p.setLayout(null);	// 배치관리자 없이
		Button btn = new Button();
		SQLExecute sql = new SQLExecute();
		ResultSet rs = sql.memberSelectSQL(con);
		
		MemberLoad ml = new MemberLoad(sql, p, con);
		ml.memberLoad(sql, p, con, "조회");
		ml.memberSearch(p, con, sql);
		ml.memberInfoChange(p, con);
		
		 //사용순서와 반대로 close (테스트중)
//		if(rs != null) {
//			try {
//				rs.close();
//			} catch(SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//		
//			if(sql.st != null) {
//			try {
//				sql.st.close();
//			} catch(SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
		btn.btnMember(con, p, ml, sql);
		
		
		
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