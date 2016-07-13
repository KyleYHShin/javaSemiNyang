package controller.game;

import javax.swing.JOptionPane;

import model.Linker;

public class GameController {
	// 각 객체 노드 저장
	private Linker link;

	private final int DURATION = 10; // 화면 갱신 시간(0.01초)
	private final int TIME_LIMIT = 10000;// 게임 제한 시간

	private final int WRONG_ANSWER = 0; // 오답
	private final int TIMES_UP = 1; // 시간초과
	private final int CLEAR_GAME = 2; // 게임 클리어
	private final int FINAL_LEVEL = 20; // 최종 레벨

	// 게임용 변수
	private boolean started = false; // 존재하는 게임이 있는지 확인
	private boolean InGame = false;// 기존에 있는 게임이 실행중인지 확인

	private long startTime;
	private long endTime;
	private long remainTime;

	private int topScore[];

	// 유저 데이터
	private int level;
	private int score;

	Thread game; // 쓰레드

	public GameController(Linker link) {
		this.link = link;
		this.link.setGameController(this);

		// ■■■ 임시 : 레벨별 최대 점수 ■■■
		topScore = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 1000, 1200, 1400, 1600, 1800, 2200, 2600, 3000,
				3400, 3800, 5000, 6000 };
	}

	// 시작 버튼에 대한 처리
	public void startSignal() {
		// start : 처음 시작하는 경우
		if (!started) {
			startNewGame();
			link.getGameBot().setFire();
		}
		// pause & restart : 이미 게임이 시작된 상태에서 버튼 클릭시
		else {
			if (InGame) {
				pauseSignal();
				link.getGameBot().disFire();
			} else {
				restartSignal();
				link.getGameBot().setFire();
			}
		}
	}

	// startSignal 1 : 게임 새로 시작
	private void startNewGame() {
		// 게임 변수들 초기화
		started = true;
		InGame = true;
		level = 1;
		score = 0;

		// 게임 시작
		stageStart(level);
	}

	// startSignal 2 : 일시정지
	private void pauseSignal() {
		InGame = false;
		// 남은 시간 저장
		remainTime = endTime - System.currentTimeMillis();
		// System.out.println("남은시간 : " + remainTime);

		// game 일시정지(스레드 정지)
		game.interrupt();

		// GameMidPanel에 Pause 화면 세팅
		link.getGameView().setMidPause();
	}

	// startSignal 3 : 재시작
	private void restartSignal() {
		InGame = true;
		// startTime & endTime 재설정
		startTime = System.currentTimeMillis();
		endTime = startTime + remainTime;

		// GameMid JPanel에 임시저장해놓은 이전 화면 세팅
		link.getGameView().setMidPre();

		// game 재시작(스레드 생성하여 시작)
		game = new Thread(new gameTimerThread());
		game.start();
	}

	// 게임(해당 level) 시작
	private void stageStart(int newLevel) {
		// 시작 & 종료 시간값 설정
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		// 스레드 생성
		game = new Thread(new gameTimerThread());

		// GameTop 초기화
		link.getGameTop().setStage(level, score);
		// GameMid 초기화(게임화면 세팅)
		link.getGameView().setMidLevel(newLevel);

		// game(스레드) 시작
		game.start();
	}

	// 정답 선택시
	public void clearLevel(long clickTime) {
		// 1. 게임(해당 level=스레드) 정지
		game.interrupt();

		// 2. 점수 갱신
		remainTime = endTime - clickTime;
		int stageScore = (int) (((double) remainTime / (double) TIME_LIMIT) * topScore[level - 1]);
		// System.out.println("Stage Score : " + stageScore);
		score += stageScore;
		// System.out.println("Total Score : " + score);
		link.getGameTop().setStage(level, score);

		// 3-1. 최고레벨 클리어시
		if (level >= FINAL_LEVEL)
			endGame(CLEAR_GAME);

		// 3-2. 레벨 갱신
		else {
			level++;
			// 새 게임(올라간 level) 시작
			stageStart(level);
		}
	}

	// 게임 종료
	// WRONG_ANSWER:오답클릭 / TIMES_UP:시간초과 / CLEAR_GAME:최고레벨클리어
	public void endGame(int reason) {
		// 쓰레드 종료
		game.interrupt();

		// 게임용 변수들 초기화
		started = false; // 존재하는 게임이 있는지 확인
		InGame = false;// 기존에 있는 게임이 실행중인지 확인

		String message;
		if (reason == WRONG_ANSWER) {
			message = "오답";
			link.getGameView().setMidFail(score);
		} else if (reason == TIMES_UP) {
			message = "시간초과";
			link.getGameView().setMidFail(score);
		} else if (reason == CLEAR_GAME) {
			message = "게임 클리어";
			link.getGameView().setMidSuccess(score);
		} else
			message = "ERROR : Wrong Value";

		// GameBot 초기화(프로그레스바)
		link.getGameBot().setTime(0, TIME_LIMIT);
		link.getGameBot().disFire();

		// MainFrame에 점수 업로드창 띄우기
		endMessage();

		// MainFrame의 시작버튼 초기화
		link.getButtonView().setRefreshStartButton();
		System.out.println("GameController : 게임종료(" + message + ")");
	}

	// 게임 종료 후 점수 업로드 확인창
	private void endMessage() {
		String endMsg = "점수 : " + score + "\n저장하시겠습니까?";

		if (JOptionPane.showConfirmDialog(link.getMainFrame(), endMsg, "Upload",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
			// 문자열 입력창
			String nickName = "";
			while (true) {
				nickName = JOptionPane.showInputDialog("닉네임을 입력해주세요.");
				// 취소 선택시
				if (nickName == null) {
					System.out.println("저장취소");
					break;
				}
				// 특정값을 입력하고 확인 선택시
				if (!nickName.equals("") && nickName != null) {
					// 서버에 기록 전송후 랭크뷰의 메인 테이블 -> 서브 테이블 갱신
					link.getRankView().addNewUser(nickName, score, System.currentTimeMillis());
					break;
				}
			}
		}
	}

	// 게임(스레드)
	class gameTimerThread implements Runnable {
		@Override
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					// 일정 시간(0.01초) 마다
					Thread.sleep(DURATION);
					// 남은 시간 계속 전송
					remainTime = endTime - System.currentTimeMillis();
					link.getGameTop().setRemainTime(remainTime);
					link.getGameBot().setTime(remainTime, TIME_LIMIT);

					// 시간초과
					if (remainTime <= 0) {
						endGame(TIMES_UP);
						break;
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
