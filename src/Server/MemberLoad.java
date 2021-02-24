package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MemberLoad {
	// 선언부
	Common comm = new Common();
	private String[][] dataArray = null;
	private String[][] memberChangeArray = null;
	private String[][] memberInfoArray = null;
	private int dataArrayRow;
	private JComboBox combo;
	private JTextField searchText;
	JTable memberChangeTable;
	JTable memberChangeInfoTable;
	JTable table;
	private int check_btnMemberInfoChange = 0;
	
	// setter
	public void setDataArray(String[][] dataArray) {
		this.dataArray = dataArray;
	}
	
	public void setDataArrayRow(int dataArrayRow) {
		this.dataArrayRow = dataArrayRow;
	}
	
	public void setMemberChangeArray(String[][] memberChangeArray) {
		this.memberChangeArray = memberChangeArray;
	}
	
	public void setMemberInfoArray(String[][] memberInfoArray) {
		this.memberInfoArray = memberInfoArray;
	}
	
	public void setCheck_btnMemberInfoChange(int check_btnMemberInfoChange) {
		this.check_btnMemberInfoChange = check_btnMemberInfoChange;
	}

MemberLoad(SQLExecute sql, JPanel p, Connection con){
	// table 추가
	String column[] = {"아이디", "이름", "휴대폰", "생년월일", "이메일", "회원가입", "최근로그인", "남은시간(단위:초)"};	// 열 이름
	
	DefaultTableModel model = new DefaultTableModel(dataArray, column) {
		// 셀 내용 더블클릭 후 수정 금지
		public boolean isCellEditable(int i, int c) {
			return false;
			}
	};
	table = new JTable(model);
	
	memberLoad(sql, p, con, "조회");
	
	// 위의 테이블 클릭하면 아래의 테이블의 값 변경되도록 이벤트 설정
	table.addMouseListener(new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			setDataArrayRow(table.getSelectedRow());
			
			String[][] data = new String[1][3];
			int cnt = 0;
			for(int i = 2; i < 5; i++) {
				data[0][cnt] = dataArray[dataArrayRow][i];
				cnt++;
			}
			setMemberChangeArray(data);
			
			String[][] memberInfo = new String[1][2];
			cnt = 0;
			for(int i = 0; i < 2; i++) {
				memberInfo[0][i] = dataArray[dataArrayRow][i];
				cnt++;
			}
			setMemberInfoArray(memberInfo);

			String column[] = {"휴대폰", "생년월일", "이메일"};	// 열 이름
			DefaultTableModel model1 = new DefaultTableModel(memberChangeArray, column);
			memberChangeTable.setModel(model1);
			
			String memberInfoColumn[] = {"아이디", "이름"};	// 열 이름
			DefaultTableModel model2 = new DefaultTableModel(memberInfoArray, memberInfoColumn) {
				// 셀 내용 더블클릭 후 수정 금지
				public boolean isCellEditable(int i, int c) {
					return false;
					}
			};
			memberChangeInfoTable.setModel(model2);
			setCheck_btnMemberInfoChange(1);// 회원수정 버튼이 동작 가능하도록 변경
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// 사용x 지우면 err
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// 사용x 지우면 err
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 사용x 지우면 err
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 사용x 지우면 err
		}
	});
	
	// 셀 간격 조정
	DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
	celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
	
	DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
	celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
	
	// 각 셀의 너비와 정렬
	table.getColumn("아이디").setPreferredWidth(10);
	JScrollPane sp = new JScrollPane(table);	// 스크롤 바 붙이기
	sp.setSize(1000, 500);	// 크기 설정

	p.add(sp);
}

	public void memberLoad(SQLExecute sql, JPanel p, Connection con, String 조건) {
		ResultSet rs = null;
		switch(조건) {
			case "조회": rs = sql.memberSelectSQL(con);
				break;
			case "검색": rs = sql.memberSearchSQL(con, p, combo.getSelectedIndex(), searchText.getText());
				break;
			default:
				System.out.println("memberLoad 오류!!");
				break;
		}
		
		int rowCount = comm.rowsCount(rs);
		
		String[][] data = new String[rowCount][8];
		
		String column[] = {"아이디", "이름", "휴대폰", "생년월일", "이메일", "회원가입", "최근로그인", "남은시간(단위:초)"};	// 열 이름
		
		int dataCount = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("아이디");
				data[dataCount][1] = rs.getString("이름");
				data[dataCount][2] = rs.getString("휴대폰");
				data[dataCount][3] = rs.getString("생년월일");
				data[dataCount][4] = rs.getString("이메일");
				data[dataCount][5] = rs.getString("회원가입일자");
				data[dataCount][6] = rs.getString("최근로그인일자");
				data[dataCount][7] = rs.getString("남은시간");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setDataArray(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		DefaultTableModel model = new DefaultTableModel(dataArray, column) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table.setModel(model);	// table 업데이트
	}

	
	public void memberSearch(JPanel p, Connection con, SQLExecute sql) {
		/**********
		 * 회원 검색 *
		 **********/
		// Label
		JLabel memberSearchLabel = new JLabel("< 회원 검색 >");
		memberSearchLabel.setBounds(10, 505, 80, 25);
		p.add(memberSearchLabel);
		
		// TextField
		searchText = new JTextField(20);
		searchText.setBounds(120, 530, 150, 25);
		p.add(searchText);
		
		// ComboBox
		String [] select = {"아이디", "이름", "휴대폰", "이메일"};
		combo = new JComboBox(select);

		combo.setBounds(10, 530, 100, 25);
		p.add(combo);
		
		// JButton
		JButton btnUsrSearch = new JButton("회원 검색");
		btnUsrSearch.setBounds(280, 530, 150, 25);
		
		p.add(btnUsrSearch);
		btnUsrSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memberLoad(sql, p, con, "검색");
				setCheck_btnMemberInfoChange(0);// 회원수정 버튼이 동작 불가능하도록 변경
			}
		});
		
	}
	
	public void memberInfoChange(JPanel p, Connection con) {
		/*************
		 * 회원정보 수정 *
		 *************/
		SQLExecute sql = new SQLExecute();

		
		// Label
		JLabel memberInfoChangeLabel = new JLabel("< 회원정보 변경 > - 상단 테이블에서 선택 후 아래 테이블에서 더블클릭하여 변경 후 [엔터], [회원정보 변경]버튼 클릭");
		memberInfoChangeLabel.setBounds(10, 590, 920, 25);
		p.add(memberInfoChangeLabel);
		
		// table
		// 회원정보 테이블 생성
		String memberInfoColumn[] = {"아이디", "이름"};	// 열 이름
		
		DefaultTableModel memberChangeModel = new DefaultTableModel(memberInfoArray, memberInfoColumn);
		memberChangeInfoTable = new JTable(memberChangeModel);
		
		// 회원정보 수정 테이블 생성
		String column[] = {"휴대폰", "생년월일", "이메일"};	// 열 이름
		
		DefaultTableModel model = new DefaultTableModel(memberChangeArray, column);
		memberChangeTable = new JTable(model);
		
		// 셀 간격 조정
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		
		// 각 셀의 너비와 정렬
		JScrollPane sp = new JScrollPane(memberChangeTable);	// 스크롤 바 붙이기
		sp.setBounds(310, 620, 500, 40);
		
		JScrollPane sp1 = new JScrollPane(memberChangeInfoTable);	// 스크롤 바 붙이기
		sp1.setBounds(10, 620, 300, 40);

		p.add(sp);
		p.add(sp1);

		// JButton
		JButton btnUsrInfoChange = new JButton("회원정보 변경");
		btnUsrInfoChange.setBounds(10, 670, 150, 25);
		
		btnUsrInfoChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check_btnMemberInfoChange == 0) {
					JOptionPane.showMessageDialog(null, "상단 테이블에서 변경할 회원을 선택해주세요.", "회원정보 수정", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String phone = memberChangeTable.getValueAt(0, 0).toString();
					String email = memberChangeTable.getValueAt(0, 2).toString();
					// 생일을 String -> Date 형식으로 캐스팅
					String sBirthday = memberChangeTable.getValueAt(0, 1).toString();
					Date birthday = Date.valueOf(sBirthday);
					
					String id = memberChangeInfoTable.getValueAt(0, 0).toString();
					String message = "회원정보를 다음과 같이 변경하시겠습니까?\n< 기존 >\n  - 휴대폰: " + table.getValueAt(table.getSelectedRow(), 2)
									+ "\n  - 생년월일: " + table.getValueAt(table.getSelectedRow(), 3)
									+ "\n  - 이메일: " + table.getValueAt(table.getSelectedRow(), 4)
									+ "\n\n< 변경 >"
									+ "\n  - 휴대폰: " + phone
									+ "\n  - 생년월일: " + birthday
									+ "\n  - 이메일: " + email;
					// result - 0: 예 / 1: 아니오 / -1: x버튼으로 닫기
					int result = JOptionPane.showConfirmDialog(null, message, "회원정보 변경", JOptionPane.YES_NO_OPTION);
					if(result == 0) {
						sql.memberInfoChange(con, id, phone, birthday, email);
						JOptionPane.showMessageDialog(null, "회원정보 변경 완료!", "회원정보 변경", 1);
					}
				}
			}
		});
		p.add(btnUsrInfoChange);
	}
	

}
