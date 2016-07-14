package controller.game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import view.MainFrame;

public class GameController {
	// ��ü ���
	private MainFrame mainFrame;

	private final int DURATION = 10; // ȭ�� ���� �ð�(0.01��)
	private final int TIME_LIMIT = 10000;// ���� ���� �ð�

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

	private int topScore[];

	// ���� ������
	private int level;
	private int score;

	private Thread game; // ������
	private Clip mainSound;
	private Clip endSound;
	private final String IN_GAME = "bgm/InGame.wav";
	private final String GAME_OVER = "bgm/GameOver.wav";
	private final String GAME_CLEAR = "bgm/GameClear.wav";

	public GameController() {
		mainFrame = new MainFrame(this);

		// ���� ������ �ִ� ���� ����
		topScore = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 1000, 1200, 1400, 1600, 1800, 2200, 2600, 3000,
				3400, 3800, 5000, 6000 };
	}

	// ���� ��ư�� ���� ó��
	public void startSignal() {
		// start : ó�� �����ϴ� ���
		if (!started) {
			// ���� �Ҵ�(ó������ ����)
			mainSound = Sound(IN_GAME);
			mainSound.loop(-1);// ���ѹݺ� ����
			mainSound.start();
			startNewGame();
			mainFrame.getGameView().getGameBot().setFire();
		}
		// pause & restart : �̹� ������ ���۵� ���¿��� ��ư Ŭ����
		else {
			if (InGame) {
				mainSound.stop();
				pauseSignal();
				mainFrame.getGameView().getGameBot().disFire();
			} else {
				mainSound.start();
				restartSignal();
				mainFrame.getGameView().getGameBot().setFire();
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

		// ���� ����
		stageStart(level);
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
		mainFrame.getGameView().setMidPause();
	}

	// startSignal 3 : �����
	private void restartSignal() {
		InGame = true;
		// startTime & endTime �缳��
		startTime = System.currentTimeMillis();
		endTime = startTime + remainTime;

		// GameMid JPanel�� �ӽ������س��� ���� ȭ�� ����
		mainFrame.getGameView().setMidPre();

		// game �����(������ �����Ͽ� ����)
		game = new Thread(new gameTimerThread());
		game.start();
	}

	// ����(�ش� level) ����
	private void stageStart(int newLevel) {
		// ���� & ���� �ð��� ����
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		// ������ ����
		game = new Thread(new gameTimerThread());

		// GameTop �ʱ�ȭ
		mainFrame.getGameView().getGameTop().setStage(level, score);
		// GameMid �ʱ�ȭ(����ȭ�� ����)
		mainFrame.getGameView().setMidLevel(newLevel);

		// game(������) ����
		game.start();
	}

	// ���� ���ý�
	public void clearLevel(long clickTime) {
		// 1. ����(�ش� level=������) ����
		game.interrupt();

		// 2. ���� ����
		remainTime = endTime - clickTime;
		int stageScore = (int) (((double) remainTime / (double) TIME_LIMIT) * topScore[level - 1]);
		// System.out.println("Stage Score : " + stageScore);
		score += stageScore;
		// System.out.println("Total Score : " + score);
		mainFrame.getGameView().getGameTop().setStage(level, score);

		// 3-1. �ְ��� Ŭ�����
		if (level >= FINAL_LEVEL)
			endGame(CLEAR_GAME);

		// 3-2. ���� ����
		else {
			level++;
			// �� ����(�ö� level) ����
			stageStart(level);
		}
	}

	// ���� ����
	// WRONG_ANSWER:����Ŭ�� / TIMES_UP:�ð��ʰ� / CLEAR_GAME:�ְ���Ŭ����
	public void endGame(int reason) {
		// ������ ����
		game.interrupt();
		mainSound.stop();

		// ���ӿ� ������ �ʱ�ȭ
		started = false; // �����ϴ� ������ �ִ��� Ȯ��
		InGame = false;// ������ �ִ� ������ ���������� Ȯ��

		String message;
		if (reason == WRONG_ANSWER) {
			message = "����";
			endSound = Sound(GAME_OVER);
			endSound.start();
			mainFrame.getGameView().setMidFail(score);
		} else if (reason == TIMES_UP) {
			message = "�ð��ʰ�";
			endSound = Sound(GAME_OVER);
			endSound.start();
			mainFrame.getGameView().setMidFail(score);
		} else if (reason == CLEAR_GAME) {
			message = "���� Ŭ����";
			endSound = Sound(GAME_CLEAR);
			endSound.start();
			mainFrame.getGameView().setMidSuccess(score);
		} else
			message = "ERROR : Wrong Value";

		// GameBot �ʱ�ȭ(���α׷�����)
		mainFrame.getGameView().getGameBot().setTime(0, TIME_LIMIT);
		mainFrame.getGameView().getGameBot().disFire();

		// MainFrame�� ���� ���ε�â ����
		endMessage();

		// MainFrame�� ���۹�ư �ʱ�ȭ
		mainFrame.getButtonView().setRefreshStartButton();
		System.out.println("GameController : ��������(" + message + ")");
	}

	// ���� ���� �� ���� ���ε� Ȯ��â
	private void endMessage() {
		String endMsg = "���� : " + score + "\n�����Ͻðڽ��ϱ�?";

		if (JOptionPane.showConfirmDialog(mainFrame, endMsg, "Upload",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
			// ���ڿ� �Է�â
			String nickName = "";
			while (true) {
				nickName = JOptionPane.showInputDialog("�г����� �Է����ּ���.");
				// ��� ���ý�
				if (nickName == null) {
					System.out.println("�������");
					break;
				}
				// Ư������ �Է��ϰ� Ȯ�� ���ý�
				if (!nickName.equals("") && nickName != null) {
					// ������ ��� ������ ��ũ���� ���� ���̺� -> ���� ���̺� ����
					mainFrame.getRankView().addNewUser(nickName, score, System.currentTimeMillis());
					break;
				}
			}
		}
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
					mainFrame.getGameView().getGameTop().setRemainTime(remainTime);
					mainFrame.getGameView().getGameBot().setTime(remainTime, TIME_LIMIT);

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

	// ���� �����׸� ������ ���� (�Ҵ��Ͽ� ó������) �����ϴ� �޼���
	public void setMainSound(Clip mainSound) {
		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(new BufferedInputStream(new FileInputStream("bgm/InGame.wav")));
			mainSound = AudioSystem.getClip();
			mainSound.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���� ����� �޼���
	public Clip Sound(String file) {
		Clip clip = null;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

}
