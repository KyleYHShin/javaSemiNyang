package controller;

public class TimeController {
	private final int DURATION = 10; // ȭ�� ���� �ð�
	private final int TIME_LIMIT = 5000;// ���� ���� �ð�

	private boolean started = false; // �����ϴ� ������ �ִ��� Ȯ��
	private boolean checkInGame = false;// ������ �ִ� ������ ���������� Ȯ��

	private long startTime;
	private long endTime;
	private long remainTime;

	Thread game; // ������

	// ���� TimeBar tBar;
	// ���� GameController gameController;

	public TimeController(/*GameController gController*/) {
		// ���� Ÿ�ӷ��̾ƿ� ���� �� ���� (+Ÿ�Ӻ信�� ���α׷����� ���� �� ����)
		// ���� tbar = new TimeBar(); // Ȥ�� ���۷��� �ּҰ� ���� �޾� ����
		// ���� ���Ӻ� ���� �� ����
		// ���� gController = new gController(); // Ȥ�� ���۷��� �ּҰ� ���� �޾� ����
		
		//this.gameController = gController;
		//this.tBar = gameController.getTBar();
	}

	public static void main(String[] args) throws InterruptedException {
		TimeController tc = new TimeController();
		tc.orderGame();
		// System.out.println("1�� �� �Ͻ�����");
		// Thread.sleep(1000);
		// tc.orderGame();
		// System.out.println("1�� �� �����");
		// Thread.sleep(1000);
		// tc.orderGame();
	}

	public void orderGame() {
		// start : ó�� �����ϴ� ���
		if (!started) {
			startGame();
		}
		// pause & restart : �̹� ������ ���۵� ���¿��� ��ư Ŭ����
		else {
			if (checkInGame) {
				pause();
			} else {
				System.out.println("�����");
				restart();
			}
		}
	}

	public void startGame() {
		// ���� ���� Ȯ�ο� ������ �ʱ�ȭ
		started = true;
		checkInGame = true;

		// ���� & ���� �ð��� �Է�
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		System.out.println("ù ����");
		System.out.println("�����ð� : " + TIME_LIMIT);
		// ������ ���� �� ����
		game = new Thread(new gameTimerThread());
		game.start();
	}

	private void pause() {
		// game ������ ����
		game.interrupt();

		checkInGame = false;
		remainTime = endTime - System.currentTimeMillis();

		// System.out.println("�Ͻ�����");
		// System.out.println("�����ð� : " + remainTime);
	}

	private void restart() {
		checkInGame = true;
		// startTime & endTime �缳��
		startTime = System.currentTimeMillis();
		endTime = startTime + remainTime;

		// game ������ �����(������Ͽ� ����)
		game = new Thread(new gameTimerThread());
		game.start();

		// System.out.println("�Ͻ�����");
		// System.out.println("�����ð� : " + remainTime);
	}

	// ���� ����(true:�ð��ʰ� / false:����Ŭ��
	public void endStage(boolean condition) {
		// ������ ����
		game.interrupt();
		if (condition) {
			// ���� �ð��ʰ� â ����(Ȥ�� ���ڿ� ����)
			System.out.println("�ð� �ʰ�");
		} else {
			// ���� ���ӿ��� â ����(Ȥ�� ���ڿ� ����)
			System.out.println("�߸��� ��ư Ŭ��");
		}

		// ���� Ȯ�ο� ������ �ʱ�ȭ
		started = false;
		checkInGame = false;
		// ���� 3.���Ӻ��� ���� �޼��� ȣ�� ����
		// ���� 4.Ÿ�ӷ��̾ƿ� �ʱ�ȭ(���α׷����� �ʱ�ȭ)
		// ���� tBar.setTime(0);
	}

	// ������
	class gameTimerThread implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("thread start");
				while (!Thread.currentThread().isInterrupted()) {
					// ���� �ð�(0.01��) ����
					Thread.sleep(DURATION);
					// ���� �ð� ��� ����
					remainTime = endTime - System.currentTimeMillis();
					// ���� tBar.setTime(remainTime);

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
