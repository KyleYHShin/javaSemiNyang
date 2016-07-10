package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import controller.Controller;

public class ButtonView extends JFrame {

	private JToggleButton startBtn;
	private JButton exitBtn;

	private int level = 1;
	private int score = 0;

	private static long startTime; // ���ް�, ������ �Ͻ������� Ǯ���� Ÿ��
	private static long playTime; // ���ް�, �Ͻ����������� �÷���Ÿ��
	private long pauseTime; // �Ͻ������ߴ� �ð�
	private long endTime; // ���ް�, ������ ����ð�

	public ButtonView() {	}

	
	//getter and setter
	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}

	public long getStartTime() {
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

	public void setLevel(int level) {
		this.level = level;
	}

	public void setScore(int score) {
		this.score = score;
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

	
	
	
	public JButton makeExitButton() { //Exit��ư Ŭ���� ���� ����
		MainFrame main = new MainFrame();
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(200, 100));
		exitBtn.addActionListener(new ActionEventExitHandler(main));
		return exitBtn;
	}

	public JToggleButton makeStartButton() { //������ ���� Ȥ�� �Ͻ�����
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
	public void actionPerformed(ActionEvent e) { //���� start and pause

		ButtonView buttonView = new ButtonView();
		if (pause) {
			// ���� �߿� pause ���
			pause = false;
			buttonView.setPauseTime(e.getWhen());
			tempPlayTime += (buttonView.getPauseTime() - buttonView.getStartTime());
			buttonView.setPlayTime(tempPlayTime);
			
			//���� �۵� Ȯ��
			SimpleDateFormat sdf = new SimpleDateFormat("mm�� ss.SSS��");
			System.out.println("����");			
			System.out.println("pauseTime : " + (buttonView.getPauseTime()));
			System.out.println("���� ��ŸƮŸ��" + buttonView.getStartTime());
			System.out.println("�÷���Ÿ�� : " + sdf.format(buttonView.getPlayTime()));
			// �׽�Ʈ�� ����ð� ����
			// �׽�Ʈ�� �����ð� ���= e.getWhen();
			// Ư�� �޼��忡 ����ð� ����

		} else { 
			//���� ����
			pause = true;
			buttonView.setStartTime(e.getWhen());
			
			//���� �۵� Ȯ��
			System.out.println("��ŸƮ");
			System.out.println("��ŸƮ�ð�" + buttonView.getStartTime());
			// startTime, playTime-����
			// Ư�� �޼��忡 ����ð� ����
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
