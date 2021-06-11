package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Chat_Server extends JFrame implements ActionListener {
	private ServerSocket serverSocket; //서버 소켓(서비스를 제공하기 위한 용도) 생성
	private Socket clientSocket;//들어오는 정보가 저장되는, 클라이언트와 통신을 위한 소켓

	private DataInputStream dataInputStream;//서버가 받은 데이터
	private DataOutputStream dataOutputStream;
	private String id = null;
	
	JTextArea chetText;
	JTextField inputText;
	String sendData;
	
	JPanel p;
	// frame

	public Chat_Server(String id) {
		this.id = id;
		p = new JPanel();
		p.setLayout(null);	// 배치관리자 없이
		
		add(p);
		
		
		//******** setting	이부분 마지막에 써야 창이 바로 뜸...
		// 창 크기(너비, 높이)
		setSize(350, 500);
		// 창 보이기
		setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		setLocation(800, 400);
//		setLocationRelativeTo(null);
		// 창 크기 고정
		setResizable(false);
		// 종료 이벤트(상단 X클릭 시 종료되지 않도록 설정)
//		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		loadChet();
		serverSetting();
		StreamSetting();
		dataRecv();
		dataSend();
		
	}
	
	// 테스트용 main
	public static void main(String[] args) {
		new Chat_Server("test");
	}
	
	public void loadChet() {
		chetText = new JTextArea();
		chetText.setBounds(0, 0, 350, 430);
		chetText.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		chetText.setEditable(false);
		p.add(chetText);
		
		inputText = new JTextField();
		inputText.setBounds(10, 440, 230, 20);
		inputText.addActionListener(this);
		inputText.requestFocus();// 텍스트 필드에 커서 입력
		p.add(inputText);
		
		JScrollPane js = new JScrollPane(chetText);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		sendData = chetText.getText();
		inputText.setText("");
	}

	
	//1. 데이터를 지속적으로 송신해줄 스레드
	//2. 데이터를 지속적으로 수신해줄 스레드
	//이 두가지 작업을 지속적으로 해줄 스레드가 필요함

	public void serverSetting() {
		try {
			serverSocket = new ServerSocket(8484);//포트번호 8484, 생성과 바인드
			clientSocket = serverSocket.accept(); // 어셉트의 결과로 클라이언트가 접속하면 해당 클라이언트를 관리할 소켓을 생성하여 리턴. 이걸  clientSocket에 받음.
			//실질적으로 소켓에 접속 완료된 시점
			System.out.println("클라이언트 소켓 연결");
			chetText.setText(chetText.getText() + id + "\n고객과 연결되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeAll() {
		try {
			serverSocket.close(); //소켓 사용 후 반납
			clientSocket.close();
			dataInputStream.close();
			dataOutputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void StreamSetting() {
		try {
			dataInputStream = new DataInputStream(clientSocket.getInputStream()); // clientSocket에 InputStream 객체를 연결
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream()); //clientSocket에 OutputStream 객체를 연결
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void dataRecv() {
		new Thread(new Runnable() {
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread) {
					try {
						String recvData = dataInputStream.readUTF();//연결된 InputSteram 객체의 readUTF 메소드를 호출하여 데이터 읽어들임
						if(recvData.equals("/종료"))
							isThread = false;
						else {
							System.out.println(id + " : "+recvData);	// 채팅 데이터
							chetText.setText(chetText.getText() + "\n" + id + " : "+recvData);
						}
							
					} catch (Exception e) {
					}
				}
				closeAll();
				chetText.setText(chetText.getText() + "\n대화가 종료되었습니다.");
				System.out.println("종료되었습니다.");
			}

		}).start();
	}

	public void dataSend() {
		new Thread(new Runnable() {
//			Scanner in = new Scanner(System.in);
			Scanner in = new Scanner(chetText.getText());
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread){
					try {
						sendData = in.nextLine();
						if(sendData.equals("/종료"))
							isThread = false;
						else
							dataOutputStream.writeUTF(sendData);//연결된 출력스트림에 메세지 실어보냄
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}

	
}
