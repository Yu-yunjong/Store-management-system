package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Chat {
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	JPanel p;
	// frame
	JFrame f = new JFrame("채팅");
	
	// test main
	public static void main(String[] args) {
		Chat chet = new Chat("test");
	}
	
	public Chat(String id) {
		p = new JPanel();
		p.setLayout(null);	// 배치관리자 없이
		
		f.add(p);
		
		//******** setting	이부분 마지막에 써야 창이 바로 뜸...
		// 창 크기(너비, 높이)
		f.setSize(350, 500);
		// 창 보이기
		f.setVisible(true);
		// 창이 뜰 위치 결정(가로, 세로)
		f.setLocation(1500, 100);
//		f.setLocationRelativeTo(null);
		// 창 크기 고정
		f.setResizable(false);
		// 종료 이벤트(상단 X클릭 시 종료되지 않도록 설정)
//		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		connect();
		StreamSetting();
		dataSend();
		dataRecv();
	}
	
	public void loadChet() {
		JTextArea chetText = new JTextArea();
		chetText.setBounds(20, 20, 250, 65);
		chetText.setLineWrap(true);		// 너비만큼 text가 도달할 시 자동 줄바꿈
		chetText.setEditable(false);
		p.add(chetText);
	}
	
	//1. 데이터를 지속적으로 송신해줄 스레드
	//2. 데이터를 지속적으로 수신해줄 스레드
	//이 두가지 작업을 지속적으로 해줄 스레드가 필요함 -> 두 개의 메소드에 스레드 생성
	public void connect() {
		try {
			System.out.println("접속 시도...");
			clientSocket = new Socket("192.168.0.21",8484);
			System.out.println("접속 완료. 종료하시려면 [/종료]를 입력해주세요.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dataSend() {
		new Thread(new Runnable() {
			Scanner in = new Scanner(System.in);
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread){
					try {
						String sendData = in.nextLine();
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

	public void dataRecv() {
		new Thread(new Runnable() {
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread) {
					try {
						String recvData = dataInputStream.readUTF();//연결된 InputSteram 객체의 readUTF 메소드를 호출하여 데이터 읽어들임
						if(recvData.equals("/quit"))
							isThread = false;
						else
							System.out.println("관리자 : "+recvData);
					} catch (Exception e) {

					}
				}
				closeAll();
				System.out.println("연결이 종료되었습니다.");
			}
		}).start();
	}

	public void StreamSetting() {
		try {
			dataInputStream = new DataInputStream(clientSocket.getInputStream()); // clientSocket에 InputStream 객체를 연결
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream()); //clientSocket에 OutputStream 객체를 연결
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// close
	public void closeAll() {
		try {
			clientSocket.close();
			dataInputStream.close();
			dataOutputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
