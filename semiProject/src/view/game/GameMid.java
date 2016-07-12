package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Linker;
import java.util.*;

public class GameMid extends JPanel {
	// �� ��ü ��� ����
	private Linker link;

	private final int WRONG_ANSWER = 0; // ����

	public GameMid(Linker link) {
		this.link = link;
		this.link.setGameMid(this);
	}

	// default ȭ�� ����
	public JPanel setDefaultScreen() {
		System.out.println("GameMid : default ȭ�� ����");
		JPanel defaultScreen = new JPanel();
		defaultScreen.setSize(600, 600);
		defaultScreen.setBackground(Color.BLACK);

		return defaultScreen;
	}

	// pause ȭ�� ����
	public JPanel setPauseScreen() {
		System.out.println("GameMid : pause ȭ�� ����");
		JPanel pauseScreen = new JPanel();
		pauseScreen.setSize(600, 600);
		pauseScreen.setBackground(Color.BLACK);

		return pauseScreen;
	}

	// Clear ȭ�� ����
	public JPanel setClearScreen(){
		System.out.println("GameMid : Clear ȭ�� ����");
		JPanel clearScreen = new JPanel();
		clearScreen.setSize(600, 600);
		clearScreen.setBackground(Color.BLACK);

		return clearScreen;		
	}

	// ���� ���� �ִ� ȭ�� ����
	public JPanel makeBlockPanel(int level) {
		// level�� ���� ����
		// ���� �������� �̻��� �� ĭ ���� ���̻� ���� ���ϰ�
		int block = level / 2 + 2;
		int blockNum = block * block;
		int blockTerm = 23 - level;
		// int difColor = (int) (100 - (985/13) *

		// ���ӿ� �г� ����
		JPanel blockPanel = new JPanel();
		blockPanel.setSize(600, 600);
		blockPanel.setLayout(new GridLayout(block, block, blockTerm, blockTerm));

		// ���� ���� ���� �˰��� �߰�-> Btn[i].setBackground(Color.BLACK);
		
		Random r = new Random(); 
		
		int difColor = 13-level/2; //���Ƿ� ���� (20ź ���ൿ�� 12~3���� �پ���)
		int red = r.nextInt(256-difColor);
		int green = r.nextInt(256-difColor);
		int blue = r.nextInt(256-difColor);
		// int difColor = (int) (100 - (985 / 13) * java.lang.Math.log((double)
		// level));
		
		Color wrongColor = new Color(red,green,blue);
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
