package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import controller.*;

public class MainFrame extends JFrame {

	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel scorePanel;
	private JPanel progressbarPanel;

	// �ӽ� ����
	private static MainFrame mainFrame;

	// ������ ���۹�ư �߰�
	private JButton StartBtn;

	public static void main(String[] args) {
		mainFrame = new MainFrame();
		mainFrame.setMainFrame();
	}

	public MainFrame() {
	}

	public void setMainFrame() {
		// ������ â �ʱ�ȭ --------------------------------
		this.setTitle("�����϶�! ���� ����");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// ũ�� ���� �Ұ�
		this.setLayout(null);
		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();

		gamePanel.setLayout(null);
		gamePanel.setSize(700, 850);
		gamePanel.setLocation(0, 0);
		this.add(gamePanel);

		rankPanel.setSize(300, 500);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
		this.add(rankPanel);

		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(700, 500);
		buttonPanel.setBackground(Color.BLUE);

		// ���� �гο� �ӽ� ���۹�ư �߰�////////////////////////
		StartBtn = new JButton("����");
		startButtonActionListener startAction = new startButtonActionListener();
		StartBtn.addActionListener(startAction);
		buttonPanel.add(StartBtn);
		this.add(buttonPanel);

		// gamePanel �Ҽ� �гε� �ʱ�ȭ
		scorePanel = new JPanel();
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);

		colorPanel = new JPanel();
		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		colorPanel.setBackground(Color.BLACK);

		progressbarPanel = new JPanel();
		progressbarPanel.setLayout(null);
		progressbarPanel.setSize(700, 150);
		progressbarPanel.setLocation(0, 700);
		progressbarPanel.setBackground(Color.RED);

		// gamePanel�� �߰�
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);

		this.setVisible(true);
	}

	public void addColorPanel() {
		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		colorPanel.setBackground(Color.BLACK);
	}

	public void addScorePanel() {
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);
	}

	public void addProgressbarPanel() {
		progressbarPanel.setLayout(null);
		progressbarPanel.setSize(700, 150);
		progressbarPanel.setLocation(0, 700);
		progressbarPanel.setBackground(Color.RED);
	}

	public void resetGamePanel(int level) {
		// �гο� �ִ� ��ü�� ��� ����
		colorPanel.removeAll();
		// �� ��ü(�г�) �����Ͽ� �߰�
		colorPanel.add(new GameColorPanel().makeColorPanel(level, this));
		colorPanel.revalidate();
		colorPanel.repaint();
	}

	//
	public void initializeGamePanel() {
		// ����ȭ������ ���ư���
		// ���� ������ �������� �ʱ�ȭ�� �ʿ�
		// (���Ӹ��̶���� �ܼ��� �̹��� �Ѹ�������...)
		colorPanel.removeAll();
		colorPanel.setBackground(Color.BLACK);
		colorPanel.revalidate();
		colorPanel.repaint();
	}

	// ����Ŭ������ startButton �̺�Ʈ ó��
	private class startButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.resetGamePanel(2);
		}
	}
}
