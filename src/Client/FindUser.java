package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

public class FindUser extends JFrame {
	public FindUser() {
		// panel
		JPanel idPanel = new JPanel();
		JPanel pwPanel = new JPanel();
		
		// Tab
		JTabbedPane tab = new JTabbedPane();
		//tab.setBounds(50, 50, 200, 200);
		tab.add("아이디 찾기", idPanel);
		tab.add("비밀번호 찾기", pwPanel);
		add(tab);
		
		findID(idPanel);
		findPW(pwPanel);
		
		//******** setting
		// 창 이름
		 setTitle("아이디/비밀번호 찾기");
		// 창 크기(너비, 높이)
		setSize(300, 400);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		setLocation(500, 300);
		// 창 크기 고정
		setResizable(false);
	}
	
	// 아이디 찾기
	private void findID(JPanel p) {
		p.setLayout(null);	// 배치관리자 없이 직접 배치
		
		// Label
		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(20, 80, 80, 25);
		p.add(nameLabel);
		
		// textField
		JTextField nameText = new JTextField(20);
		nameText.setBounds(100, 80, 160, 25);
		p.add(nameText);
		
		JTextField phoneOrEmailText = new JTextField(20);
		phoneOrEmailText.setBounds(100, 110, 160, 25);
		p.add(phoneOrEmailText);
		
		// ComboBox
		String [] select = {"휴대폰", "이메일"};
		JComboBox combo = new JComboBox(select);
		combo.setBounds(20, 110, 70, 25);
		p.add(combo);
		
		// button
		// 아이디 조회 버튼
		JButton btnFindID = new JButton("아이디 조회");
		btnFindID.setBounds(50, 270, 200, 25);
		
		btnFindID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBManager db = new DBManager();
				db = SQLExecute.findID_SQL(nameText.getText(), phoneOrEmailText.getText(), combo.getSelectedItem().toString());
				int rowsCount = Common.rowsCount(db.rs);	// 행 개수 반환
				
				if(rowsCount == 0) {
					JOptionPane.showMessageDialog(null, "해당하는 정보와 일치하는 아이디가 없습니다.", "아이디 찾기", JOptionPane.ERROR_MESSAGE);
					System.out.println("아이디 찾기 실패.. " + nameText.getText() + "\t" + phoneOrEmailText.getText());
				}
				else {
					String searchID = null;
					// ResultSet에 저장된 데이터 얻어오기
					try {
						while(db.rs.next()) {
							searchID = db.rs.getString("아이디");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 
					JOptionPane.showMessageDialog(null, "<조회된 ID>\n  " + searchID, "아이디 찾기", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("성공적으로 아이디를 찾음! " + nameText.getText() + "\t" + phoneOrEmailText.getText() + "\t" + searchID);
				}
				Common.closeDB(db);
			}
		});
		p.add(btnFindID);
	}
	
	// 비밀번호 찾기
	private void findPW(JPanel p) {
		p.setLayout(null);	// 배치관리자 없이 직접 배치
		
		// Label
		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(20, 80, 80, 25);
		p.add(nameLabel);
		
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(20, 110, 80, 25);
		p.add(idLabel);
		
		// textField
		JTextField nameText = new JTextField(20);
		nameText.setBounds(100, 80, 160, 25);
		p.add(nameText);
		
		JTextField idText = new JTextField(20);
		idText.setBounds(100, 110, 160, 25);
		p.add(idText);
		
		JTextField phoneOrEmailText = new JTextField(20);
		phoneOrEmailText.setBounds(100, 140, 160, 25);
		p.add(phoneOrEmailText);
		
		// ComboBox
		String [] select = {"휴대폰", "이메일"};
		JComboBox combo = new JComboBox(select);
		combo.setBounds(20, 140, 70, 25);
		p.add(combo);
		
		// button
		// 비밀번호 조회 버튼
		JButton btnFindPW = new JButton("비밀번호 조회");
		btnFindPW.setBounds(50, 270, 200, 25);
		
		btnFindPW.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBManager db = new DBManager();
				db = SQLExecute.findPW_SQL(nameText.getText(), idText.getText(), phoneOrEmailText.getText(), combo.getSelectedItem().toString());
				int rowsCount = Common.rowsCount(db.rs);	// 행 개수 반환
				
				if(rowsCount == 0) {
					JOptionPane.showMessageDialog(null, "해당하는 정보와 일치하는 회원이 없습니다.", "비밀번호 찾기", JOptionPane.ERROR_MESSAGE);
					System.out.println("비밀번호 찾기 실패.. " + nameText.getText() + "\t" + idText.getText() + "\t" + phoneOrEmailText.getText());
				}
				else {
					String searchPW = null;
					// ResultSet에 저장된 데이터 얻어오기
					try {
						while(db.rs.next()) {
							searchPW = db.rs.getString("비밀번호");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 
					JOptionPane.showMessageDialog(null, "<조회된 비밀번호>\n  " + searchPW, "비밀번호 찾기", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("성공적으로 비밀번호를 찾음! " + nameText.getText() + "\t" + idText.getText() + "\t" + phoneOrEmailText.getText() + "\t" + searchPW);
				}
				Common.closeDB(db);
			}
		});
		p.add(btnFindPW);
	}
}
