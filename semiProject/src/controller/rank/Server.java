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
	// ��ɾ� ���
	private final String CHECK_GET = "get";
	// ���� ��Ʈ��ȣ�� ������ IP�ּ� ����
	private final int PORT = 2323;
	private ServerSocket server;
	// ���ϰ��� ����
	Vector<Socket> sManager = new Vector<Socket>(); 

	// ������
	private List<User> users;

	public Server() {
		// ���� ������ ���� �о����
		loadUserData();

//		users = new ArrayList<User>();
	}

	public void startServer() {
		try {
			// ������ ���� ��ü�� ����
			server = new ServerSocket(PORT);
			System.out.println("���� : ���ϰ�ü ���� �Ϸ�");

			// Ŭ���̾�Ʈ�� ���� ��û ���� ���
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// ���� ��û ���� �� �ش� Ŭ���̾�Ʈ ���� ��ü ����
					Socket client = server.accept();
					System.out.println("���� : Ŭ���̾�Ʈ ����, ��Ž���");

					// Ŭ���̾�Ʈ�� ����ϴ� �����带 �����ϰ� �����Ų��.
					new Client_Thread(client).start();

					// ���� ������ ����Ʈ�� ������ �߰��Ѵ�.
					sManager.add(client);
					// ���� �����ϰ� �ִ� Ŭ���̾�Ʈ�� ���� ȭ�鿡 ����Ѵ�.
					System.out.println("���� Ŭ���̾�Ʈ ��: " + sManager.size());
				}
			}
		} catch (Exception e) {
			// ���� �߻��� ���� ������ ��� ����
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
				// ��ü���
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
				// test
				System.out.println("���� : ���ſ� ��ü ���� �Ϸ�");
				// ������ ����
				Object getObject;
				while ((getObject = ois.readObject()) != null) {
					System.out.println("���� : Ŭ���̾�Ʈ ������ ���� �Ϸ�");
					if (getObject instanceof String) {
						// Update �ܼ� �� �����͸� ���ϴ� ���
						if (((String) getObject).equals(CHECK_GET)) {
							System.out.println("���� : Update ��ɾ� ����");
							oos.writeObject(users);
							oos.flush();
						}
					} else if (getObject instanceof User) {
						// ���ο� User ������ ��� �� ���ŵ� ���� ����
						System.out.println("���� : User ������ ����");
						users.add((User) getObject);
						oos.writeObject(users);
						oos.flush();
					}
				}
			} catch (ClassNotFoundException | IOException e) {
			} finally { // Ŭ���̾�Ʈ ���������� ó��
				try {
					// ���ϰ����� ����Ʈ���� �ش� ���� ����
					sManager.remove(client);
					// �ش� Ŭ���̾�Ʈ ��ü�� ����
					if (oos != null)
						oos.close();
					if (ois != null)
						ois.close();
					if (client != null)
						client.close();
					// test
					System.out.println("���� : Ŭ���̾�Ʈ ���� ����");
					System.out.println("���� Ŭ���̾�Ʈ ��: " + sManager.size());

				} catch (IOException e) {
				}
			}
		}
	}

	// ������ ����� ���Ͽ��� ���� ������ �о���� ������
	public void loadUserData() {
		// test
		System.out.println("���� ��ü �ε�");
		users = new ArrayList<User>();
		GregorianCalendar gcal;
		users.add(new User("Kyle", 1111, new GregorianCalendar()));
		users.add(new User("Kyle", 222, new GregorianCalendar()));
		users.add(new User("��", 4444, new GregorianCalendar()));
		gcal = new GregorianCalendar(2016, 6, 2);
		users.add(new User("��ȭ", 2390, gcal));
		gcal = new GregorianCalendar(2016, 5, 27);
		users.add(new User("Kyle", 2, gcal));
		gcal = new GregorianCalendar(2016, 5, 25);
		users.add(new User("��", 11231, gcal));
		gcal = new GregorianCalendar(2016, 6, 1);
		users.add(new User("�̽�", 643, gcal));
		gcal = new GregorianCalendar(2016, 6, 7);
		users.add(new User("��ȭ", 467, gcal));
		gcal = new GregorianCalendar(2016, 4, 27);
		users.add(new User("��", 34343, gcal));
	}

	// ������ ���� ������ ���Ͽ� �����ϱ� ������
	// Thread & scheduler : Ư���ð��� �ֱ�� ��� ����
	public void saveUserData() {
		System.out.println("������ ����");
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}

}
