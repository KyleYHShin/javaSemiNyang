package controller.rank;

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
			// 데이터 수신
			Object getObject = ois.readObject();
			// 수신받은 데이터 변형
			users = checkObject(getObject);

			ois.close();
			oos.close();
			server.close();

		} catch (ClassNotFoundException | IOException e) {
			System.out.println("클라이언트 : 통신오류(null 리턴)");
		}
		return users;
	}

	private ArrayList<User> checkObject(Object getObject) {
		ArrayList<User> users = null;

		if (getObject instanceof ArrayList) {
			users = new ArrayList<User>();
			users.addAll((ArrayList<User>) getObject);
		}
		return /*(ArrayList<User>) */users;
	}
}
