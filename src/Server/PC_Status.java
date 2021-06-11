package Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PC_Status extends JFrame{
	private JLayeredPane lpane = new JLayeredPane();
	
	// 선언부
	JTextArea pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8, pc9, pc10, 
	pc11, pc12, pc13, pc14, pc15, pc16, pc17, pc18, pc19, pc20;
	
	PC_Status(Connection con, JPanel p){
		loadPCStatus(p);
		pcStatus(con, p);
		renew(con, p);
		updatePCStatus(con, p);
	}
	
	public void pcStatus(Connection con, JPanel p) {
		DBManager db = new DBManager();
		db = SQLExecute.selectPCStatus(con);
		int rowsCount = Common.rowsCount(db.rs);
		
		String pcNum[] = new String[rowsCount];
		int pcStatus[] = new int[rowsCount];
		String memo[] = new String[rowsCount];
		String userID[] = new String[rowsCount];
		String input[] = new String[rowsCount];
		String pcStatusStr[] = new String[rowsCount];
		
		try {
			int i = 0;
			while(db.rs.next()) {
				pcNum[i] = db.rs.getString("PC번호");
				pcStatus[i] = db.rs.getInt("PC상태");
				if(pcStatus[i] == 1)
					pcStatusStr[i] = "정상";
				else
					pcStatusStr[i] = "고장";
				memo[i] = db.rs.getString("메모");
				userID[i] = db.rs.getString("사용자ID");
				input[i] = "       < PC" + pcNum[i] + " >\nPC상태: " + pcStatusStr[i] + 
						"\n메모: " + memo[i] + "\n사용자ID: " + userID[i];
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Common.closeDB(db);
		
		pc1.setText(input[0]);
		pc2.setText(input[1]);
		pc3.setText(input[2]);
		pc4.setText(input[3]);
		pc5.setText(input[4]);
		pc6.setText(input[5]);
		pc7.setText(input[6]);
		pc8.setText(input[7]);
		pc9.setText(input[8]);
		pc10.setText(input[9]);
		pc11.setText(input[10]);
		pc12.setText(input[11]);
		pc13.setText(input[12]);
		pc14.setText(input[13]);
		pc15.setText(input[14]);
		pc16.setText(input[15]);
		pc17.setText(input[16]);
		pc18.setText(input[17]);
		pc19.setText(input[18]);
		pc20.setText(input[19]);
	}
	
	public void loadPCStatus(JPanel p) {

		// 폰트
		Font font = new Font("돋움", Font.BOLD, 15);
		
		// TextArea
		pc1 = new JTextArea();
		pc1.setBounds(70, 70, 150, 120);
		pc1.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc1.setEditable(false);
		pc1.setFont(font);
		p.add(pc1);
		
		pc2 = new JTextArea();
		pc2.setBounds(250, 70, 150, 120);
		pc2.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc2.setEditable(false);
		pc2.setFont(font);
		p.add(pc2);
		
		pc3 = new JTextArea();
		pc3.setBounds(430, 70, 150, 120);
		pc3.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc3.setEditable(false);
		pc3.setFont(font);
		p.add(pc3);
		
		pc4 = new JTextArea();
		pc4.setBounds(610, 70, 150, 120);
		pc4.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc4.setEditable(false);
		pc4.setFont(font);
		p.add(pc4);
		
		pc5 = new JTextArea();
		pc5.setBounds(790, 70, 150, 120);
		pc5.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc5.setEditable(false);
		pc5.setFont(font);
		p.add(pc5);
		
		pc6 = new JTextArea();
		pc6.setBounds(970, 70, 150, 120);
		pc6.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc6.setEditable(false);
		pc6.setFont(font);
		p.add(pc6);
		
		pc7 = new JTextArea();
		pc7.setBounds(70, 250, 150, 120);
		pc7.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc7.setEditable(false);
		pc7.setFont(font);
		p.add(pc7);
		
		pc8 = new JTextArea();
		pc8.setBounds(250, 250, 150, 120);
		pc8.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc8.setEditable(false);
		pc8.setFont(font);
		p.add(pc8);
		
		pc9 = new JTextArea();
		pc9.setBounds(430, 250, 150, 120);
		pc9.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc9.setEditable(false);
		pc9.setFont(font);
		p.add(pc9);
		
		pc10 = new JTextArea();
		pc10.setBounds(610, 250, 150, 120);
		pc10.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc10.setEditable(false);
		pc10.setFont(font);
		p.add(pc10);
		
		pc11 = new JTextArea();
		pc11.setBounds(790, 250, 150, 120);
		pc11.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc11.setEditable(false);
		pc11.setFont(font);
		p.add(pc11);
		
		pc12 = new JTextArea();
		pc12.setBounds(970, 250, 150, 120);
		pc12.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc12.setEditable(false);
		pc12.setFont(font);
		p.add(pc12);
		
		pc13 = new JTextArea();
		pc13.setBounds(70, 430, 150, 120);
		pc13.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc13.setEditable(false);
		pc13.setFont(font);
		p.add(pc13);
		
		pc14 = new JTextArea();
		pc14.setBounds(250, 430, 150, 120);
		pc14.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc14.setEditable(false);
		pc14.setFont(font);
		p.add(pc14);
		
		pc15 = new JTextArea();
		pc15.setBounds(430, 430, 150, 120);
		pc15.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc15.setEditable(false);
		pc15.setFont(font);
		p.add(pc15);
		
		pc16 = new JTextArea();
		pc16.setBounds(610, 430, 150, 120);
		pc16.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc16.setEditable(false);
		pc16.setFont(font);
		p.add(pc16);
		
		pc17 = new JTextArea();
		pc17.setBounds(790, 430, 150, 120);
		pc17.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc17.setEditable(false);
		pc17.setFont(font);
		p.add(pc17);
		
		pc18 = new JTextArea();
		pc18.setBounds(970, 430, 150, 120);
		pc18.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc18.setEditable(false);
		pc18.setFont(font);
		p.add(pc18);
		
		pc19 = new JTextArea();
		pc19.setBounds(70, 610, 150, 120);
		pc19.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc19.setEditable(false);
		pc19.setFont(font);
		p.add(pc19);
		
		pc20 = new JTextArea();
		pc20.setBounds(250, 610, 150, 120);
		pc20.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		pc20.setEditable(false);
		pc20.setFont(font);
		p.add(pc20);
	}
	
	public void renew(Connection con, JPanel p) {
		JButton btnRenew = new JButton("새로고침");
		btnRenew.setBounds(60, 20, 150, 25);
		
		btnRenew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pcStatus(con, p);
			}
		});
		p.add(btnRenew);
	}
	
	public void updatePCStatus(Connection con, JPanel p) {
		// JComboBox
		String [] status = { "고장", "정상" };
		
		JComboBox pcStatusCombo = new JComboBox(status);
		pcStatusCombo.setBounds(460, 20, 80, 25);
		p.add(pcStatusCombo);
		
		String [] pcNumArr = new String[20];
		for(int i = 0; i < 20; i++)
			pcNumArr[i] = Integer.toString(i+1);

		JComboBox pcNumCombo = new JComboBox(pcNumArr);
		pcNumCombo.setBounds(380, 20, 80, 25);
		p.add(pcNumCombo);
		
		// JTextArea
		JTextArea memo = new JTextArea();
		memo.setBounds(600, 20, 150, 25);
		memo.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		p.add(memo);
		
		// 버튼
		JButton btnRenew = new JButton("업데이트");
		btnRenew.setBounds(800, 20, 150, 25);
		
		btnRenew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int cnt = 0;
				
				if(memo.getText().toString() == "") {
					cnt = SQLExecute.pcStateChange(con, pcStatusCombo.getSelectedIndex(), null, pcNumCombo.getSelectedItem().toString());
				} else {
					cnt = SQLExecute.pcStateChange(con, pcStatusCombo.getSelectedIndex(), memo.getText().toString(), pcNumCombo.getSelectedItem().toString());
				}
				
				if(cnt == 1) {
					JOptionPane.showMessageDialog(null, "PC상태 변경 완료!", "PC상태 변경", 1);
					pcStatus(con, p);// 새로고침
				} else {
					JOptionPane.showMessageDialog(null, "PC상태 변경 실패!", "PC상태 변경", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		p.add(btnRenew);
		
		
	}
}
