package Server;

import javax.swing.*;


@SuppressWarnings("serial")
public class ServerMain extends JFrame {
	public static void main(String[] args) {
		DBConnect db = new DBConnect();
		//ServerMain main = new ServerMain();
	}
	
	public ServerMain() {
		//******** setting
		// 창 이름
		super("PC방 관리 프로그램(서버)");
		// 창 크기(너비, 높이)
		setSize(1500, 1000);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		//setLocation(500, 300);
		// 창 크기 고정
		setResizable(false);
		// 종료 이벤트(종료 시 메모리에서도 사라지도록)
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// panel
		// 1: 메인, 2: 상품 관리, 3: 회원 관리, 4: 매출 관리, 5: PC 상태
		JPanel panel1 = new JPanel();
		mainPanel(panel1);
		
		JPanel panel2 = new JPanel();
		productPanel(panel2);
		
		JPanel panel3 = new JPanel();
		memberPanel(panel3);
		
		JPanel panel4 = new JPanel();
		salesPanel(panel4);
		
		JPanel panel5 = new JPanel();
		pcPanel(panel5);
		
		// add
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		
		// Tab
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(50, 50, 200, 200);
		tab.add("메인", panel1);
		tab.add("상품 관리", panel2);
		tab.add("회원 관리", panel3);
		tab.add("매출 관리", panel4);
		tab.add("PC 상태", panel5);
		add(tab);
		
		
	}
	// 메인
	public void mainPanel(JPanel p) {
		
	}
	
	// 상품 관리
	public void productPanel(JPanel p) {
		
	}
	
	// 회원 관리
	public void memberPanel(JPanel p) {
		
	}
	
	// 매출 관리
	public void salesPanel(JPanel p) {
		
	}
	
	// PC 상태
	public void pcPanel(JPanel p) {
		
	}
}