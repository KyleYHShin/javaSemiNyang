package controller.rank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import model.User;

public class Server {
	// 명령어 상수
	private final String CHECK_GET = "get";
	// 서버 포트번호와 서버의 IP주소 설정
	private final int PORT = 2323;
	private ServerSocket server;
	// 소켓관리 벡터
	Vector<Socket> sManager = new Vector<Socket>(); 

	// 데이터
	private List<User> users;

	public Server() {
		// 유저 데이터 파일 읽어오기
		loadUserData();

//		users = new ArrayList<User>();
	}

	public void startServer() {
		try {
			// 서버용 소켓 객체를 생성
			server = new ServerSocket(PORT);
			System.out.println("서버 : 소켓객체 생성 완료");

			// 클라이언트쪽 접속 요청 무한 대기
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// 접속 요청 수락 후 해당 클라이언트 소켓 객체 생성
					Socket client = server.accept();
					System.out.println("서버 : 클라이언트 수신, 통신시작");

					// 클라이언트와 통신하는 스레드를 생성하고 실행시킨다.
					new Client_Thread(client).start();

					// 소켓 관리자 리스트에 소켓을 추가한다.
					sManager.add(client);
					// 현재 접속하고 있는 클라이언트의 수를 화면에 출력한다.
					System.out.println("현재 클라이언트 수: " + sManager.size());
				}
			}
		} catch (Exception e) {
			// 에러 발생시 기존 데이터 모두 저장
			saveUserData();
			System.out.println(e);
		}
	}

	class Client_Thread extends Thread {
		Socket client;
		ObjectOutputStream oos;
		ObjectInputStream ois;

		Client_Thread(Socket client) {
			this.client = client;
		}

		public void run() {
			try {
				// 객체통신
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
				// test
				System.out.println("서버 : 수신용 객체 생성 완료");
				// 데이터 수신
				Object getObject;
				while ((getObject = ois.readObject()) != null) {
					System.out.println("서버 : 클라이언트 데이터 수신 완료");
					if (getObject instanceof String) {
						// Update 단순 새 데이터만 원하는 경우
						if (((String) getObject).equals(CHECK_GET)) {
							System.out.println("서버 : Update 명령어 수신");
							oos.writeObject(users);
							oos.flush();
						}
					} else if (getObject instanceof User) {
						// 새로운 User 데이터 기록 후 갱신된 내용 전송
						System.out.println("서버 : User 데이터 수신");
						users.add((User) getObject);
						oos.writeObject(users);
						oos.flush();
					}
				}
			} catch (ClassNotFoundException | IOException e) {
			} finally { // 클라이언트 접속해제시 처리
				try {
					// 소켓관리자 리스트에서 해당 소켓 제거
					sManager.remove(client);
					// 해당 클라이언트 객체들 해제
					if (oos != null)
						oos.close();
					if (ois != null)
						ois.close();
					if (client != null)
						client.close();
					// test
					System.out.println("서버 : 클라이언트 접속 해제");
					System.out.println("현재 클라이언트 수: " + sManager.size());

				} catch (IOException e) {
				}
			}
		}
	}

	// ■■■■■ 저장된 파일에서 유저 데이터 읽어오기 ■■■■■
	public void loadUserData() {
		// test
		System.out.println("유저 객체 로드");
		users = new ArrayList<User>();
		GregorianCalendar gcal;
		users.add(new User("Kyle", 1111, new GregorianCalendar()));
		users.add(new User("Kyle", 222, new GregorianCalendar()));
		users.add(new User("명", 4444, new GregorianCalendar()));
		gcal = new GregorianCalendar(2016, 6, 2);
		users.add(new User("유화", 2390, gcal));
		gcal = new GregorianCalendar(2016, 5, 27);
		users.add(new User("Kyle", 2, gcal));
		gcal = new GregorianCalendar(2016, 5, 25);
		users.add(new User("명", 11231, gcal));
		gcal = new GregorianCalendar(2016, 6, 1);
		users.add(new User("이슬", 643, gcal));
		gcal = new GregorianCalendar(2016, 6, 7);
		users.add(new User("유화", 467, gcal));
		gcal = new GregorianCalendar(2016, 4, 27);
		users.add(new User("슬", 34343, gcal));
	}

	// ■■■■■ 유저 데이터 파일에 저장하기 ■■■■■
	// Thread & scheduler : 특정시간을 주기로 계속 저장
	public void saveUserData() {
		System.out.println("데이터 저장");
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

}
