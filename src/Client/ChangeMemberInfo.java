package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangeMemberInfo extends JFrame {
	String name = null, phone = null, email = null, signupDate = null, loginDate = null, pcNum = null;
	Date birthday = null;
	int time = 0;
	
	ChangeMemberInfo(String id){
		// panel
		JPanel p = new JPanel();
		
		// add
		add(p);
		memberInfoLoad(p, id);
		
		//******** setting
		// 창 이름
		setTitle("회원정보 변경");
		// 창 크기(너비, 높이)
		setSize(300, 400);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		setLocation(1500, 200);
		// 창 크기 고정
		setResizable(false);
	}
	
	public void memberInfoLoad(JPanel p, String id) {
		p.setLayout(null);	// 배치관리자 없이 직접 배치
		loadMyInfo(id);
		
		// Label
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(20, 30, 80, 25);
		p.add(idLabel);
		
		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(20, 60, 80, 25);
		p.add(nameLabel);
		
		JLabel birthdayLabel = new JLabel("생일");
		birthdayLabel.setBounds(20, 90, 80, 25);
		p.add(birthdayLabel);
		
		JLabel phoneLabel = new JLabel("휴대폰");
		phoneLabel.setBounds(20, 120, 80, 25);
		p.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("이메일");
		emailLabel.setBounds(20, 150, 80, 25);
		p.add(emailLabel);
		
		JLabel signupDateLabel = new JLabel("회원가입날짜");
		signupDateLabel.setBounds(20, 180, 80, 25);
		p.add(signupDateLabel);
		
		JLabel loginDayLabel = new JLabel("최근 로그인");
		loginDayLabel.setBounds(20, 210, 80, 25);
		p.add(loginDayLabel);
		
		JLabel pcNumLabel = new JLabel("접속중인 PC");
		pcNumLabel.setBounds(20, 240, 80, 25);
		p.add(pcNumLabel);
		
		// textField
		JTextField idText = new JTextField(20);
		idText.setBounds(100, 30, 160, 25);
		idText.setText(id);
		idText.setEditable(false);
		p.add(idText);
		
		JTextField nameText = new JTextField(10);
		nameText.setBounds(100, 60, 160, 25);
		nameText.setText(name);
		nameText.setEditable(false);
		p.add(nameText);
		
		JTextField phoneText = new JTextField(50);
		phoneText.setBounds(100, 120, 160, 25);
		phoneText.setText(phone);
		p.add(phoneText);
		
		JTextField emailText = new JTextField(50);
		emailText.setBounds(100, 150, 160, 25);
		emailText.setText(email);
		p.add(emailText);
		
		JTextField signupDateText = new JTextField(20);
		signupDateText.setBounds(100, 180, 160, 25);
		signupDateText.setText(signupDate);
		signupDateText.setEditable(false);
		p.add(signupDateText);
		
		JTextField loginDateText = new JTextField(20);
		loginDateText.setBounds(100, 210, 160, 25);
		loginDateText.setText(loginDate);
		loginDateText.setEditable(false);
		p.add(loginDateText);
		
		JTextField pcNumText = new JTextField(6);
		pcNumText.setBounds(100, 240, 160, 25);
		pcNumText.setText(pcNum);
		pcNumText.setEditable(false);
		p.add(pcNumText);
		
		// combobox
		// 날짜 출력
		Calendar cal = Calendar.getInstance();
		cal.clear();// 캘린더 초기화
		cal.setTime(birthday);// 생일 일자로 설정
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH) + 1;
		int day1 = cal.get(Calendar.DATE);
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		String [] year = new String[thisYear-1919];
		int cnt = 0;	// index값
		for(int i = 1920; i <= thisYear; i++) {
			year[cnt++] = Integer.toString(i);
		}
		JComboBox yearCombo = new JComboBox(year);
		yearCombo.setBounds(100, 90, 60, 25);
		yearCombo.setSelectedItem(Integer.toString(year1));// 생일 연도로 설정
		p.add(yearCombo);
		
		String [] month = new String[12];
		for(int i = 1; i <= 12; i++)
			month[i - 1] = Integer.toString(i);
		JComboBox monthCombo = new JComboBox(month);
		monthCombo.setBounds(170, 90, 40, 25);
		monthCombo.setSelectedItem(Integer.toString(month1));// 생일 달로 설정
		p.add(monthCombo);
		
		String [] day = new String[31];
		for(int i = 1; i <= 31; i++)
			day[i - 1] = Integer.toString(i);
		JComboBox dayCombo = new JComboBox(day);
		dayCombo.setBounds(220, 90, 40, 25);
		dayCombo.setSelectedItem(Integer.toString(day1));// 생일 일자로 설정
		p.add(dayCombo);
		
		// button
		// 회원정보 변경 버튼
		JButton btnChangeInfo = new JButton("회원정보 변경");
		btnChangeInfo.setBounds(100, 270, 150, 25);
		
		btnChangeInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 날짜 형식으로 변환
				Date changeBirthday = Date.valueOf(yearCombo.getSelectedItem().toString() + 
						"-" + monthCombo.getSelectedItem().toString() + 
						"-" + dayCombo.getSelectedItem().toString());
				
				String message = "회원정보를 다음과 같이 변경하시겠습니까?\n(변경사항이 있는 항목만 표기됩니다.)\n< 기존 >\n";
				String message1 = "";// 변경된 회원정보 저장
				int chkValue = 0;	// 변경될 값이 있는지 확인
				
				// 휴대폰 정보가 [변경 전]과 [변경 후]가 같지 않을 경우(변경된 경우)
				if(phone.length() > 13) {	// 휴대폰 번호가 13자리보다 클 경우
					JOptionPane.showMessageDialog(null, "휴대폰 번호가 잘못 입력되었습니다. (13자 초과)", "회원정보 수정", JOptionPane.ERROR_MESSAGE);
				}
				else if(!phoneText.getText().toString().equals(phone)) {
					message = message + "  - 휴대폰: " + phone;
					message1 = message1 + "\n  - 휴대폰: " + phoneText.getText().toString();
					chkValue++;
				}
				
				// 생년월일 정보가 [변경 전]과 [변경 후]가 같지 않을 경우
				if(!birthday.toString().equals(changeBirthday.toString())) {
					message = message + "\n  - 생년월일: " + birthday;
					message1 = message1 + "\n  - 생년월일: " + changeBirthday.toString();
					chkValue++;
				}
				
				// 이메일 정보가 [변경 전]과 [변경 후]가 같지 않을 경우
				if(!emailText.getText().toString().equals(email)) {
					message = message + "\n  - 이메일: " + email;
					message1 = message1 + "\n  - 이메일: " + emailText.getText().toString();
					chkValue++;
				}
				message = message + "\n\n< 변경 >";
				message = message + message1;
				// result - 0: 예 / 1: 아니오 / -1: x버튼으로 닫기

				if(chkValue == 0) {
					JOptionPane.showMessageDialog(null, "[변경할 값]이 [기존 값]과 동일합니다. 값을 변경해주세요.", "회원정보 수정", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int result = JOptionPane.showConfirmDialog(null, message, "회원정보 변경", JOptionPane.YES_NO_OPTION);
					if(result == 0) {
						int chk = SQLExecute.memberInfoChange(id, phoneText.getText().toString(), changeBirthday, emailText.getText().toString());
						if(chk == 1) {// 1개 행의 값이 변경(update)되었으면
							JOptionPane.showMessageDialog(null, "회원정보 변경 완료!", "회원정보 변경", 1);
							setVisible(false);
							loadMyInfo(id);// SQL로부터 다시 불러오기
						}
						else {
							JOptionPane.showMessageDialog(null, "SQL 처리 중 오류 발생! 콘솔창을 확인해주세요.", "회원정보 수정", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		p.add(btnChangeInfo);
	}
	
	public void loadMyInfo(String usrID) {
		DBManager db = SQLExecute.memberInfo_SQL(usrID);
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(db.rs.next()) {
				name = db.rs.getString("이름");
				phone = db.rs.getString("휴대폰");
				birthday = db.rs.getDate("생년월일");
				email = db.rs.getString("이메일");
				signupDate = db.rs.getString("회원가입일자");
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
		
	}
}
