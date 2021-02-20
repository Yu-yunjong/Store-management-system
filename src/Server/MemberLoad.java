package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MemberLoad {
	MemberLoad(ResultSet rs, JPanel p, int rowCount) {
		// table 추가
		String[][] data = new String[rowCount][8];
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
		
		String column[] = {"아이디", "이름", "휴대폰", "생년월일", "이메일", "회원가입", "최근로그인", "남은시간(단위:분)"};	// 열 이름
		
		DefaultTableModel model = new DefaultTableModel(data, column);
		JTable table = new JTable(model);
		
		// 셀 간격 조정
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		
		// 각 셀의 너비와 정렬
		p.setLayout(null);	// 배치관리자 없이
		table.getColumn("아이디").setPreferredWidth(10);
		JScrollPane sp = new JScrollPane(table);	// 스크롤 바 붙이기
		sp.setSize(1000, 500);	// 크기 설정
		//sp.setPreferredSize(new Dimension(1000, 500));	// 크기 설정(안쓰는게 좋을듯)

		p.add(sp);
		
		// table 끝 ===========================================================================
	}
	
	public void memberSearch(JPanel p) {
		/**********
		 * 회원 검색 *
		 **********/
		// Label
		JLabel memberSearchLabel = new JLabel("< 회원 검색 >");
		memberSearchLabel.setBounds(10, 510, 80, 25);
		p.add(memberSearchLabel);
		
		// ComboBox
		String [] select = {"아이디", "이름", "휴대폰", "이메일"};
		JComboBox combo = new JComboBox(select);

		combo.setBounds(10, 530, 100, 25);
		p.add(combo);
		
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox)e.getSource();
				int index = comboBox.getSelectedIndex();
				System.out.println(index);
			}
		});
		
		// TextField
		JTextField searchText = new JTextField(20);
		searchText.setBounds(120, 530, 150, 25);
		p.add(searchText);
	}
}
