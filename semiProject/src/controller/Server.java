package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import model.User;

public class Server {
	// 서버 포트번호와 서버의 IP주소 설정
	private final int PORT = 2323;
	private ServerSocket server;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	// 데이터
	private List<User> users;

	public Server() {
		// 유저 데이터 파일 읽어오기
		loadUserData();
	}

	public void netWorks() {
		try {
			// 서버용 소켓 객체를 생성
			server = new ServerSocket(PORT);

			// test
			System.out.println("서버 : 소켓객체 생성 완료");
			// **후에 thread 처리 필요
			// 클라이언트쪽 접속 요청 무한 대기
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// 접속 요청 수락 후 해당 클라이언트 소켓 객체 생성
					Socket client = server.accept();
					// test
					System.out.println("서버 : 클라이언트 수신, 통신시작");
					// 객체통신
					oos = new ObjectOutputStream(client.getOutputStream());
					ois = new ObjectInputStream(client.getInputStream());
					// test
					System.out.println("서버 : 수신용 객체 생성 완료");
					// 데이터 수신
					Object getObject = null;
					getObject = ois.readObject();
					// test
					System.out.println("서버 : 클라이언트 데이터 수신 완료");
					// 받은 명령어가 있을 경우
					if (getObject != null) {
						// Update 단순 새 데이터만 원하는 경우
						if (getObject instanceof String) {
							// test
							System.out.println("get 수신");
							oos.writeObject(users);
							oos.flush();
						} else if (getObject instanceof User) {
							// test
							System.out.println("User 오브젝트 수신");
							users.add((User) getObject);
							oos.writeObject(users);
							oos.flush();
						} else {
							// test
							System.out.println("수신 오류 1");
							oos.writeObject(null);
							oos.flush();
						}
					}
					// null 값을 전송 받을 경우 null 값 전달 후 종료
					else {
						// test
						System.out.println("수신오류 2");
						oos.writeObject(null);
						oos.flush();
					}

					// 해당 클라이언트 객체들 해제
					oos.close();
					ois.close();
					client.close();
					// test
					System.out.println("서버 : 클라이언트 접속 해제");
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// 에러 발생시 기존 데이터 모두 저장
			saveUserData();
			e.printStackTrace();
		}
	}

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

	public void saveUserData() {

	}

	// Test
	public static void main(String[] args) {
		new Server().netWorks();
	}
}
