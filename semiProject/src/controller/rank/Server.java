package controller.rank;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import model.User;

public class Server {
	private final String CHECK_GET = "get";
	private final String FILE_PATH = "userData.dat";
	private final int PORT = 2323;
	private final int SAVE_TERM = 60000;

	private ServerSocket server;
	// 소켓관리 벡터
	private Vector<Socket> sManager = new Vector<Socket>();
	// 데이터
	private ArrayList<User> users;

	public Server() {
		// 유저 데이터 파일 읽어오기
		loadUserData();
	}

	public void startServer() {
		Auto_Save fileSaver = new Auto_Save(/* users */);
		try {
			// 자동 저장 스레드 실행
			fileSaver.start();
			// 서버용 소켓 객체를 생성
			server = new ServerSocket(PORT);

			// 클라이언트쪽 접속 요청 무한 대기
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// 접속 요청이 올때까지 무한 대기
					Socket client = server.accept();
					System.out.println("서버 : 클라이언트 수신, 통신시작");

					// 클라이언트와 통신하는 스레드를 생성하고 실행시킨다.
					new Client_Thread(client).start();

					// 소켓 관리자 리스트에 소켓을 추가한다.
					sManager.add(client);
					// 현재 접속하고 있는 클라이언트의 수를 화면에 출력한다.
					System.out.println("현재 클라이언트 수 : " + sManager.size());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 해당 클라이언트와 통신하는 스레드
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
				// System.out.println("서버 : 클라이언트용 스레드 생성");
				// 데이터 수신
				Object getObject;
				while ((getObject = ois.readObject()) != null) {
					if (getObject instanceof String) {
						// Update 단순 새 데이터만 원하는 경우
						if (((String) getObject).equals(CHECK_GET)) {
							// System.out.println("서버 : Update 명령어 수신");
							oos.writeObject(users);
							oos.flush();
						}
					} else if (getObject instanceof User) {
						// 새로운 User 데이터 기록 후 갱신된 내용 전송
						// System.out.println("서버 : User 데이터 수신");
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

	// 자동 데이터 저장 스레드
	class Auto_Save extends Thread {

		public void run() {
			// 데이터 저장 객체 생성(파일 덮어씌우기)
			try {
				while (true) {
					System.out.println("서버 : 자동 저장 기능 실행");
					// 특정 시간(1분)동안 sleep
					Thread.sleep(SAVE_TERM);
					// 데이터 저장 : 아래 선언문 위치(코드 줄번호)에 따라 오류 발생
					// 파일을 비워놓고 입력대기 상태로 종료되니 빈파일만 생성되어 다음 파일 읽기 때 문제
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH, false));
					oos.writeObject(users);
					oos.flush();
					oos.close();
					System.out.println("서버 : 자동 저장 완료");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	// 저장된 파일에서 유저 데이터 읽어오기
	public void loadUserData() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			users = (ArrayList) ois.readObject();
		} catch (EOFException e) {
			System.out.println("서버 : 유저 데이터 Load 완료");
			// print();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

}
