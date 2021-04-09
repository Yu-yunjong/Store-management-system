package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Server.ServerMain;

public class LoginPanel extends JFrame{
	private JButton btnLogin;
	private JPasswordField pwText;
	private JTextField idText;
	
	LoginPanel() {
		// panel
		JPanel p = new JPanel();
		
		// add
		add(p);
		
		loginPanel(p);
		
		//******** setting
		// 창 이름
		 setTitle("사용자 로그인");		//super("로그인");
		// 창 크기(너비, 높이)
		setSize(300, 400);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		setLocation(500, 300);
		// 창 크기 고정
		setResizable(false);
		// 종료 이벤트(종료 시 메모리에서도 사라지도록)
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void loginPanel(JPanel p) {
		p.setLayout(null);	// 배치관리자 없이 직접 배치
		
		// Label
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(20, 80, 80, 25);
		p.add(idLabel);
		
		JLabel pwLabel = new JLabel("비밀번호");
		pwLabel.setBounds(20, 110, 80, 25);
		p.add(pwLabel);
		
		// textField
		idText = new JTextField(20);
		idText.setBounds(100, 80, 160, 25);
		p.add(idText);
		
		pwText = new JPasswordField(20);
		pwText.setBounds(100, 110, 160, 25);
		p.add(pwText);
		
		// button
		btnLogin = new JButton("로그인");
		btnLogin.setBounds(50, 180, 200, 25);
		p.add(btnLogin);
		btnLogin.addActionListener(event -> {
			String pwStr = new String(pwText.getPassword());	// getPassword()를 통해 얻은 값은 char[] 형태이므로 String으로 변환
			 int loginSuccess = SQLExecute.orderSelectMonthSQL(idText.getText(), pwStr);
//			int loginSuccess = SQLExecute.orderSelectMonthSQL("ak123", "dla0201");

			
			if(loginSuccess == 1) {	// 로그인에 성공한 경우
				JOptionPane.showMessageDialog(null, "로그인 성공!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("로그인 성공!");
				//setVisible(false);	// 로그인 창 가리기
				// 메인창 띄우기
			}
			else {	// 로그인에 실패한 경우
				JOptionPane.showMessageDialog(null, "로그인 실패!\n아이디 또는 비밀번호를 확인해 주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
				System.out.println("로그인 실패");
			}

		});
		
		// 회원가입 버튼
		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.setBounds(50, 210, 200, 25);
		
		btnSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SignUpPanel signup = new SignUpPanel();
			}
		});
		p.add(btnSignUp);
		
		// 아이디 찾기 버튼
		JButton btnFindID = new JButton("아이디/비밀번호 찾기");
		btnFindID.setBounds(50, 240, 200, 25);
		
		btnFindID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindUser find = new FindUser();
			}
		});
		p.add(btnFindID);
	}
}
