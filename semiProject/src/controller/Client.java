package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class Client {
	// ���� ��Ʈ��ȣ�� ������ IP�ּ� ����
	private final static int PORT = 2323;
	private final static String SERVER_IP = "127.0.0.1";
	Socket server;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Client() {
	}

	// ������ �ڵ����� ���(���ڰ� ���) ������
	// �Ŀ� �ڵ����� ��� �߰��� java.util.Timer->schedule() ��� ����
	public ArrayList<User> getUsers(Object sendData) {
		// ���Ͽ� �ӽ� ������ ����
		ArrayList<User> users = null;

		try {
			// Ŭ���̾�Ʈ�� ���� ��ü ����
			server = new Socket(SERVER_IP, PORT);
			// ��ü���
			ois = new ObjectInputStream(server.getInputStream());
			oos = new ObjectOutputStream(server.getOutputStream());

			// ������ ����
			oos.writeObject(sendData);
			oos.flush();
			// System.out.println("Ŭ���̾�Ʈ : ��ü ���� �Ϸ�");
			// ������ ����
			Object getObject = ois.readObject();
			// System.out.println("Ŭ���̾�Ʈ : ��ü ���� �Ϸ�");
			// ���Ź��� ������ ����
			users = checkObject(getObject);

			ois.close();
			oos.close();
			server.close();

		} catch (ClassNotFoundException | IOException e) {
			// ������ ������ ��� ���� â ��� ������
			System.out.println("������ ��� ����!!");
		}
		return users;
	}

	private ArrayList<User> checkObject(Object getObject) {
		List<User> users = null;

		if (getObject instanceof List) {
			// System.out.println("Ŭ���̾�Ʈ : List �� ����");
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
