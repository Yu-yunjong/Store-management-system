package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.JButton;
import javax.swing.JPanel;

public class Button {
	public void btnMember(Connection con, JPanel p) {
		
		/************
		 * 회원관리 탭  *
		 ************/
		// 선언 및 배치
		JButton btnInquiry = new JButton("조회");
		btnInquiry.setBounds(1050, 10, 150, 25);
		JButton btnPwReset = new JButton("비밀번호 초기화");
		btnPwReset.setBounds(1050, 40, 150, 25);
		
		// 구현부
		btnInquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Common comm = new Common();	// 공통
				SQLExecute sql = new SQLExecute();
				ResultSet rs = sql.memberSelectSQL(con);
				
				int rowCount = comm.rowsCount(rs);
				MemberLoad ml = new MemberLoad(rs, p, rowCount);
				
				 //사용순서와 반대로 close
				if(rs != null) {
					try {
						rs.close();
					} catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnPwReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		// 삽입
		p.add(btnInquiry);
		p.add(btnPwReset);
	}
}
