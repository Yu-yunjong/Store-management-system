package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ClientMain {
	// frame
	JFrame f = new JFrame("PC방 클라이언트");
	
	// 선언
	static String loginDate = null;

	static String pcNum = null;
	static JLabel useTimeText, timeText;
	private static String usrID = null;
	static Connection con = DBConnect.DBConnecting();
	
	// getter
	public static String getUsrID() {
		return usrID;
	}
	
	// 메인 함수
	public static void main(String[] args) {
		LoginPanel login = new LoginPanel();
	}
	
	ClientMain() {
		
	}
	
	ClientMain(String id) {
		JPanel p = new JPanel();
		p.setLayout(null);	// 배치관리자 없이
		
		f.add(p);
		loadMember(p, id);
		
		//******** setting	이부분 마지막에 써야 창이 바로 뜸...
		// 창 크기(너비, 높이)
		f.setSize(350, 320);
		// 창 보이기
		f.setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		f.setLocation(1500, 100);
//		f.setLocationRelativeTo(null);
		// 창 크기 고정
		f.setResizable(false);
		// 종료 이벤트(상단 X클릭 시 종료되지 않도록 설정)
		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //test용
		
		usrID = id;	// TimerThread로 넘기기 위해 사용자 아이디를 저장.
		
		TimerThread timer = new TimerThread();
		timer.start();
	}
	
	public void loadMember(JPanel p, String usrID) {
		DBManager db = SQLExecute.memberInfo_SQL(usrID);
		
		int time = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(db.rs.next()) {
				loginDate = db.rs.getString("최근로그인일자");
				pcNum = db.rs.getString("pc상태.pc번호");
				time = db.rs.getInt("남은시간");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Common.closeDB(db);
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		// Label
		JLabel pcNumLabel = new JLabel("PC번호: ");
		pcNumLabel.setBounds(10, 20, 50, 25);
		p.add(pcNumLabel);
		
		JLabel pcNumText = new JLabel(pcNum);
		pcNumText.setBounds(60, 20, 70, 25);
		p.add(pcNumText);
		
		JLabel idLabel = new JLabel("아이디: ");
		idLabel.setBounds(10, 50, 50, 25);
		p.add(idLabel);
		
		JLabel idText = new JLabel(usrID);
		idText.setBounds(60, 50, 140, 25);
		p.add(idText);
		
		JLabel useTimeLabel = new JLabel("사용 시간: ");
		useTimeLabel.setBounds(160, 20, 70, 25);
		p.add(useTimeLabel);
		
		useTimeText = new JLabel("00:00");
		useTimeText.setBounds(230, 20, 70, 25);
		p.add(useTimeText);
		
		JLabel timeLabel = new JLabel("남은 시간: ");
		timeLabel.setBounds(160, 50, 70, 25);
		p.add(timeLabel);
		
		timeText = new JLabel(timeDay(time) + "일 " + timeHour(time) + ":" + timeMin(time) + ":" + timeSec(time));
		timeText.setBounds(230, 50, 100, 25);
		p.add(timeText);
		
		// JButton
		// 회원정보 변경 버튼
		JButton btnMemberInfoChange = new JButton("회원정보 변경");
		btnMemberInfoChange.setBounds(100, 90, 150, 25);
		
		btnMemberInfoChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeMemberInfo info = new ChangeMemberInfo(usrID);
			}
		});
		p.add(btnMemberInfoChange);
		
		// 상품 주문 버튼
		JButton btnOrder = new JButton("상품 주문");
		btnOrder.setBounds(100, 130, 150, 25);
		
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Order order = new Order(usrID);
			}
		});
		p.add(btnOrder);
		
		// 관리자와 채팅 버튼
		JButton btnChatAdmin = new JButton("관리자와 채팅");
		btnChatAdmin.setBounds(100, 170, 150, 25);
		
		btnChatAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Chat chet = new Chat(usrID);
			}
		});
		p.add(btnChatAdmin);
		
		// 사용 종료 버튼
		JButton btnEnd = new JButton("사용 종료");
		btnEnd.setBounds(100, 210, 150, 25);
		
		btnEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int exitOption = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?\n예를 클릭하면 시간 저장 후 종료됩니다.", "사용 종료", JOptionPane.YES_NO_OPTION);
                
                // YES_OPTION: 0, NO_OPTION: 1, CLOSED_OPTION: -1을 반환
                if(exitOption == JOptionPane.YES_OPTION) {
                	SQLExecute.timeUpdate(usrID, TimerThread.getDbTime());// 시간 업데이트
                	SQLExecute.pcStateChange("", pcNum);	// pc 상태의 아이디 제거
                    System.exit(JFrame.EXIT_ON_CLOSE); // 프레임을 종료
                } else if ((exitOption == JOptionPane.NO_OPTION) || (exitOption == JOptionPane.CLOSED_OPTION )) {
                    return; // 아무 작업도 하지 않고 다이얼로그 상자를 닫음
                }
			}
		});
		p.add(btnEnd);
	}
	
	// 시간계산
	public static int timeDay(int sec) {
		return sec/(60*60*24); // 초 -> 일
	}
	
	public static int timeHour(int sec) {
		return (sec%(60*60*24))/(60*60); // 초 -> 시간
	}
	
	public static int timeMin(int sec) {
		return (sec%(60*60))/60; // 초 -> 분
	}
	
	public static int timeSec(int sec) {
		return sec%60; // 초 -> 초
	}
	
	static void reload() {
		DBManager db = SQLExecute.memberInfo_SQL(usrID);
		
		int time = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(db.rs.next()) {
				time = db.rs.getInt("남은시간");
				timeText.setText(timeDay(time) + "일 " + timeHour(time) + ":" + timeMin(time) + ":" + timeSec(time));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Common.closeDB(db);
			if(db.rs != null) {
				try {
					db.rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
