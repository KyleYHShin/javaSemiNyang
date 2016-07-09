package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class Client {
	// 명령어 상수
	private final static String CHECK_GET = "get";
	// 서버 포트번호와 서버의 IP주소 설정
	private final static int PORT = 2323;
	private final static String SERVER_IP = "127.0.0.1";

	public Client() {
	}

	public static void main(String[] args){
		Client cl = new Client();
		cl.getUsers(CHECK_GET);
	}
	//후에 자동갱신 기능 추가시 java.util.Timer->schedule() 기능 참고
	public ArrayList<User> getUsers(Object order) {
		// 리턴용 임시 데이터 생성
		ArrayList<User> users = null;

		// test
		System.out.println("클라이언트 : 업데이트 시작");
		try (// 클라이언트용 소켓 객체 생성
				Socket server = new Socket(SERVER_IP, PORT);
				// 객체통신
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream())) {

			// test
			System.out.println("클라이언트 : 통신 객체 생성");

			// 필요 명령어 전송
			oos.writeObject(order);
			oos.flush();
			// test
			System.out.println("클라이언트 : 객체 전송 완료");
			// 데이터 수신
			Object getObject = ois.readObject();
			// test
			System.out.println("클라이언트 : 객체 수신 완료");
			// 수신받은 데이터 변형
			users = checkObject(getObject);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// test
		// if (users != null) {
		// System.out.println("전송받은 users 데이터 확인");
		// Iterator<User> iter = users.iterator();
		// while(iter.hasNext()){
		// System.out.println(iter.next().toString());
		// }
		// }
		return users;
	}

	private ArrayList<User> checkObject(Object getObject) {
		List<User> users = null;
		if (getObject instanceof List) {
			// test
			System.out.println("클라이언트 : List 형 수신");
			users = new ArrayList<User>();
			users.addAll((List<User>) getObject);
			System.out.println("Complete to get users!!");
		} else if (getObject == null) {
			System.out.println("get NULL value...");
		} else {
			System.out.println("get wrong Object...");
		}
		return (ArrayList<User>)users;
	}
}
