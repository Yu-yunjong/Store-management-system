package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

public class SignUpPanel extends JFrame {
	
	public SignUpPanel() {
		// panel
		JPanel p = new JPanel();
		
		// add
		add(p);
		
		signUp(p);
		
		//******** setting
		// 창 이름
		 setTitle("회원가입");		//super("회원가입");
		// 창 크기(너비, 높이)
		setSize(300, 400);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		setLocation(500, 300);
		// 창 크기 고정
		setResizable(false);
		// 종료 이벤트(종료 시 메모리에서도 사라지도록)
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void signUp(JPanel p) {
		p.setLayout(null);	// 배치관리자 없이 직접 배치
		
		// Label
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(20, 30, 80, 25);
		p.add(idLabel);
		
		JLabel pwLabel = new JLabel("비밀번호");
		pwLabel.setBounds(20, 60, 80, 25);
		p.add(pwLabel);
		
		JLabel pwChkLabel = new JLabel("비번 확인");
		pwChkLabel.setBounds(20, 90, 80, 25);
		p.add(pwChkLabel);
		
		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(20, 120, 80, 25);
		p.add(nameLabel);
		
		JLabel phoneLabel = new JLabel("휴대폰");
		phoneLabel.setBounds(20, 150, 80, 25);
		p.add(phoneLabel);
		
		JLabel birthdayLabel = new JLabel("생년월일");
		birthdayLabel.setBounds(20, 180, 80, 25);
		p.add(birthdayLabel);
		
		JLabel emailLabel = new JLabel("이메일");
		emailLabel.setBounds(20, 210, 80, 25);
		p.add(emailLabel);
		
		// textField
		JTextField idText = new JTextField(20);
		idText.setBounds(100, 30, 160, 25);
		p.add(idText);
		
		JPasswordField pwText = new JPasswordField(20);
		pwText.setBounds(100, 60, 160, 25);
		p.add(pwText);
		
		JPasswordField pwChkText = new JPasswordField(20);
		pwChkText.setBounds(100, 90, 160, 25);
		p.add(pwChkText);
		
		JTextField nameText = new JTextField(10);
		nameText.setBounds(100, 120, 160, 25);
		p.add(nameText);
		
		JTextField phoneText = new JTextField(13);
		phoneText.setBounds(100, 150, 160, 25);
		p.add(phoneText);
		
		JTextField emailText = new JTextField(50);
		emailText.setBounds(100, 210, 160, 25);
		p.add(emailText);
		
		// combobox
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		String [] year = new String[thisYear - 1929];
		int cnt = 0;	// index값
		for(int i = 1930; i <= thisYear; i++) {
			year[cnt++] = Integer.toString(i);
		}
		JComboBox yearCombo = new JComboBox(year);
		yearCombo.setBounds(100, 180, 60, 25);
		yearCombo.setSelectedItem(thisYear);
		p.add(yearCombo);
		
		String [] month = new String[12];
		for(int i = 1; i <= 12; i++)
			month[i - 1] = Integer.toString(i);
		JComboBox monthCombo = new JComboBox(month);
		monthCombo.setBounds(170, 180, 40, 25);
		p.add(monthCombo);
		
		String [] day = new String[31];
		for(int i = 1; i <= 31; i++)
			day[i - 1] = Integer.toString(i);
		JComboBox dayCombo = new JComboBox(day);
		dayCombo.setBounds(220, 180, 40, 25);
		p.add(dayCombo);
		
		// button
		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.setBounds(50, 300, 200, 25);
		
		btnSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date birthdayDate = null;
				int signUpSuccess = 0;	// 로그인 성공여부
				String pwStr = new String(pwText.getPassword());		// getPassword()를 통해 얻은 값은 char[] 형태이므로 String으로 변환
				String pwChkStr = new String(pwChkText.getPassword());	// getPassword()를 통해 얻은 값은 char[] 형태이므로 String으로 변환
				
				// 예외처리
				if(idText.getText().equals("") || pwStr.equals("") || pwChkStr.equals("") || nameText.getText().equals("") || phoneText.getText().equals("") || emailText.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "[빈 칸]이 존재합니다.\n확인 후 다시 가입해주시기 바랍니다.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
				}
				else if(!(pwStr.equals(pwChkStr))) {// 비밀번호와 비밀번호 확인 칸이 일치하지 않는 경우
					JOptionPane.showMessageDialog(null, "[비밀번호]와 [비번 확인] 값이 일치하지 않습니다.", "비밀번호 확인", JOptionPane.ERROR_MESSAGE);
				}
				else {
					// combobox에서 선택한 날짜 값 합치기
					String birthdayString = yearCombo.getSelectedItem().toString()
							+ "-" + monthCombo.getSelectedItem().toString()
							+ "-" + dayCombo.getSelectedItem().toString();
					
					// 날짜 형식으로 캐스팅(String -> Date)
					try {
						SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");// date형식으로 들어왔는지 유효성 검사를 위한 것
						dateFormat.setLenient(false);	// 날짜가 파싱될 때 조건을 허술하게 할지 말지 설정? ==> false: 엄격하게
						dateFormat.parse(birthdayString);
						birthdayDate = Date.valueOf(birthdayString);
					} catch (ParseException e1) {
						System.out.println("회원가입> 날짜 형식으로 변환 과정에서 오류 발생!!");
						e1.printStackTrace();
					}

					try {
						signUpSuccess = SQLExecute.signupSQL(idText.getText(), nameText.getText(), pwStr, phoneText.getText(), birthdayDate, emailText.getText());
						if(signUpSuccess == 1) {	// 회원가입에 완료한 경우
							JOptionPane.showMessageDialog(null, "저희 PC방에 회원으로 가입하신 것을 환영합니다.", "회원가입 완료", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("회원가입 성공!");
							dispose();	// 회원가입 창 닫기
						}
						else {	// 회원가입에 실패한 경우
							JOptionPane.showMessageDialog(null, "회원가입 실패!\n입력된 값이 잘못되지 않았는지 확인해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
							System.out.println("회원가입 실패");
						}
					} catch(java.sql.SQLIntegrityConstraintViolationException e1) {
						JOptionPane.showMessageDialog(null, "존재하는 아이디입니다.\n다른 아이디를 사용해주세요.", "회원가입", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e1) {
						System.out.println("회원가입 SQL 실행 중 오류!");
						e1.printStackTrace();
					}

				}
			}
		});
		p.add(btnSignUp);
		
		


	}
}
