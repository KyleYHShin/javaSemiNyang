package controller.rank;

import java.util.*;
import model.User;

public class RankController {
	
	// ��ɾ� ���
	private final String ORDER_GET = "get";
	private List<User> data;
	private Client client;

	public RankController() {		
		client = new Client();
	}	

	//�ڽ��� ��� ������ ������Ʈ �� �ٿ�ε�
	public Object[][] updateNewUser(User newUser) {
		//���Ź��� ������ â����� �ٸ��۾� �Ұ��ϵ���...
		data = client.getUsers(newUser);
		return toObjectArray(getSortedUsers(data));
	}
	//���� ���������� ���� �� ���� 
	public Object[][] getRankList(String btnName, boolean netWork) {
		// ����� �ʿ��ϴٰ� �Ҷ��� users ����
		if (netWork) {
			//���Ź��� ������ â����� �ٸ��۾� �Ұ��ϵ���...
			data = client.getUsers(ORDER_GET);
		}
		return toObjectArray(getSortedUsers(selectUsers(btnName, data)));
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
		return users;
	}
}
