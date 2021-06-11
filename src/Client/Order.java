package Client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Order {
	static // frame
	JFrame f = new JFrame("상품 주문");
	static JPanel p;
	// 선언
	String loginDate = null, pcNum = null;
	static JLabel useTimeText, timeText;
	private static String usrID = null;
	static Connection con = DBConnect.DBConnecting();
	
	static // 이미지
	BufferedImage img1, img2, img3, img4, img5, img6;

	
	// getter
	public static String getUsrID() {
		return usrID;
	}
	
	// test메인
//	public static void main(String[] args) {
//		Order order = new Order("test");
//	}
	
	Order(String id) {
		p = new JPanel();
		p.setLayout(null);	// 배치관리자 없이
		
		RadioButton();
		img();
		orderButton();
		
		f.add(p);
		//******** setting	이부분 마지막에 써야 창이 바로 뜸...
		// 창 크기(너비, 높이)
		f.setSize(1000, 600);
		// 창 보이기
		f.setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
//		f.setLocation(500, 400);
		f.setLocationRelativeTo(null);// 창이 가운데
		// 창 크기 고정
		f.setResizable(false);
		// 종료 이벤트(상단 X클릭 시 종료되지 않도록 설정)
//		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // test용
		
		usrID = id;
	}
	
	static void img() {
		// 이미지 리사이즈
		ImageIcon icon1 = new ImageIcon("img/신라면.jpg");
		Image img1Resize = icon1.getImage();
		img1Resize = img1Resize.getScaledInstance(350, 200, Image.SCALE_SMOOTH);
		ImageIcon icon1Resize = new ImageIcon(img1Resize);
		
		ImageIcon icon2 = new ImageIcon("img/사이다.jpg");
		Image img2Resize = icon2.getImage();
		img2Resize = img2Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon2Resize = new ImageIcon(img2Resize);
		
		ImageIcon icon3 = new ImageIcon("img/코카콜라.jpg");
		Image img3Resize = icon3.getImage();
		img3Resize = img3Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon3Resize = new ImageIcon(img3Resize);
		
		ImageIcon icon4 = new ImageIcon("img/이용권(30분).png");
		Image img4Resize = icon4.getImage();
		img4Resize = img4Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon4Resize = new ImageIcon(img4Resize);
		
		ImageIcon icon5 = new ImageIcon("img/이용권(1시간).png");
		Image img5Resize = icon5.getImage();
		img5Resize = img5Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon5Resize = new ImageIcon(img5Resize);
		
		ImageIcon icon6 = new ImageIcon("img/이용권(2시간).png");
		Image img6Resize = icon6.getImage();
		img6Resize = img6Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon6Resize = new ImageIcon(img6Resize);
		
		ImageIcon icon7 = new ImageIcon("img/이용권(3시간).png");
		Image img7Resize = icon7.getImage();
		img7Resize = img7Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon7Resize = new ImageIcon(img7Resize);
		
		ImageIcon icon8 = new ImageIcon("img/이용권(6시간).png");
		Image img8Resize = icon8.getImage();
		img8Resize = img8Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon8Resize = new ImageIcon(img8Resize);
		
		ImageIcon icon9 = new ImageIcon("img/이용권(11시간).png");
		Image img9Resize = icon9.getImage();
		img9Resize = img9Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon9Resize = new ImageIcon(img9Resize);
		
		ImageIcon icon10 = new ImageIcon("img/이용권(13시간).png");
		Image img10Resize = icon10.getImage();
		img10Resize = img10Resize.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon icon10Resize = new ImageIcon(img10Resize);
		
		// JLabel에 이미지 넣기
		p.setLayout(null);	// 배치관리자 없이
		JLabel img1Label = new JLabel(icon1Resize);
		img1Label.setBounds(20, 50, 200, 200);
		p.add(img1Label);
		
		JLabel img2Label = new JLabel(icon2Resize);
		img2Label.setBounds(270, 50, 200, 200);
		p.add(img2Label);
		
		JLabel img3Label = new JLabel(icon3Resize);
		img3Label.setBounds(520, 50, 200, 200);
		p.add(img3Label);
		
//		JLabel img4Label = new JLabel(icon4Resize);
//		img4Label.setBounds(770, 50, 200, 200);
//		p.add(img4Label);
		
		JLabel img5Label = new JLabel(icon5Resize);
		img5Label.setBounds(770, 50, 200, 200);
		p.add(img5Label);
		
//		JLabel img6Label = new JLabel(icon6Resize);
//		img6Label.setBounds(480, 50, 200, 200);
//		p.add(img6Label);
		
		JLabel img7Label = new JLabel(icon7Resize);
		img7Label.setBounds(20, 310, 200, 200);
		p.add(img7Label);
		
		JLabel img8Label = new JLabel(icon8Resize);
		img8Label.setBounds(270, 310, 200, 200);
		p.add(img8Label);
		
		JLabel img9Label = new JLabel(icon9Resize);
		img9Label.setBounds(520, 310, 200, 200);
		p.add(img9Label);
		
		JLabel img10Label = new JLabel(icon10Resize);
		img10Label.setBounds(770, 310, 200, 200);
		p.add(img10Label);
	}
	
	// JRadioButton
	String radioName[] = { "라면", "사이다", "콜라", "이용권-1시간", "이용권-4시간", 
					"이용권-6시간", "이용권-11시간", "이용권-13시간" };
	JRadioButton productRadio[];
	ButtonGroup group;
	public void RadioButton() {
		group = new ButtonGroup();
		productRadio = new JRadioButton[8];
		
		for(int i = 0; i < productRadio.length; i++) {
			productRadio[i] = new JRadioButton(radioName[i]);
			group.add(productRadio[i]);
			p.add(productRadio[i]);
		}
		
		// 배치
		productRadio[0].setBounds(80, 250, 80, 20);
		productRadio[1].setBounds(340, 250, 140, 20);
		productRadio[2].setBounds(600, 250, 140, 20);
		productRadio[3].setBounds(820, 250, 140, 20);
		productRadio[4].setBounds(60, 510, 140, 30);
		productRadio[5].setBounds(320, 510, 140, 30);
		productRadio[6].setBounds(560, 510, 140, 30);
		productRadio[7].setBounds(810, 510, 140, 30);
	}
	
	void orderButton() {
		// JLabel
		JLabel useTimeLabel = new JLabel("수량: ");
		useTimeLabel.setBounds(770, 10, 50, 25);
		p.add(useTimeLabel);
		
		// combobox
		String [] amount = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		JComboBox amountCombo = new JComboBox(amount);
		amountCombo.setBounds(810, 10, 70, 25);
		p.add(amountCombo);
		
		String [] paymentMethod = { "현금", "카드" };
		JComboBox paymentCombo = new JComboBox(paymentMethod);
		paymentCombo.setBounds(450, 10, 70, 25);
		p.add(paymentCombo);
		
		// JLabel
		JLabel memoLabel = new JLabel("요청사항: ");
		memoLabel.setBounds(540, 10, 90, 25);
		p.add(memoLabel);
		
		JTextField memoText = new JTextField();
		memoText.setBounds(600, 10, 150, 25);
		p.add(memoText);
		
		// 주문 버튼
		JButton orderBtn = new JButton("주문");
		orderBtn.setBounds(900, 10, 70, 25);
		
		orderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = -1;
				for(int i = 0; i < radioName.length; i++) {
					if(productRadio[i].isSelected())
						select = i;
				}
				System.out.println(amountCombo.getSelectedIndex()+1);
				System.out.println(select);
				
				if(select == -1) {
					JOptionPane.showMessageDialog(null, "주문할 항목을 선택해주세요.", "주문 오류", JOptionPane.WARNING_MESSAGE);
				} else {
					int exitOption = JOptionPane.showConfirmDialog(null, "선택하신 항목을 주문하시겠습니까?", "주문 처리", JOptionPane.YES_NO_OPTION);
	                
	                // YES_OPTION: 0, NO_OPTION: 1, CLOSED_OPTION: -1을 반환
	                if(exitOption == JOptionPane.YES_OPTION) {
	                	DBManager db = SQLExecute.selectNextSerial_SQL("주문번호");
	                	String orderNo = null;
						try {
							if(db.rs.next())
								orderNo = Integer.toString(db.rs.getInt("시리얼"));
							orderNo = "o" + orderNo;
							SQLExecute.updateOrderNo("주문번호");	// 다음 주문번호로 업데이트
							
							db = SQLExecute.memberInfo_SQL(usrID);
							String pcNo = null;
							if(db.rs.next()) {
								pcNo = db.rs.getString("pc상태.pc번호");
							}
							
							String pno = null;
							int timeSec = 0;
							switch(select) {
								case 0:
									pno = "N01";
									break;
								case 1:
									pno = "P01";
									break;
								case 2:
									pno = "C01";
									break;
								case 3:
									pno = "CU2";
									timeSec = 3600;
									break;
								case 4:
									pno = "CU4";
									timeSec = 10800;
									break;
								case 5:
									pno = "CU5";
									timeSec = 21600;
									break;
								case 6:
									pno = "CU6";
									timeSec = 39600;
									break;
								case 7:
									pno = "CU7";
									timeSec = 46800;
									break;
								default:
									break;
							}
							
							int amount = amountCombo.getSelectedIndex()+1;
							int price = 0;
							int dbAmount = 0;
							String group = null;
							db = SQLExecute.selectProductInfo_SQL(pno);
							if(db.rs.next()) {
								group = db.rs.getString("분류");
								price = db.rs.getInt("단가");
								dbAmount = db.rs.getInt("수량");
							}
							
							if(dbAmount > amount) {
								SQLExecute.insertOrderSQL(orderNo, pno, usrID, pcNo, amount, price*amount, paymentCombo.getSelectedIndex(), "주문", memoText.getText().toString());
								SQLExecute.updateProductAmount(pno, amount);	// 수량 정보 업데이트
								JOptionPane.showMessageDialog(null, "상품이 주문완료되었습니다.", "상품 주문완료", JOptionPane.INFORMATION_MESSAGE);
							} else if(group.equals("이용권")) {
								SQLExecute.insertOrderSQL(orderNo, pno, usrID, pcNo, amount, price*amount, paymentCombo.getSelectedIndex(), "처리완료", memoText.getText().toString());
								SQLExecute.timeOrderUpdate(usrID, timeSec*amount);	// 구입 시간 업데이트
								TimerThread.reload();// 클라이언트의 남은시간 정보 다시 로드
								JOptionPane.showMessageDialog(null, "상품이 주문완료되었습니다.", "상품 주문완료", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "주문할 상품의 수량이 부족합니다.", "상품 수량 부족", JOptionPane.ERROR_MESSAGE);
							}
							
						} catch (SQLException e1) {
							System.out.println("시리얼 조회 중 오류!!");
							e1.printStackTrace();
						} catch (Exception e1) {
							System.out.println("insertOrderSQL 실행 중 오류(Order.java)!!");
							e1.printStackTrace();
						}
						
						Common.closeDB(db);
	                } else if ((exitOption == JOptionPane.NO_OPTION) || (exitOption == JOptionPane.CLOSED_OPTION )) {
	                    return; // 아무 작업도 하지 않고 다이얼로그 상자를 닫음
	                }
				}
				
			}
		});
		p.add(orderBtn);
	}
	
}
