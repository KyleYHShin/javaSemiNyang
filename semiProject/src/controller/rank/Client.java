package controller.rank;

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
			// ������ ����
			Object getObject = ois.readObject();
			// ���Ź��� ������ ����
			users = checkObject(getObject);

			ois.close();
			oos.close();
			server.close();

		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Ŭ���̾�Ʈ : ��ſ���(null ����)");
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
