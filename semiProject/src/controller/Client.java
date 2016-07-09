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
	// ��ɾ� ���
	private final static String CHECK_GET = "get";
	// ���� ��Ʈ��ȣ�� ������ IP�ּ� ����
	private final static int PORT = 2323;
	private final static String SERVER_IP = "127.0.0.1";

	public Client() {
	}

	public static void main(String[] args){
		Client cl = new Client();
		cl.getUsers(CHECK_GET);
	}
	//�Ŀ� �ڵ����� ��� �߰��� java.util.Timer->schedule() ��� ����
	public ArrayList<User> getUsers(Object order) {
		// ���Ͽ� �ӽ� ������ ����
		ArrayList<User> users = null;

		// test
		System.out.println("Ŭ���̾�Ʈ : ������Ʈ ����");
		try (// Ŭ���̾�Ʈ�� ���� ��ü ����
				Socket server = new Socket(SERVER_IP, PORT);
				// ��ü���
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream())) {

			// test
			System.out.println("Ŭ���̾�Ʈ : ��� ��ü ����");

			// �ʿ� ��ɾ� ����
			oos.writeObject(order);
			oos.flush();
			// test
			System.out.println("Ŭ���̾�Ʈ : ��ü ���� �Ϸ�");
			// ������ ����
			Object getObject = ois.readObject();
			// test
			System.out.println("Ŭ���̾�Ʈ : ��ü ���� �Ϸ�");
			// ���Ź��� ������ ����
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
		// System.out.println("���۹��� users ������ Ȯ��");
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
			System.out.println("Ŭ���̾�Ʈ : List �� ����");
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
