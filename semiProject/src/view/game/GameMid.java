package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Linker;
import java.util.*;

public class GameMid extends JPanel {
	private final ImageIcon GAME_MAIN = new ImageIcon("image/Game_Main.jpg");
	private final ImageIcon GAME_CLEAR = new ImageIcon("image/Game_Clear.jpg");
	private final ImageIcon GAME_OVER = new ImageIcon("image/Game_Over.jpg");
	private final ImageIcon GAME_PAUSE = new ImageIcon("image/Game_Pause.jpg");
	
	// �� ��ü ��� ����
	private Linker link;

	private JPanel midPanel;
	private JPanel tempMidPanel;

	private JPanel leftSide;
	private JPanel middleSide;
	private JPanel rightSide;

	private final int WRONG_ANSWER = 0; // ����

	public GameMid(Linker link) {
		this.link = link;
		this.link.setGameMid(this);
	}

	// default ȭ��(�ʱ�ȭ) ����
	public JPanel setDefaultScreen() {
		midPanel = new JPanel();
		midPanel.setLayout(null);
		midPanel.setBounds(0, 0, 700, 750);

		// ���� �������̵� �߰� ����
		leftSide = new JPanel();
		leftSide.setBounds(0, 150, 50, 600);

		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_MAIN.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// ���� �������̵� �߰� ����
		rightSide = new JPanel();
		rightSide.setBounds(650, 150, 50, 600);

		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);

		return midPanel;
	}

	// pause ȭ�� ����
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

	// pause ȭ�� ����
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

	// pause ȭ�� ����
	public JPanel getGameScreen(int level) {
		// �ش� ȭ�� ����
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
		// ���� �ش� ȭ�� ���� ����
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_OVER.getImage(), 0, 0, null);
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

	// Clear ȭ�� ����
	public JPanel getSuccessScreen(int score) {
		// ���� �ش� ȭ�� ���� ����
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_CLEAR.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		;
		middleSide.setBounds(50, 150, 600, 600);
		middleSide.setBackground(Color.WHITE);

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
		// level�� ���� ����
		// ���� (���� �ִ뷹�� �������� �ʿ� ����)
		// ���� �������� �̻��� �� ĭ ���� ���̻� ���� ���ϰ�
		int block = level / 2 + 2;
		int blockNum = block * block;
		int blockTerm = 25 - level;

		// ���ӿ� �г� ����
		JPanel blockPanel = new JPanel();
		blockPanel = new JPanel();
		blockPanel.setLayout(new GridLayout(block, block, blockTerm, blockTerm));
		blockPanel.setBounds(50, 150, 600, 600);
		// blockPanel.setSize(600, 600);

		// �� ���� ����
		Random r = new Random();
		int difColor = 12 + 100 / level;
		
		int red = r.nextInt(256 - difColor);
		int green = r.nextInt(256 - difColor);
		int blue = r.nextInt(256 - difColor);
		Color wrongColor = new Color(red, green, blue);
		Color rightColor = new Color(red + difColor, green + difColor, blue + difColor);

		// ��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[blockNum];
		// �����ư ����
		int answer = (int) (new Random().nextInt(blockNum));

		// ���� �̺�Ʈ ����
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
			link.getGameController().clearLevel(e.getWhen());
		}

	}

	private class WrongButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			link.getGameController().endGame(WRONG_ANSWER);
		}
	}
}
