package Server;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Product {
	// 선언부
	JTable table;
	JTable productChangeInfoTable;
	JTable productChangeTable;
	private String[][] dataArray;
	private String[][] productInfoArray;
	private String[][] productChangeArray;
	private int dataArrayRow;
	private String[][] ProductUpdateArray;
	DefaultTableModel model;
	private int Check_btnProductInfoChange = 0;
	
	Common comm = new Common();
	
	// setter
	public void setDataArray(String[][] dataArray) {
		this.dataArray = dataArray;
	}
	public void setDataArrayRow(int dataArrayRow) {
		this.dataArrayRow = dataArrayRow;
	}
	public void setProductUpdateArray(String[][] productUpdateArray) {
		ProductUpdateArray = productUpdateArray;
	}
	public void setProductInfoArray(String[][] productInfoArray) {
		this.productInfoArray = productInfoArray;
	}
	public void setProductChangeArray(String[][] productChangeArray) {
		this.productChangeArray = productChangeArray;
	}
	public void setCheck_btnProductInfoChange(int check_btnProductInfoChange) {
		Check_btnProductInfoChange = check_btnProductInfoChange;
	}
	
	Product(Connection con, JPanel p, SQLExecute sql){
		// table 추가
		String column[] = {"분류", "상품코드", "상품명", "수량", "단가", "유통기한"};	// 열 이름
		
		model = new DefaultTableModel(dataArray, column) {
			// 셀 내용 더블클릭 후 수정 금지
			public boolean isCellEditable(int i, int c) {
				return false;
				}
		};
		table = new JTable(model);
		
		// 위의 테이블 클릭하면 아래의 테이블의 값 변경되도록 이벤트 설정
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setDataArrayRow(table.getSelectedRow());
				
				String[][] data = new String[1][6];
				int cnt = 0;
				for(int i = 2; i < 6; i++) {
					data[0][cnt] = dataArray[dataArrayRow][i];
					cnt++;
				}
				//setProductUpdateArray(data);
				setProductChangeArray(data);
				String[][] productInfo = new String[1][2];
				cnt = 0;
				for(int i = 0; i < 2; i++) {
					productInfo[0][i] = dataArray[dataArrayRow][i];
					cnt++;
				}
				setProductInfoArray(productInfo);


				// 변경불가능 table
				String column[] = {"분류", "상품코드"};	// 열 이름
				DefaultTableModel model1 = new DefaultTableModel(productInfoArray, column) {
					// 셀 내용 더블클릭 후 수정 금지
					public boolean isCellEditable(int i, int c) {
						return false;
						}
				};
				productChangeInfoTable.setModel(model1);
				
				// 변경가능 table
				String productInfoColumn[] = {"상품명", "수량", "단가", "유통기한"};	// 열 이름
				DefaultTableModel model2 = new DefaultTableModel(productChangeArray, productInfoColumn);
				productChangeTable.setModel(model2);
				setCheck_btnProductInfoChange(1);// 회원수정 버튼이 동작 가능하도록 변경
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
		
		JScrollPane sp = new JScrollPane(table);	// 스크롤 바 붙이기
		sp.setBounds(0, 0, 1000, 500);;	// 크기 설정

		p.add(sp);
		productTableLoad(sql, p, con, "조회");
		buttonLoad(con, p, sql);
		productInfoChange(p, con, sql);
		
		productDelete(p, con, sql);
		productAdd(p, con, sql);
	}
	
	public void productTableLoad(SQLExecute sql, JPanel p, Connection con, String 조건) {
		ResultSet rs = null;
		DBManager db = null;
		switch(조건) {
			case "조회": 
				db = sql.productSelectSQL(con);
				rs = db.rs;
				break;
			case "유통기한조회":
				db = sql.productSelectExpSQL(con);
				rs = db.rs;
				break;
			case "수량순조회":
				db = sql.productAmountSelectSQL(con);
				rs = db.rs;
				break;
			default:
				System.out.println("productTableLoad 오류!!");
				break;
		}
		
		int rowCount = comm.rowsCount(rs);
		
		String[][] data = new String[rowCount][7];
		
		String column[] = {"분류", "상품코드", "상품명", "수량", "단가", "유통기한"};	// 열 이름
		
		int dataCount = 0;
		
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			while(rs.next()) {
				data[dataCount][0] = rs.getString("분류");
				data[dataCount][1] = rs.getString("상품코드");
				data[dataCount][2] = rs.getString("상품명");
				data[dataCount][3] = rs.getString("수량");
				data[dataCount][4] = rs.getString("단가");
				data[dataCount][5] = rs.getString("유통기한");
				dataCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			comm.closeDB(db);
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		setDataArray(data);	// 데이터를 setter을 이용하여 클래스단에 있는 dataArray 배열에 저장.

		model.setDataVector(dataArray, column);
		table.setModel(model);	// table 업데이트
		// 각 셀의 너비
		table.getColumn("분류").setPreferredWidth(3);
		table.getColumn("상품코드").setPreferredWidth(7);
		table.getColumn("수량").setPreferredWidth(7);
		table.getColumn("단가").setPreferredWidth(7);
		table.getColumn("유통기한").setPreferredWidth(10);
		
		setCheck_btnProductInfoChange(0);// 회원수정 버튼이 동작 불가하도록 변경
	}
	
	private void buttonLoad(Connection con, JPanel p, SQLExecute sql) {
		// 배치
		JButton btnInquiry = new JButton("전체 조회(새로고침)");
		btnInquiry.setBounds(1025, 10, 150, 25);
		JButton btnEXP = new JButton("유통기한순 정렬");
		btnEXP.setBounds(1025, 40, 150, 25);
		JButton btnAmount = new JButton("수량순 정렬");
		btnAmount.setBounds(1025, 70, 150, 25);
		
		// 구현부
		btnInquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productTableLoad(sql, p, con, "조회");
			}
		});
		
		btnEXP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productTableLoad(sql, p, con, "유통기한조회");
			}
		});
		
		btnAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productTableLoad(sql, p, con, "수량순조회");
			}
		});
		
		// 삽입
		p.add(btnInquiry);
		p.add(btnEXP);
		p.add(btnAmount);
	}
	
	public void productInfoChange(JPanel p, Connection con, SQLExecute sql) {
		/*************
		 * 제품정보 변경   *
		 *************/
		
		// Label
		JLabel productInfoChangeLabel = new JLabel("< 상품정보 변경 > - 상단 테이블에서 선택 후 아래 테이블에서 더블클릭하여 변경 후 [엔터], [상품정보 변경]버튼 클릭");
		productInfoChangeLabel.setBounds(350, 510, 920, 25);
		p.add(productInfoChangeLabel);
		
		// table
		// 회원정보 테이블 생성
		String productInfoColumn[] = {"분류", "상품코드"};	// 열 이름
		
		DefaultTableModel productChangeModel = new DefaultTableModel(productInfoArray, productInfoColumn);
		productChangeInfoTable = new JTable(productChangeModel);
		
		// 회원정보 수정 테이블 생성
		String column[] = {"상품명", "수량", "단가", "유통기한"};	// 열 이름
		
		DefaultTableModel model = new DefaultTableModel(productChangeArray, column);
		productChangeTable = new JTable(model);
		
		// 각 셀의 너비와 정렬
		JScrollPane sp = new JScrollPane(productChangeTable);	// 스크롤 바 붙이기
		sp.setBounds(500, 540, 650, 40);
		
		JScrollPane sp1 = new JScrollPane(productChangeInfoTable);	// 스크롤 바 붙이기
		sp1.setBounds(350, 540, 150, 40);

		p.add(sp);
		p.add(sp1);

		// JButton
		JButton btnProductInfoChange = new JButton("상품정보 변경");
		btnProductInfoChange.setBounds(350, 600, 150, 25);
		
		btnProductInfoChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date selectDate = null;
				int chkNull = 0;
				if(Check_btnProductInfoChange == 0) {
					JOptionPane.showMessageDialog(null, "상단 테이블에서 변경할 상품을 선택해주세요.", "상품정보 수정", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String pID = productChangeInfoTable.getValueAt(0, 1).toString();
					String selectName = productChangeTable.getValueAt(0, 0).toString();
					String selectAmount = productChangeTable.getValueAt(0, 1).toString();
					String selectPrice = productChangeTable.getValueAt(0, 2).toString();
					String sSelectDate = null;
					try {
						sSelectDate = productChangeTable.getValueAt(0, 3).toString();// null값 발생할 경우 오류
					} catch(NullPointerException e1) {
						chkNull = 1;
					}
					
					if(sSelectDate.equals("")) {
						chkNull = 1;
						sSelectDate = null;
					}
					
					boolean chk_date = true;
					if(chkNull == 0) {
						try {
							SimpleDateFormat  dateFormat = new  SimpleDateFormat("yyyy-MM-dd");// date형식으로 들어왔는지 유효성 검사를 위한 것
							dateFormat.setLenient(false);	// 날짜가 파싱될 때 조건을 허술하게 할지 말지 설정? ==> false: 엄격하게
							dateFormat.parse(sSelectDate);
							chk_date = true;
						} catch(Throwable e1) {
							chk_date = false;
						}
					}
					
					if(chk_date == false) {
						JOptionPane.showMessageDialog(null, "잘못된 값을 입력하였습니다. 날짜 형식(yyyy-mm-dd)에 맞춰 입력해주세요.", "상품정보 수정", JOptionPane.ERROR_MESSAGE);
					}
					else {
						String pName = productChangeInfoTable.getValueAt(0, 0).toString();
						String message = "상품 정보를 다음과 같이 변경하시겠습니까?\n(변경사항이 있는 항목만 표기됩니다.)\n< 기존 >\n";
						String message1 = "";// 변경된 상품정보 저장
						int chkValue = 0;	// 변경될 값이 있는지 확인
						
						// 상품명이 [변경 전]과 [변경 후]가 같지 않을 경우(변경된 경우)
						if(!table.getValueAt(table.getSelectedRow(), 2).toString().equals(selectName)) {
							message = message + "  - 상품명: " + table.getValueAt(table.getSelectedRow(), 2);
							message1 = message1 + "\n  - 상품명: " + selectName;
							chkValue++;
						}
						
						// 수량 정보가 [변경 전]과 [변경 후]가 같지 않을 경우
						if(!(table.getValueAt(table.getSelectedRow(), 3).toString().equals(selectAmount))) {
							message = message + "\n  - 수량: " + table.getValueAt(table.getSelectedRow(), 3);
							message1 = message1 + "\n  - 수량: " + selectAmount;
							chkValue++;
						}
						
						// 단가 정보가 [변경 전]과 [변경 후]가 같지 않을 경우
						if(!(table.getValueAt(table.getSelectedRow(), 4).toString().equals(selectPrice))) {
							message = message + "\n  - 단가: " + table.getValueAt(table.getSelectedRow(), 4);
							message1 = message1 + "\n  - 단가: " + selectPrice;
							chkValue++;
						}
						
						try {
							selectDate = Date.valueOf(sSelectDate);
							System.out.println("sSelectDate: " + sSelectDate);
							System.out.println(table.getValueAt(table.getSelectedRow(), 5).toString());
							// 유통기한 정보가 [변경 전]과 [변경 후]가 같지 않을 경우
							if(!table.getValueAt(table.getSelectedRow(), 5).toString().equals(selectDate)) {
								message = message + "\n  - 유통기한: " + table.getValueAt(table.getSelectedRow(), 5);
								message1 = message1 + "\n  - 유통기한: " + selectDate;
								chkValue++;
							}
						} catch(IllegalArgumentException e1) {
							System.out.println("상품 유통기한이 비어있음.");
							//selectDate = null;
						} catch(NullPointerException e1) {
							System.out.println("상품 유통기한이 비어있음.");
							//selectDate = null;
							chkValue++;
						}
						
						message = message + "\n\n< 변경 >";
						message = message + message1;
						// result - 0: 예 / 1: 아니오 / -1: x버튼으로 닫기
						
						if(chkValue == 0) {
							JOptionPane.showMessageDialog(null, "[변경할 값]이 [기존 값]과 동일합니다. 값을 변경해주세요.", "상품정보 변경", JOptionPane.ERROR_MESSAGE);
						}
						else {
							int result = JOptionPane.showConfirmDialog(null, message, "상품정보 변경", JOptionPane.YES_NO_OPTION);
							if(result == 0) {
								int chk = sql.productInfoChange(con, selectName, Integer.parseInt(selectAmount), Integer.parseInt(selectPrice), selectDate, pID);
								if(chk == 1) {// 1개 행의 값이 변경(update)되었으면
									JOptionPane.showMessageDialog(null, "상품정보 변경 완료!", "상품정보 변경", 1);
									productTableLoad(sql, p, con, "조회");// 상단 테이블 다시 로드
									setCheck_btnProductInfoChange(0);// 회원정보 수정 버튼 눌리지 않도록 변경
								}
								else {
									JOptionPane.showMessageDialog(null, "SQL 처리 중 오류 발생! 콘솔창을 확인해주세요.", "상품정보 변경", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				}
			}
		});
		p.add(btnProductInfoChange);
	}
	
	public void productAdd(JPanel p, Connection con, SQLExecute sql) {
		/**********
		 * 상품 추가   *
		 **********/
		
		// Label
		JLabel productAddLable = new JLabel("< 상품 추가 >");
		productAddLable.setBounds(10, 510, 90, 25);
		p.add(productAddLable);
		
		JLabel pGroupLable = new JLabel("분         류");
		pGroupLable.setBounds(10, 540, 70, 25);
		p.add(pGroupLable);
		
		JLabel pIDLable = new JLabel("상품코드");
		pIDLable.setBounds(10, 570, 70, 25);
		p.add(pIDLable);
		
		JLabel pNameLable = new JLabel("상  품  명");
		pNameLable.setBounds(10, 600, 70, 25);
		p.add(pNameLable);
		
		JLabel pAmountLable = new JLabel("수         량");
		pAmountLable.setBounds(10, 630, 70, 25);
		p.add(pAmountLable);
		
		JLabel pPriceLable = new JLabel("단         가");
		pPriceLable.setBounds(10, 660, 70, 25);
		p.add(pPriceLable);
		
		JLabel pExpLable = new JLabel("유통기한");
		pExpLable.setBounds(10, 690, 70, 25);
		p.add(pExpLable);
		
		// ComboBox
		String [] select = {"캔음료", "페트음료", "이용권", "라면", "과자류", "식사류", "간식류"};
		JComboBox combo = new JComboBox(select);
		combo.setBounds(100, 540, 150, 25);
		p.add(combo);
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR) + 3;	// 현재 연도보다 3년 후까지 표기
		String [] year = new String[thisYear - 2018];
		year[0] = "(null)";
		int cnt = 1;	// index값
		for(int i = 2020; i <= thisYear; i++) {
			year[cnt++] = Integer.toString(i);
		}
		JComboBox yearCombo = new JComboBox(year);
		yearCombo.setBounds(100, 690, 60, 25);
		p.add(yearCombo);
		
		String [] month = new String[12];
		for(int i = 1; i <= 12; i++)
			month[i - 1] = Integer.toString(i);
		JComboBox monthCombo = new JComboBox(month);
		monthCombo.setBounds(170, 690, 40, 25);
		p.add(monthCombo);
		
		String [] day = new String[31];
		for(int i = 1; i <= 31; i++)
			day[i - 1] = Integer.toString(i);
		JComboBox dayCombo = new JComboBox(day);
		dayCombo.setBounds(220, 690, 40, 25);
		p.add(dayCombo);
		
		// TextField
		JTextField pIDText = new JTextField(10);
		pIDText.setBounds(100, 570, 150, 25);
		p.add(pIDText);
		
		JTextField pNameText = new JTextField(30);
		pNameText.setBounds(100, 600, 150, 25);
		p.add(pNameText);
		
		JTextField pAmountText = new JTextField(10);
		pAmountText.setBounds(100, 630, 150, 25);
		p.add(pAmountText);
		
		JTextField pPriceText = new JTextField(10);
		pPriceText.setBounds(100, 660, 150, 25);
		p.add(pPriceText);

		// JButton
		JButton btnProductInfoChange = new JButton("상품 추가");
		btnProductInfoChange.setBounds(100, 715, 150, 25);
		
		btnProductInfoChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String expString = null;
				Date pExpDate = null;
				// 연도의 인덱스가 0(null)이 아닌 경우
				if(yearCombo.getSelectedIndex() != 0) {
					// 날짜 형식(yyyy-MM-dd)의 String으로 저장
					expString = yearCombo.getSelectedItem().toString()
							+ "-" + monthCombo.getSelectedItem().toString()
							+ "-" + dayCombo.getSelectedItem().toString();
					
					// 날짜 형식으로 캐스팅(String -> Date)
					try {
						SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");// date형식으로 들어왔는지 유효성 검사를 위한 것
						dateFormat.setLenient(false);	// 날짜가 파싱될 때 조건을 허술하게 할지 말지 설정? ==> false: 엄격하게
						dateFormat.parse(expString);
						pExpDate = Date.valueOf(expString);
					} catch (ParseException e1) {
						System.out.println("상품 추가> 날짜 형식으로 변환 과정에서 오류 발생!!");
						e1.printStackTrace();
					}
				}
				// 입력값이 null인경우
				if(pIDText.getText().toString().equals("") || pNameText.getText().toString().equals("") || pAmountText.getText().toString().equals("") || pPriceText.getText().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "비어있는 칸이 있습니다. 모든 칸에 데이터를 입력해주세요.", "상품 추가", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						int chk = sql.productAddSQL(con, combo.getSelectedItem().toString(), pIDText.getText(), pNameText.getText(), Integer.parseInt(pAmountText.getText()), Integer.parseInt(pPriceText.getText()), pExpDate);
						if(chk == 1) {// 1개 행의 값이 변경(update)되었으면
							JOptionPane.showMessageDialog(null, "상품 추가 완료!", "상품 추가", JOptionPane.INFORMATION_MESSAGE);
							pIDText.setText("");
							pNameText.setText("");
							pAmountText.setText("");
							pPriceText.setText("");
							productTableLoad(sql, p, con, "조회");// 상단 테이블 다시 로드
						}
						else {
							JOptionPane.showMessageDialog(null, "SQL 처리 중 오류 발생! 콘솔창을 확인해주세요.", "상품 추가", JOptionPane.ERROR_MESSAGE);
						}
					} catch(java.lang.NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "\'수량\'과 \'단가\'에는 숫자만 입력 가능합니다.", "상품 추가", JOptionPane.ERROR_MESSAGE);
					} 


				}
			}
		});
		p.add(btnProductInfoChange);
	}
	
	public void productDelete(JPanel p, Connection con, SQLExecute sql) {
		/**********
		 * 상품 삭제   *
		 **********/
		
		// JButton
		JButton btnProductDelete = new JButton("상품 삭제");
		btnProductDelete.setBounds(1025, 470, 150, 25);
		
		btnProductDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Check_btnProductInfoChange == 0) {
					JOptionPane.showMessageDialog(null, "상단 테이블에서 삭제할 상품을 선택해주세요.", "상품 삭제", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//String productID = JOptionPane.showInputDialog("삭제할 상품의 [상품코드]를 입력해주세요.");
					String productID = table.getValueAt(table.getSelectedRow(), 1).toString();
					
					DBManager db = sql.productSelectSQL(con, productID);
					ResultSet rs = db.rs;
					String pGroup = null, pID = null, pName = null;
					int pAmount = 0, pPrice = 0;
					Date pExp = null;
					int chkValue = 0; // 정상적으로 조회 되었다면 1로 변경되도록
					
					// ResultSet에 저장된 데이터 얻어오기
					try {
						while(rs.next()) {
							pGroup = rs.getString("분류");
							pID = rs.getString("상품코드");
							pName = rs.getString("상품명");
							pAmount = rs.getInt("수량");
							pPrice = rs.getInt("단가");
							pExp = rs.getDate("유통기한");
							chkValue = 1;	// 정상적으로 조회 되었다면 1로 변경되도록
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "존재하지 않는 상품입니다.\n상품코드를 확인후 다시 시도하세요.\n- 선택한 상품코드: " + productID, "상품 삭제", JOptionPane.ERROR_MESSAGE);
					} finally {
						comm.closeDB(db);
						if(rs != null) {
							try {
								rs.close();
							} catch(SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
					
					if(chkValue == 1) {
						String message = "아래 상품을 삭제하시겠습니까?\n"
								+ "삭제 후 되돌릴 수 없으므로, 주의하시길 바랍니다.\n\n"
								+ "< 삭제할 상품 >"
								+ "\n  - 분류: " + pGroup
								+ "\n  - 상품코드: " + pID
								+ "\n  - 상품명: " + pName
								+ "\n  - 수량: " + pAmount
								+ "\n  - 단가: " + pPrice
								+ "\n  - 유통기한: " + pExp;
						// result - 0: 예 / 1: 아니오 / -1: x버튼으로 닫기
						int result = JOptionPane.showConfirmDialog(null, message, "상품 삭제", JOptionPane.YES_NO_OPTION);
						if(result == 0) {
							int chk = sql.productDeleteSQL(con, productID);
							if(chk == 1) {	// chk가 1(값이 업데이트 됨)이면
								JOptionPane.showMessageDialog(null, "삭제되었습니다.", "상품 삭제", JOptionPane.INFORMATION_MESSAGE);
								setCheck_btnProductInfoChange(0);
								productTableLoad(sql, p, con, "조회");
							}
						}
					}
				}

			}
		});
		p.add(btnProductDelete);
	}
}
