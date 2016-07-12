package controller.game;

import model.Linker;
import view.MainFrame;
import view.game.GameTop;
import view.game.GameMid;

public class GameController {
	// �� ��ü ��� ����
	private Linker link;

	private final int DURATION = 10; // ȭ�� ���� �ð�
	private final int TIME_LIMIT = 5000;// ���� ���� �ð�

	private final int WRONG_ANSWER = 0; // ����
	private final int TIMES_UP = 1; // �ð��ʰ�
	private final int CLEAR_GAME = 2; // ���� Ŭ����
	private final int FINAL_LEVEL = 20; // ���� ����

	// ���ӿ� ����
	private boolean started = false; // �����ϴ� ������ �ִ��� Ȯ��
	private boolean InGame = false;// ������ �ִ� ������ ���������� Ȯ��

	private long startTime;
	private long endTime;
	private long remainTime;

	private int level;
	private int score;
	private int topScore[];

	Thread game; // ������

	public GameController(Linker link) {
		this.link = link;
		this.link.setGameController(this);

		// ���� �ӽ� : ������ �ִ� ���� ����
		topScore = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600,
				1700, 1800, 1900, 2000 };
	}

	// ���� ��ư�� ���� ó��
	public void startSignal() {
		// start : ó�� �����ϴ� ���
		if (!started) {
			startNewGame();
		}
		// pause & restart : �̹� ������ ���۵� ���¿��� ��ư Ŭ����
		else {
			if (InGame) {
				pauseSignal();
			} else {
				restartSignal();
			}
		}
	}

	// startSignal 1 : ���� ���� ����
	private void startNewGame() {
		// ���� ������ �ʱ�ȭ
		started = true;
		InGame = true;
		level = 1;
		score = 0;

		// ���� GameTop �ʱ�ȭ
		// ���� gameTop.setValues(level, score);

		// ���� ����
		start(level);
	}

	// startSignal 2 : �Ͻ�����
	private void pauseSignal() {
		InGame = false;
		// ���� �ð� ����
		remainTime = endTime - System.currentTimeMillis();
		// System.out.println("�����ð� : " + remainTime);

		// game �Ͻ�����(������ ����)
		game.interrupt();

		// GameMidPanel�� Pause ȭ�� ����
		link.getGameView().setMidPause();
	}

	// startSignal 3 : �����
	private void restartSignal() {
		InGame = true;
		// startTime & endTime �缳��
		startTime = System.currentTimeMillis();
		endTime = startTime + remainTime;

		// GameMid JPanel�� �ӽ������س��� ���� ȭ�� ����
		link.getGameView().resetPreMid();

		// game �����(������ �����Ͽ� ����)
		game = new Thread(new gameTimerThread());
		game.start();

		// System.out.println("�Ͻ�����");
		// System.out.println("�����ð� : " + remainTime);
	}

	// ���� ���� �޼���
	private void start(int newLevel) {
		// ���� & ���� �ð��� ����
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		// ������ ����
		game = new Thread(new gameTimerThread());
		// GameMid JPanel�� ����ȭ�� ����
		link.getGameView().resetMidLevel(newLevel);
		// link.getGameMid().setLevelScreen(newLevel);
		// game(������) ����
		game.start();
	}

	// ���� ���ý�
	public void clearLevel(long clickTime) {
		// 1. ����(������) ����
		game.interrupt();

		// 2. ���� ����
		remainTime = endTime - clickTime;
		System.out.println(remainTime);
		score += ((double) remainTime / (double) TIME_LIMIT) * topScore[level - 1];
		
		// 3-1. �ְ��� Ŭ�����
		if (level >= FINAL_LEVEL)
			endGame(CLEAR_GAME);

		// 3-2. ���� ����
		else {
			level++;
			// GameMid
			// ���� 4. GameTop�� ���ŵ� ������ ���� ���ε�
			// ���� gameTop.setValues(level, score);

			// 5. ������ ����
			start(level);
		}
	}

	// ���� ����
	// WRONG_ANSWER:����Ŭ�� / TIMES_UP:�ð��ʰ� / CLEAR_GAME:�ְ���Ŭ����
	public void endGame(int reason) {
		// ������ ����
		game.interrupt();

		String message;
		if (reason == WRONG_ANSWER)
			message = "����";
		else if (reason == TIMES_UP)
			message = "�ð��ʰ�";
		else if (reason == CLEAR_GAME)
			message = "���� Ŭ����";
		else
			message = "ERROR : Wrong Value";

		// ���ӿ� ������ �ʱ�ȭ
		started = false; // �����ϴ� ������ �ִ��� Ȯ��
		InGame = false;// ������ �ִ� ������ ���������� Ȯ��

		// GameMid JPanel�� default ȭ������ ����
		link.getGameMid().setDefaultScreen();
		// ���� 4.GameTop �ʱ�ȭ(-> ���α׷����� �ʱ�ȭ)
		// ���� 5.MainFrame�� ���� ���ε�â ����
		// mainFrame.�ӽ�â ����(message, score);(->�̺�Ʈ �ڵ鷯)

		// MainFrame�� ���۹�ư �ʱ�ȭ
		System.out.println("GameController : ��������(" + message + ")");
		link.getGameView().setMidDefault();
		link.getMainFrame().getButtonView().setRefreshStartButton();
	}

	// ����(������)
	class gameTimerThread implements Runnable {
		@Override
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					// ���� �ð�(0.01��) ����
					Thread.sleep(DURATION);
					// ���� �ð� ��� ����
					remainTime = endTime - System.currentTimeMillis();
					// ���� gameTop.setTime(remainTime);

					// �ð��ʰ�
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
