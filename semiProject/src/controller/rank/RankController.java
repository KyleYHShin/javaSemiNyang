package controller.rank;

import java.util.*;
import javax.swing.JOptionPane;

import controller.game.GameController;
import view.MainFrame;
import model.User;

public class RankController {
	
	private final String ORDER_GET = "get";	// 통신 명령어
	
	private MainFrame mainFrame;
	private GameController gameController;
	private Client client;	//클라이언트
	
	private ArrayList<User> data;	//점수 데이터 저장 변수

	public RankController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.gameController = mainFrame.getGameController();
		
		client = new Client();
	}

	// 자신의 기록 서버에 업데이트 후 다운로드
	public Object[][] updateNewUser(String checkedBtn, String nickName, int score, long playTime) {
		// User 생성
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(playTime);
		User newUser = new User(nickName, score, date);

		// 업데이트 후 데이터 수신
		data = client.getUsers(newUser);
		if (data == null) {
			gameController.Sound("bgm/Alarm.wav", false);
			JOptionPane.showMessageDialog(mainFrame, "통신오류!!\n데이터를 읽어 올 수 없습니다.");
		}
		return toObjectArray(sortUsers(selectUsers(checkedBtn, data)));
	}

	// RankView의 '업데이트'버튼 / MainView의 JRadioButton 클릭
	public Object[][] getRankList(String checkedBtn, boolean netWork) {
		// '업데이트'버튼 클릭시만 통신
		if (netWork) {
			ArrayList<User> temp = client.getUsers(ORDER_GET);
			if (temp != null) {
				//업데이트 및 갱신 성공시 값 저장
				data = temp;
			}else{
				String msg = "데이터를 읽어 올 수 없습니다.\n점수 기록기능이 제한됩니다.";
				JOptionPane.showMessageDialog(mainFrame, msg, "통신오류", JOptionPane.ERROR_MESSAGE);
			}
		}
		// 업데이트 클릭 실패 or JRadioButton 클릭시 기존 데이터 반환
		return toObjectArray(sortUsers(selectUsers(checkedBtn, data)));
	}

	// 원하는 기간안의 목록만 추출
	private List<User> selectUsers(String checkedBtn, ArrayList<User> users) {
		int timeTerm = 0;

		// 넘어오는 버튼이름에 따라 시간텀 조정
		if (checkedBtn.equals("월별"))
			timeTerm = 30 * 24 * 60 * 60;
		else if (checkedBtn.equals("주별"))
			timeTerm = 7 * 24 * 60 * 60;
		else if (checkedBtn.equals("일별"))
			timeTerm = 24 * 60 * 60;

		// 현재시간
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

	// 목록의 점수기준 내림차순 정렬
	private List<User> sortUsers(List<User> selectedUsers) {
		if (selectedUsers != null) {
			selectedUsers.sort(new DescRank());
		}
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
		return users;
	}
}
