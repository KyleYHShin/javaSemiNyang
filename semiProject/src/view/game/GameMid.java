package view.game;

import javax.swing.*;

import controller.game.GameController;
import view.MainFrame;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class GameMid extends JPanel {
	private final ImageIcon GAME_MAIN = new ImageIcon("image/Game_Main.jpg");
	private final ImageIcon GAME_CLEAR = new ImageIcon("image/Game_Clear.jpg");
	private final ImageIcon GAME_OVER = new ImageIcon("image/Game_Over.jpg");
	private final ImageIcon GAME_PAUSE = new ImageIcon("image/Game_Pause.jpg");

	private GameController gameController;

	private JPanel midPanel;
	private JPanel tempMidPanel;

	private JPanel leftSide;
	private JPanel middleSide;
	private JPanel rightSide;

	private final int WRONG_ANSWER = 0; // ����

	public GameMid(MainFrame mainFrame) {
		gameController = mainFrame.getGameController();

		midPanel = new JPanel();
		midPanel.setLayout(null);
		midPanel.setBounds(0, 0, 700, 750);

		// ���� �������̵� �߰� ����
		leftSide = new JPanel();
		leftSide.setBounds(0, 150, 50, 600);

		middleSide = new JPanel();
		middleSide.setBounds(50, 150, 600, 600);

		// ���� �������̵� �߰� ����
		rightSide = new JPanel();
		rightSide.setBounds(650, 150, 50, 600);

		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
	}

	// default ȭ��(�ʱ�ȭ) ����
	public JPanel setDefaultScreen() {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_MAIN.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);
		middleSide.setBackground(Color.RED);

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// game -> pause ȭ�� ����
	public JPanel getPauseScreen() {
		// ���� middle ȭ�� ����
		tempMidPanel = middleSide;

		// ȭ�� ����
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_PAUSE.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// pause -> game ȭ�� ����
	public JPanel getPreScreen() {
		// ������ middle ȭ�� �ҷ�����
		middleSide = tempMidPanel;

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// game ���� ȭ�� ����
	public JPanel getGameScreen(int level) {
		middleSide = new JPanel();
		middleSide.setBounds(50, 150, 600, 600);
		middleSide = makeBlockPanel(level);

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// Fail ȭ�� ����
	public JPanel getFailScreen(int score) {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_OVER.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// Clear ȭ�� ����
	public JPanel getSuccessScreen(int score) {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_CLEAR.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// �缳��
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// ���� ���� �ִ� ȭ�� ����
	private JPanel makeBlockPanel(int level) {
		// ���� (���� �ִ뷹�� �������� �ʿ� ����)�������� �̻��� �� ĭ ���� ���̻� ���� ���ϰ� ����

		// level�� ���� ����
		int block = level / 2 + 2;
		int blockNum = block * block;
		int blockTerm = 25 - level;

		// ���ӿ� �г� ����
		JPanel blockPanel = new JPanel();
		blockPanel = new JPanel();
		blockPanel.setLayout(new GridLayout(block, block, blockTerm, blockTerm));
		blockPanel.setBounds(50, 150, 600, 600);

		// �� ���� ����
		final int MAX_HALF = 128;
		int difColor = 5 + 110 / level;
		int red, green, blue;
		Color wrongColor, rightColor;

		Random r = new Random();

		red = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		green = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		blue = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		
		if (r.nextBoolean()) {
			wrongColor = new Color(red + difColor, green + difColor, blue + difColor);
		} else {
			wrongColor = new Color(red - difColor, green - difColor, blue - difColor);
		}
		rightColor = new Color(red, green, blue);

		// ��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[blockNum];
		// �����ư ����
		int answer = (int) (new Random().nextInt(blockNum));

		// �̺�Ʈ ����
		RightButtonActionListener rightBtnListener = new RightButtonActionListener();
		WrongButtonActionListener wrongBtnListener = new WrongButtonActionListener();

		// ���ǿ� �°� ��ư ����
		for (int i = 0; i < blockNum; i++) {
			Btn[i] = new JButton();
			if (i == answer) {
				Btn[i].setBackground(rightColor);
				Btn[i].addActionListener(rightBtnListener);
			} else {
				Btn[i].setBackground(wrongColor);
				Btn[i].addActionListener(wrongBtnListener);
			}
			blockPanel.add(Btn[i]);
		}
		return blockPanel;
	}

	// ����Ŭ������ Button �̺�Ʈ ó��
	private class RightButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameController.Sound("bgm/Click.wav").start();
			gameController.clearLevel(e.getWhen());
		}

	}

	private class WrongButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameController.Sound("bgm/Click.wav").start();
			gameController.endGame(WRONG_ANSWER);
		}
	}
}
