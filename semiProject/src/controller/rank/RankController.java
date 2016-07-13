package controller.rank;

import java.util.*;
import javax.swing.JOptionPane;
import model.Linker;
import model.User;

public class RankController {
	Linker link;
	// ��ɾ� ���
	private final String ORDER_GET = "get";

	private List<User> data;
	private Client client;

	public RankController(Linker link) {
		this.link = link;
		this.link.setRankController(this);

		client = new Client();
	}

	// �ڽ��� ��� ������ ������Ʈ �� �ٿ�ε�
	public void updateNewUser(String nickName, int score, long playTime) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(playTime);
		User newUser = new User(nickName, score, date);
		// ���� ���Ź��� ������ â����� �ٸ��۾� �Ұ� ����
		data = client.getUsers(newUser);
		if (data == null) {
			JOptionPane.showMessageDialog(link.getMainFrame(), "��ſ���!!\n�����͸� �о� �� �� �����ϴ�.");
		}

		link.getRankView().updateJTable(toObjectArray(getSortedUsers(data)));
	}

	// ���� ���������� ���� �� ����
	public Object[][] getRankList(String btnName, boolean netWork) {
		// "������Ʈ"��ư Ŭ���ø� ���
		if (netWork) {
			// ���� ���Ź��� ������ â����� �ٸ��۾� �Ұ� ����
			data = client.getUsers(ORDER_GET);
			if (data == null) {
				String msg = "�����͸� �о� �� �� �����ϴ�.\n���� ��ϱ���� ���ѵ˴ϴ�.";
				JOptionPane.showMessageDialog(link.getMainFrame(), msg, "��ſ���", JOptionPane.ERROR_MESSAGE);// ,
																											// icon);
			}
		}
		return toObjectArray(getSortedUsers(selectUsers(btnName, data)));
	}

	// �� ������ ���
	public Object[][] getMyList(String myNickName) {
		return toObjectArray(getSortedUsers(selectMine(myNickName)));
	}

	private List<User> selectMine(String myNickName) {
		List<User> selected = new ArrayList<User>();
		for (User u : data) {
			if (u.getNickName().equals(myNickName)) {
				selected.add(u);
			}
		}
		return selected;
	}

	// ���ϴ� �Ⱓ���� ��ϸ� ����
	private List<User> selectUsers(String btnName, List<User> users) {
		int timeTerm = 0;

		// �Ѿ���� ��ư�̸��� ���� �ð��� ����
		if (btnName.equals("����"))
			timeTerm = 30 * 24 * 60 * 60;
		else if (btnName.equals("�ֺ�"))
			timeTerm = 7 * 24 * 60 * 60;
		else if (btnName.equals("�Ϻ�"))
			timeTerm = 24 * 60 * 60;

		// ����ð�
		long today = new GregorianCalendar(Locale.KOREA).getTimeInMillis();

		List<User> selectList = null;
		if (users != null) {
			selectList = new ArrayList<User>();
			for (User u : users) {
				long gameDate = u.getDate().getTimeInMillis();
				if (((today - gameDate) / 1000) <= timeTerm) {
					selectList.add(u);
				}
			}
		}
		return selectList;
	}

	// ����� �������� �������� ����
	private List<User> getSortedUsers(List<User> selectedUsers) {
		if (selectedUsers != null) {
			selectedUsers.sort(new DescRank());
		}
		// //Test
		// for(User u : selectedUsers)
		// System.out.println(u.toString());
		return selectedUsers;
	}

	// ����� Object[][]ȭ
	private Object[][] toObjectArray(List<User> sortedUser) {
		Object[][] users = null;
		if (sortedUser != null) {
			users = new Object[sortedUser.size()][4];
			for (int i = 0; i < sortedUser.size(); i++) {
				// object : {��ũ, �г���, ���ھ�, ��¥}
				users[i][0] = i + 1;
				users[i][1] = sortedUser.get(i).getNickName();
				users[i][2] = sortedUser.get(i).getScore();
				users[i][3] = sortedUser.get(i).getDateToString();
				//// Test
				// System.out.print("��ũ : " + (i+1));
				// System.out.print(" �̸� : " + sortedUser.get(i).getNickName());
				// System.out.print(" ���� : " + sortedUser.get(i).getScore());
				// System.out.print(" �ð� : " +
				// sortedUser.get(i).getDateToString());
				// System.out.println();
			}
		}
		return users;
	}
}
