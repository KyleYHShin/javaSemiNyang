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
		users.add(new User("명", 4444, new GregorianCalendar()));
		gcal = new GregorianCalendar(2016, 6, 2);
		users.add(new User("유화", 2390, gcal));
		gcal = new GregorianCalendar(2016, 5, 27);
		users.add(new User("Kyle", 2, gcal));
		gcal = new GregorianCalendar(2016, 5, 25);
		users.add(new User("명", 11231, gcal));
		gcal = new GregorianCalendar(2016, 6, 1);
		users.add(new User("이슬", 643, gcal));
		gcal = new GregorianCalendar(2016, 6, 7);
		users.add(new User("유화", 467, gcal));
		gcal = new GregorianCalendar(2016, 4, 27);
		users.add(new User("슬", 34343, gcal));

		// 통신이 필요하다고 할때만 users 갱신
		if (netWork) {
			Client client = new Client();
			// users = new Users[];
			// users = client.getRankList();
		}
		return toObjectArray(getSortedUsers(selectUsers(btnName, users)));
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

		// System.out.println(btnName + " : " + timeTerm);
		// 현재시간
		long today = new GregorianCalendar(Locale.KOREA).getTimeInMillis();

		List<User> selectList = null;
		if (users != null) {
			// 필요한 값들만 추출하여 List<User>에 입력
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
		// 점수에 따라 정렬 Descending sorting
		if (selectedUsers != null) {
			selectedUsers.sort(new RankDescending());
		}
		// System.out.println(selectedUsers);
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
