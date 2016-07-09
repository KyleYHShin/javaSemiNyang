package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import controller.Controller;

public class ButtonView extends JFrame {

	private JToggleButton startBtn;
	private JButton endTempBtn;
	private JButton exitBtn;

	private long endTime;
	private int level = 1;
	private int score = 0;

	private static long startTime;
	private static long playTime;
	private long pauseTime;

	public ButtonView() {
	}

	public static long getStartTime() {
		return startTime;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public long getPlayTime() {
		return playTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public JButton makeEndTempBtn() {
		MainFrame main = new MainFrame();
		endTempBtn = new JButton("End");
		endTempBtn.addActionListener(new ActionEventHandler(main));
		return endTempBtn;
	}

	public JButton makeExitButton() {
		MainFrame main = new MainFrame();
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(150, 75));
		exitBtn.addActionListener(new ActionEventExitHandler(main));
		return exitBtn;
	}

	public JToggleButton makeStartButton() {
		MainFrame main = new MainFrame();
		startBtn = new JToggleButton();
		startBtn.setBackground(Color.blue);
		startBtn.addActionListener(new ActionEventStartHandler(main));
		try {
			Image tempStartImg = ImageIO.read(new File("image/play.png"));
			Image StartImg = tempStartImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			startBtn.setIcon(new ImageIcon(StartImg));

			Image tempPauseImg = ImageIO.read(new File("image/pause.png"));
			Image PauseImg = tempPauseImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			startBtn.setSelectedIcon(new ImageIcon(PauseImg));
		} catch (Exception e) {
		}

		return startBtn;
	}
}

class ActionEventStartHandler implements ActionListener {

	private JFrame parent;

	private boolean pause = false;
	private long tempPlayTime = 0;

	public ActionEventStartHandler(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ButtonView buttonView = new ButtonView();
		if (pause) {
			// ���� �߿� pause ���
			System.out.println("����");
			pause = false;
			buttonView.setPauseTime(e.getWhen());
			System.out.println("pauseTime : " + buttonView.getPauseTime());
			System.out.println("���� ��ŸƮŸ��" + buttonView.getStartTime());
			tempPlayTime += buttonView.getPlayTime() + (buttonView.getPauseTime() - buttonView.getStartTime());
			buttonView.setPlayTime(tempPlayTime);
			System.out.println("�÷���Ÿ�� : " + buttonView.getPlayTime());
			// �׽�Ʈ�� ����ð� ����
			// �׽�Ʈ�� �����ð� ���= e.getWhen();
			// Ư�� �޼��忡 ����ð� ����

		} else {
			// ������� ���� ����
			System.out.println("��ŸƮ");
			pause = true;
			buttonView.setStartTime(e.getWhen());
			System.out.println("��ŸƮ�ð�" + buttonView.getStartTime());
			// startTime, playTime-����
			// ������ �Ϸ�ÿ� playTime += listTime - startTime;
			// Ư�� �޼��忡 ����ð� ����
		}

	}

}

//������ 2������ Exit ��� Exit ��ư ó��, �̰Ŷ� �����ư�̶� ��� ���ľߵ�
class ActionEventHandler implements ActionListener {

	private JFrame parent;

	public ActionEventHandler(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ButtonView buttonview = new ButtonView();
		switch (e.getActionCommand()) {

		case "End":
			buttonview.setEndTime(e.getWhen());
			System.out.println(buttonview.getEndTime() + "���� ����");

			int saveResult = JOptionPane.showConfirmDialog(parent, "�����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (saveResult == JOptionPane.YES_OPTION) {
				// ������ �������
				// ���� ����
				Controller controller = new Controller();
				long score = controller.scoreCalc(buttonview.getLevel(), buttonview.getPlayTime(),
						buttonview.getStartTime(), buttonview.getEndTime());
				System.out.println(buttonview.getLevel() + "��, �÷���" + buttonview.getPlayTime() + "Ÿ��, ��ŸƮ : "
						+ buttonview.getStartTime() + "Ÿ��, ���� : " + buttonview.getEndTime());
				System.out.println("����" + score);
			} else {
				buttonview.setPlayTime(0);
				// ���� ����
			} // ���� ������ �÷��� Ÿ�� �ʱ�ȭ
			break;

		}
	}

	
}
class ActionEventExitHandler implements ActionListener {
	private JFrame parent;

	public ActionEventExitHandler(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonView buttonview = new ButtonView();
		e.getActionCommand();
		int result = JOptionPane.showConfirmDialog(parent, "���� �����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (result == JOptionPane.NO_OPTION) {
		}
	}
}

