package controller;

import java.util.*;

import model.User;

public class RankListController {
	private List<User> users;

	public RankListController() {
	}

	public Object[][] getRankList(String btnName, boolean netWork) {
		// Test
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

		// ����� �ʿ��ϴٰ� �Ҷ��� users ����
		if (netWork) {
			Client client = new Client();
			// users = new Users[];
			// users = client.getRankList();
		}
		return toObjectArray(getSortedUsers(selectUsers(btnName, users)));
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

		// System.out.println(btnName + " : " + timeTerm);
		// ����ð�
		long today = new GregorianCalendar(Locale.KOREA).getTimeInMillis();

		List<User> selectList = null;
		if (users != null) {
			// �ʿ��� ���鸸 �����Ͽ� List<User>�� �Է�
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
		// ������ ���� ���� Descending sorting
		if (selectedUsers != null) {
			selectedUsers.sort(new RankDescending());
		}
		// System.out.println(selectedUsers);
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
			}
		}
		// System.out.println();
		// for(int i =0; i<users.length; i++){
		// System.out.println(users[i][0] + "," + users[i][1] + "," +
		// users[i][2] + "," + users[i][3] );
		// }
		return users;
	}
}
