package controller.rank;

import java.util.*;
import javax.swing.JOptionPane;
import model.Linker;
import model.User;

public class RankController {
	Linker link;
	// 명령어 상수
	private final String ORDER_GET = "get";

	private List<User> data;
	private Client client;

	public RankController(Linker link) {
		this.link = link;
		this.link.setRankController(this);

		client = new Client();
	}

	// 자신의 기록 서버에 업데이트 후 다운로드
	public void updateNewUser(String nickName, int score, long playTime) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(playTime);
		User newUser = new User(nickName, score, date);
		// ■■■ 수신받을 때까지 창띄워서 다른작업 불가 ■■■
		data = client.getUsers(newUser);
		if (data == null) {
			JOptionPane.showMessageDialog(link.getMainFrame(), "통신오류!!\n데이터를 읽어 올 수 없습니다.");
		}

		link.getRankView().updateJTable(toObjectArray(getSortedUsers(data)));
	}

	// 기존 유저데이터 갱신 및 정렬
	public Object[][] getRankList(String btnName, boolean netWork) {
		// "업데이트"버튼 클릭시만 통신
		if (netWork) {
			// ■■■ 수신받을 때까지 창띄워서 다른작업 불가 ■■■
			data = client.getUsers(ORDER_GET);
			if (data == null) {
				String msg = "데이터를 읽어 올 수 없습니다.\n점수 기록기능이 제한됩니다.";
				JOptionPane.showMessageDialog(link.getMainFrame(), msg, "통신오류", JOptionPane.ERROR_MESSAGE);// ,
																											// icon);
			}
		}
		return toObjectArray(getSortedUsers(selectUsers(btnName, data)));
	}

	// 내 점수만 출력
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

	// 원하는 기간안의 목록만 추출
	private List<User> selectUsers(String btnName, List<User> users) {
		int timeTerm = 0;

		// 넘어오는 버튼이름에 따라 시간텀 조정
		if (btnName.equals("월별"))
			timeTerm = 30 * 24 * 60 * 60;
		else if (btnName.equals("주별"))
			timeTerm = 7 * 24 * 60 * 60;
		else if (btnName.equals("일별"))
			timeTerm = 24 * 60 * 60;

		// 현재시간
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

	// 목록의 점수기준 내림차순 정렬
	private List<User> getSortedUsers(List<User> selectedUsers) {
		if (selectedUsers != null) {
			selectedUsers.sort(new DescRank());
		}
		// //Test
		// for(User u : selectedUsers)
		// System.out.println(u.toString());
		return selectedUsers;
	}

	// 목록의 Object[][]화
	private Object[][] toObjectArray(List<User> sortedUser) {
		Object[][] users = null;
		if (sortedUser != null) {
			users = new Object[sortedUser.size()][4];
			for (int i = 0; i < sortedUser.size(); i++) {
				// object : {랭크, 닉네임, 스코어, 날짜}
				users[i][0] = i + 1;
				users[i][1] = sortedUser.get(i).getNickName();
				users[i][2] = sortedUser.get(i).getScore();
				users[i][3] = sortedUser.get(i).getDateToString();
				//// Test
				// System.out.print("랭크 : " + (i+1));
				// System.out.print(" 이름 : " + sortedUser.get(i).getNickName());
				// System.out.print(" 점수 : " + sortedUser.get(i).getScore());
				// System.out.print(" 시간 : " +
				// sortedUser.get(i).getDateToString());
				// System.out.println();
			}
		}
		return users;
	}
}
