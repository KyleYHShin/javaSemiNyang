package controller.game;

import model.Linker;
import view.MainFrame;
import view.game.GameTop;
import view.game.GameMid;

public class GameController {
	// 각 객체 노드 저장
	private Linker link;

	private final int DURATION = 10; // 화면 갱신 시간
	private final int TIME_LIMIT = 5000;// 게임 제한 시간

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

	private int level;
	private int score;
	private int topScore[];

	Thread game; // 쓰레드

	public GameController(Linker link) {
		this.link = link;
		this.link.setGameController(this);

		// ■■■ 임시 : 레벨별 최대 점수 ■■■
		topScore = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600,
				1700, 1800, 1900, 2000 };
	}

	// 시작 버튼에 대한 처리
	public void startSignal() {
		// start : 처음 시작하는 경우
		if (!started) {
			startNewGame();
		}
		// pause & restart : 이미 게임이 시작된 상태에서 버튼 클릭시
		else {
			if (InGame) {
				pauseSignal();
			} else {
				restartSignal();
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

		// ■■■ GameTop 초기화
		// ■■■ gameTop.setValues(level, score);

		// 게임 시작
		start(level);
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
		link.getGameView().resetPreMid();

		// game 재시작(스레드 생성하여 시작)
		game = new Thread(new gameTimerThread());
		game.start();

		// System.out.println("일시정지");
		// System.out.println("남은시간 : " + remainTime);
	}

	// 게임 시작 메서드
	private void start(int newLevel) {
		// 시작 & 종료 시간값 설정
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		// 스레드 생성
		game = new Thread(new gameTimerThread());
		// GameMid JPanel에 게임화면 세팅
		link.getGameView().resetMidLevel(newLevel);
		// link.getGameMid().setLevelScreen(newLevel);
		// game(스레드) 시작
		game.start();
	}

	// 정답 선택시
	public void clearLevel(long clickTime) {
		// 1. 게임(스레드) 정지
		game.interrupt();

		// 2. 점수 갱신
		remainTime = endTime - clickTime;
		System.out.println(remainTime);
		score += ((double) remainTime / (double) TIME_LIMIT) * topScore[level - 1];
		
		// 3-1. 최고레벨 클리어시
		if (level >= FINAL_LEVEL)
			endGame(CLEAR_GAME);

		// 3-2. 레벨 갱신
		else {
			level++;
			// GameMid
			// ■■■ 4. GameTop에 갱신된 점수와 레벨 업로드
			// ■■■ gameTop.setValues(level, score);

			// 5. 새게임 시작
			start(level);
		}
	}

	// 게임 종료
	// WRONG_ANSWER:오답클릭 / TIMES_UP:시간초과 / CLEAR_GAME:최고레벨클리어
	public void endGame(int reason) {
		// 쓰레드 종료
		game.interrupt();

		String message;
		if (reason == WRONG_ANSWER)
			message = "오답";
		else if (reason == TIMES_UP)
			message = "시간초과";
		else if (reason == CLEAR_GAME)
			message = "게임 클리어";
		else
			message = "ERROR : Wrong Value";

		// 게임용 변수들 초기화
		started = false; // 존재하는 게임이 있는지 확인
		InGame = false;// 기존에 있는 게임이 실행중인지 확인

		// GameMid JPanel을 default 화면으로 설정
		link.getGameMid().setDefaultScreen();
		// ■■■ 4.GameTop 초기화(-> 프로그레스바 초기화)
		// ■■■ 5.MainFrame에 점수 업로드창 띄우기
		// mainFrame.임시창 띄우기(message, score);(->이벤트 핸들러)

		// MainFrame의 시작버튼 초기화
		System.out.println("GameController : 게임종료(" + message + ")");
		link.getGameView().setMidDefault();
		link.getMainFrame().getButtonView().setRefreshStartButton();
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
					// ■■■ gameTop.setTime(remainTime);

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
