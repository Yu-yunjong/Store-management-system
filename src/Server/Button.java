package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Button {
	public void btnMember(Connection con, JPanel p, MemberLoad ml, SQLExecute sql) {

		
		/************
		 * 회원관리 탭  *
		 ************/
		// 배치
		JButton btnInquiry = new JButton("조회(새로고침)");
		btnInquiry.setBounds(1025, 10, 150, 25);
		JButton btnPwReset = new JButton("비밀번호 초기화");
		btnPwReset.setBounds(1025, 40, 150, 25);
		
		// 구현부
		btnInquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SQLExecute sql = new SQLExecute();
				ResultSet rs = sql.memberSelectSQL(con);
				
				ml.memberLoad(sql, p, con, "조회");
				
				 //사용순서와 반대로 close
				if(rs != null) {
					try {
						rs.close();
					} catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
				ml.setCheck_btnMemberInfoChange(0);// 회원정보 수정 버튼 동작하지 않도록 변경.
			}
		});
		
		btnPwReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				String q1 = "비밀번호 초기화를 진행할 사용자 ID를 입력해주세요.\n(비밀번호는 등록된 생년월일 6자리로 초기화됩니다.)\n초기화된 비밀번호 예시: 980101";
				String id = JOptionPane.showInputDialog(q1);
				String birthday = sql.memberBirthday(con, id);

				if(!(id == null)) {	// null: 팝업창을 x눌러 닫은 경우
					// 조건: 생일이 null(아이디가 존재하지 않는 경우)가 아닐 때 ==> 아이디가 존재하는경우
					if(!(birthday == null)) {
						String q2 = "사용자 ID: " + id + "\n생년월일: " + birthday + "\n입력하신 사용자의 비밀번호가 생년월일로 초기화됩니다.\n 계속하시겠습니까?";
						// result = 0: 예 / 1: 아니오 / -1: x를 눌러 창 닫은 경우
						int result = JOptionPane.showConfirmDialog(null, q2, "비밀번호 초기화", JOptionPane.YES_NO_OPTION);
						
						if(result == 0) {
							int chk = sql.memberPwResetSQL(con, id, birthday);
							if(chk == 1)	// 1행의 값이 변경되었으면
								JOptionPane.showMessageDialog(null, "비밀번호 초기화 완료!");
						}
					} else if(birthday == null) {
						JOptionPane.showMessageDialog(null, "입력하신 아이디가 존재하지 않습니다.");
					}
				}
				
			}
		});
		
		// 삽입
		p.add(btnInquiry);
		p.add(btnPwReset);
	}
}
