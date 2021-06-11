package Client;

import java.sql.Connection;
import java.sql.SQLException;

public class TimerThread extends Thread {
	private int nowTimeSec = 0;
	private int sec = 0;
	private int min = 0;
	private int hour = 0;
	private int day = 0;
	
	private String nowTimeStr = null;
	private String DBTimeStr = null;
	
	static Connection con = DBConnect.DBConnecting();
	private static int dbTime = 0;	// DB에 저장되어 있는 남은 시간
	
	// getter
	public static int getDbTime() {
		return dbTime;
	}
	
	int time = 0;	// 현재 시간을 초로 저장
//	int dbTime = 0;	// DB에 저장되어 있는 남은 시간
	int cnt30 = 0;	// 30초마다 DB에 기록하기 위한 카운트 변수
	
	static void reload() {
		// ID에서 현재 남은시간 조회
		DBManager db = SQLExecute.memberInfo_SQL(ClientMain.getUsrID());
		// 3) ResultSet에 저장된 데이터 얻어오기
		try {
			if(db.rs.next()) {
				dbTime = db.rs.getInt("남은시간");
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
	
	@Override
	public void run() {
		reload();
		
		// 스레드를 유지하기 위해 무한루프 작성
		while(true) {
			// 로그인 후 현재 시간을 클라이언트 창에 1초마다 업데이트
			nowTimeStr = ClientMain.timeDay(time) + "일 " + ClientMain.timeHour(time) + 
					":" + ClientMain.timeMin(time) + ":" + ClientMain.timeSec(time);
			ClientMain.useTimeText.setText(nowTimeStr);
			
			// 로그인 후 DB에 저장되어 있던 남은 시간을 클라이언트 창에 1초마다 업데이트
			DBTimeStr = ClientMain.timeDay(dbTime) + "일 " + ClientMain.timeHour(dbTime) + 
					":" + ClientMain.timeMin(dbTime) + ":" + ClientMain.timeSec(dbTime);
			ClientMain.timeText.setText(DBTimeStr);
			
			// 30초마다 남은시간 자동저장
			if(cnt30 == 30) {
				SQLExecute.timeUpdate(ClientMain.getUsrID(), dbTime);
				cnt30 = 0;
			}
			
			dbTime--;
			time++;
			cnt30++;
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("스레드 종료(InterruptedException)");
				e.printStackTrace();
			}
		}
	}
}
