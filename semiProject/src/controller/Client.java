package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class Client {
	// 서버 포트번호와 서버의 IP주소 설정
	private final static int PORT = 2323;
	private final static String SERVER_IP = "127.0.0.1";
	Socket server;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Client() {
	}

	// ■■■■■ 자동갱신 기능(다자간 통신) ■■■■■
	// 후에 자동갱신 기능 추가시 java.util.Timer->schedule() 기능 참고
	public ArrayList<User> getUsers(Object sendData) {
		// 리턴용 임시 데이터 생성
		ArrayList<User> users = null;

		try {
			// 클라이언트용 소켓 객체 생성
			server = new Socket(SERVER_IP, PORT);
			// 객체통신
			ois = new ObjectInputStream(server.getInputStream());
			oos = new ObjectOutputStream(server.getOutputStream());

			// 데이터 전송
			oos.writeObject(sendData);
			oos.flush();
			// System.out.println("클라이언트 : 객체 전송 완료");
			// 데이터 수신
			Object getObject = ois.readObject();
			// System.out.println("클라이언트 : 객체 수신 완료");
			// 수신받은 데이터 변형
			users = checkObject(getObject);

			ois.close();
			oos.close();
			server.close();

		} catch (ClassNotFoundException | IOException e) {
			// ■■■■■ 데이터 통신 오류 창 출력 ■■■■■
			System.out.println("데이터 통신 오류!!");
		}
		return users;
	}

	private ArrayList<User> checkObject(Object getObject) {
		List<User> users = null;

		if (getObject instanceof List) {
			// System.out.println("클라이언트 : List 형 수신");
			users = new ArrayList<User>();
			users.addAll((List<User>) getObject);
			// System.out.println("Complete to get users!!");
		} else if (getObject == null) {
			// System.out.println("get NULL value...");
		} else {
			// System.out.println("get wrong Object...");
		}
		return (ArrayList<User>) users;
	}
}
