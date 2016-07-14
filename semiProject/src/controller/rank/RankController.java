package controller.rank;

import java.util.*;
import javax.swing.JOptionPane;

import controller.game.GameController;
import view.MainFrame;
import model.User;

public class RankController {
	
	private final String ORDER_GET = "get";	// ��� ��ɾ�
	
	private MainFrame mainFrame;
	private GameController gameController;
	private Client client;	//Ŭ���̾�Ʈ
	
	private ArrayList<User> data;	//���� ������ ���� ����

	public RankController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.gameController = mainFrame.getGameController();
		
		client = new Client();
	}

	// �ڽ��� ��� ������ ������Ʈ �� �ٿ�ε�
	public Object[][] updateNewUser(String checkedBtn, String nickName, int score, long playTime) {
		// User ����
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(playTime);
		User newUser = new User(nickName, score, date);

		// ������Ʈ �� ������ ����
		data = client.getUsers(newUser);
		if (data == null) {
			gameController.Sound("bgm/Alarm.wav", false);
			JOptionPane.showMessageDialog(mainFrame, "��ſ���!!\n�����͸� �о� �� �� �����ϴ�.");
		}
		return toObjectArray(sortUsers(selectUsers(checkedBtn, data)));
	}

	// RankView�� '������Ʈ'��ư / MainView�� JRadioButton Ŭ��
	public Object[][] getRankList(String checkedBtn, boolean netWork) {
		// '������Ʈ'��ư Ŭ���ø� ���
		if (netWork) {
			ArrayList<User> temp = client.getUsers(ORDER_GET);
			if (temp != null) {
				//������Ʈ �� ���� ������ �� ����
				data = temp;
			}else{
				String msg = "�����͸� �о� �� �� �����ϴ�.\n���� ��ϱ���� ���ѵ˴ϴ�.";
				JOptionPane.showMessageDialog(mainFrame, msg, "��ſ���", JOptionPane.ERROR_MESSAGE);
			}
		}
		// ������Ʈ Ŭ�� ���� or JRadioButton Ŭ���� ���� ������ ��ȯ
		return toObjectArray(sortUsers(selectUsers(checkedBtn, data)));
	}

	// ���ϴ� �Ⱓ���� ��ϸ� ����
	private List<User> selectUsers(String checkedBtn, ArrayList<User> users) {
		int timeTerm = 0;

		// �Ѿ���� ��ư�̸��� ���� �ð��� ����
		if (checkedBtn.equals("����"))
			timeTerm = 30 * 24 * 60 * 60;
		else if (checkedBtn.equals("�ֺ�"))
			timeTerm = 7 * 24 * 60 * 60;
		else if (checkedBtn.equals("�Ϻ�"))
			timeTerm = 24 * 60 * 60;

		// ����ð�
		long today = new GregorianCalendar(Locale.KOREA).getTimeInMillis();

		ArrayList<User> selectList = null;
		
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
	private List<User> sortUsers(List<User> selectedUsers) {
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
