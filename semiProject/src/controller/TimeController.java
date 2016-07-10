package controller;

public class TimeController {
	private final int DURATION = 10; // 화면 갱신 시간
	private final int TIME_LIMIT = 5000;// 게임 제한 시간

	private boolean started = false; // 존재하는 게임이 있는지 확인
	private boolean checkInGame = false;// 기존에 있는 게임이 실행중인지 확인

	private long startTime;
	private long endTime;
	private long remainTime;

	Thread game; // 쓰레드

	// ■■■ TimeBar tBar;
	// ■■■ GameController gameController;

	public TimeController(/*GameController gController*/) {
		// ■■■ 타임레이아웃 생성 및 연결 (+타임뷰에서 프로그래스바 생성 및 연결)
		// ■■■ tbar = new TimeBar(); // 혹은 레퍼런스 주소값 전달 받아 저장
		// ■■■ 게임뷰 생성 및 연결
		// ■■■ gController = new gController(); // 혹은 레퍼런스 주소값 전달 받아 저장
		
		//this.gameController = gController;
		//this.tBar = gameController.getTBar();
	}

	public static void main(String[] args) throws InterruptedException {
		TimeController tc = new TimeController();
		tc.orderGame();
		// System.out.println("1초 뒤 일시정지");
		// Thread.sleep(1000);
		// tc.orderGame();
		// System.out.println("1초 뒤 재시작");
		// Thread.sleep(1000);
		// tc.orderGame();
	}

	public void orderGame() {
		// start : 처음 시작하는 경우
		if (!started) {
			startGame();
		}
		// pause & restart : 이미 게임이 시작된 상태에서 버튼 클릭시
		else {
			if (checkInGame) {
				pause();
			} else {
				System.out.println("재시작");
				restart();
			}
		}
	}

	public void startGame() {
		// 게임 실행 확인용 변수들 초기화
		started = true;
		checkInGame = true;

		// 시작 & 종료 시간값 입력
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		System.out.println("첫 시작");
		System.out.println("남은시간 : " + TIME_LIMIT);
		// 스레드 생성 및 시작
		game = new Thread(new gameTimerThread());
		game.start();
	}

	private void pause() {
		// game 쓰레드 정지
		game.interrupt();

		checkInGame = false;
		remainTime = endTime - System.currentTimeMillis();

		// System.out.println("일시정지");
		// System.out.println("남은시간 : " + remainTime);
	}

	private void restart() {
		checkInGame = true;
		// startTime & endTime 재설정
		startTime = System.currentTimeMillis();
		endTime = startTime + remainTime;

		// game 쓰레드 재시작(재생성하여 시작)
		game = new Thread(new gameTimerThread());
		game.start();

		// System.out.println("일시정지");
		// System.out.println("남은시간 : " + remainTime);
	}

	// 게임 종료(true:시간초과 / false:오답클릭
	public void endStage(boolean condition) {
		// 쓰레드 종료
		game.interrupt();
		if (condition) {
			// ■■■ 시간초과 창 띄우기(혹은 문자열 전달)
			System.out.println("시간 초과");
		} else {
			// ■■■ 게임오버 창 띄우기(혹은 문자열 전달)
			System.out.println("잘못된 버튼 클릭");
		}

		// 실행 확인용 변수들 초기화
		started = false;
		checkInGame = false;
		// ■■■ 3.게임뷰의 종료 메서드 호출 ■■■
		// ■■■ 4.타임레이아웃 초기화(프로그레스바 초기화)
		// ■■■ tBar.setTime(0);
	}

	// 쓰레드
	class gameTimerThread implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("thread start");
				while (!Thread.currentThread().isInterrupted()) {
					// 일정 시간(0.01초) 마다
					Thread.sleep(DURATION);
					// 남은 시간 계속 전송
					remainTime = endTime - System.currentTimeMillis();
					// ■■■ tBar.setTime(remainTime);

					// times up
					if (remainTime <= 0) {
						endStage(true);
						break;
					}
				}
			} catch (InterruptedException e) {
				System.out.println("pause");
			} finally {
				System.out.println("thread end");
			}
		}
	}
}
