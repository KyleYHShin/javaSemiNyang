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
	// ���� ��Ʈ��ȣ�� ������ IP�ּ� ����
	private final int PORT = 2323;
	private ServerSocket server;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	// ������
	private List<User> users;

	public Server() {
		// ���� ������ ���� �о����
		loadUserData();
	}

	public void netWorks() {
		try {
			// ������ ���� ��ü�� ����
			server = new ServerSocket(PORT);

			// test
			System.out.println("���� : ���ϰ�ü ���� �Ϸ�");
			// **�Ŀ� thread ó�� �ʿ�
			// Ŭ���̾�Ʈ�� ���� ��û ���� ���
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// ���� ��û ���� �� �ش� Ŭ���̾�Ʈ ���� ��ü ����
					Socket client = server.accept();
					// test
					System.out.println("���� : Ŭ���̾�Ʈ ����, ��Ž���");
					// ��ü���
					oos = new ObjectOutputStream(client.getOutputStream());
					ois = new ObjectInputStream(client.getInputStream());
					// test
					System.out.println("���� : ���ſ� ��ü ���� �Ϸ�");
					// ������ ����
					Object getObject = null;
					getObject = ois.readObject();
					// test
					System.out.println("���� : Ŭ���̾�Ʈ ������ ���� �Ϸ�");
					// ���� ��ɾ ���� ���
					if (getObject != null) {
						// Update �ܼ� �� �����͸� ���ϴ� ���
						if (getObject instanceof String) {
							// test
							System.out.println("get ����");
							oos.writeObject(users);
							oos.flush();
						} else if (getObject instanceof User) {
							// test
							System.out.println("User ������Ʈ ����");
							users.add((User) getObject);
							oos.writeObject(users);
							oos.flush();
						} else {
							// test
							System.out.println("���� ���� 1");
							oos.writeObject(null);
							oos.flush();
						}
					}
					// null ���� ���� ���� ��� null �� ���� �� ����
					else {
						// test
						System.out.println("���ſ��� 2");
						oos.writeObject(null);
						oos.flush();
					}

					// �ش� Ŭ���̾�Ʈ ��ü�� ����
					oos.close();
					ois.close();
					client.close();
					// test
					System.out.println("���� : Ŭ���̾�Ʈ ���� ����");
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// ���� �߻��� ���� ������ ��� ����
			saveUserData();
			e.printStackTrace();
		}
	}

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

	public void saveUserData() {

	}

	// Test
	public static void main(String[] args) {
		new Server().netWorks();
	}
}
