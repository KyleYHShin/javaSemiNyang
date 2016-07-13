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
	// ���ϰ��� ����
	private Vector<Socket> sManager = new Vector<Socket>();
	// ������
	private ArrayList<User> users;

	public Server() {
		// ���� ������ ���� �о����
		loadUserData();
	}

	public void startServer() {
		Auto_Save fileSaver = new Auto_Save(/* users */);
		try {
			// �ڵ� ���� ������ ����
			fileSaver.start();
			// ������ ���� ��ü�� ����
			server = new ServerSocket(PORT);

			// Ŭ���̾�Ʈ�� ���� ��û ���� ���
			if (server != null) {
				while (true) {
					System.out.println("----------------------------");
					// ���� ��û�� �ö����� ���� ���
					Socket client = server.accept();
					System.out.println("���� : Ŭ���̾�Ʈ ����, ��Ž���");

					// Ŭ���̾�Ʈ�� ����ϴ� �����带 �����ϰ� �����Ų��.
					new Client_Thread(client).start();

					// ���� ������ ����Ʈ�� ������ �߰��Ѵ�.
					sManager.add(client);
					// ���� �����ϰ� �ִ� Ŭ���̾�Ʈ�� ���� ȭ�鿡 ����Ѵ�.
					System.out.println("���� Ŭ���̾�Ʈ �� : " + sManager.size());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// �ش� Ŭ���̾�Ʈ�� ����ϴ� ������
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
				// System.out.println("���� : Ŭ���̾�Ʈ�� ������ ����");
				// ������ ����
				Object getObject;
				while ((getObject = ois.readObject()) != null) {
					if (getObject instanceof String) {
						// Update �ܼ� �� �����͸� ���ϴ� ���
						if (((String) getObject).equals(CHECK_GET)) {
							// System.out.println("���� : Update ��ɾ� ����");
							oos.writeObject(users);
							oos.flush();
						}
					} else if (getObject instanceof User) {
						// ���ο� User ������ ��� �� ���ŵ� ���� ����
						// System.out.println("���� : User ������ ����");
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

	// �ڵ� ������ ���� ������
	class Auto_Save extends Thread {

		public void run() {
			// ������ ���� ��ü ����(���� ������)
			try {
				while (true) {
					System.out.println("���� : �ڵ� ���� ��� ����");
					// Ư�� �ð�(1��)���� sleep
					Thread.sleep(SAVE_TERM);
					// ������ ���� : �Ʒ� ���� ��ġ(�ڵ� �ٹ�ȣ)�� ���� ���� �߻�
					// ������ ������� �Է´�� ���·� ����Ǵ� �����ϸ� �����Ǿ� ���� ���� �б� �� ����
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH, false));
					oos.writeObject(users);
					oos.flush();
					oos.close();
					System.out.println("���� : �ڵ� ���� �Ϸ�");
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

	// ����� ���Ͽ��� ���� ������ �о����
	public void loadUserData() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			users = (ArrayList) ois.readObject();
		} catch (EOFException e) {
			System.out.println("���� : ���� ������ Load �Ϸ�");
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
