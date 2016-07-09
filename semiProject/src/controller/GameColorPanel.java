package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import view.*;

public class GameColorPanel extends JPanel {
	private JPanel colorPanel;
	private MainFrame mainFrame;
	// ���� ����
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public JPanel makeColorPanel(int newLevel, MainFrame getFrame) {
		this.level = newLevel;
		// ������ �ִ� ���������� ���۷��� ����
		this.mainFrame = getFrame;
		// ���ӿ� �г� �����
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0, 0);
		colorPanel.setLayout(new GridLayout(level, level));
		//////////////////////////////////////////////////////
		// �������� �̻��� �� ĭ ���� ���̻� ���� ���ϰ�
		// ���� ���� �˰��� �߰�-> Btn[i].setBackground(Color.BLACK);
		//
		//////////////////////////////////////////////////////
		// ��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[level * level];
		// �����ư ����
		Random r = new Random();
		int answer = (int) (r.nextInt(level * level));
		// �̺�Ʈ ����
		GameButtonActionListener1 btnListener1 = new GameButtonActionListener1();
		GameButtonActionListener2 btnListener2 = new GameButtonActionListener2();
		// ���ǿ� �°� ��ư ����
		for (int i = 0; i < level * level; i++) {
			Btn[i] = new JButton();

			if (i == answer) {
				// ��ư�� �ִ� text �Ⱥ��̰� ���� �ʿ�

				Btn[i].setBackground(Color.BLACK);
				Btn[i].addActionListener(btnListener1);
			} else {

				Btn[i].setBackground(Color.YELLOW);
				Btn[i].addActionListener(btnListener2);
				ButtonView bv = new ButtonView();

				// ������ �� �̺�Ʈ ���� �ʿ�
			}
			colorPanel.add(Btn[i]);
		}
		return colorPanel;
	}

	// ����Ŭ������ Button �̺�Ʈ ó��
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			e.getActionCommand();

			level++;
			
			// ������ �ִ� ������������ ȭ�鰻�� �޼���(resetGamePanel()) ȣ��
			mainFrame.resetGamePanel(level);
		}

	}

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ButtonView buttonview = new ButtonView();
			e.getActionCommand();

			buttonview.setEndTime(e.getWhen());
			System.out.println(buttonview.getEndTime() + "���� ����");

			int saveResult = JOptionPane.showConfirmDialog(colorPanel, "��������\n������ ���ε� �Ͻðڽ��ϱ�?", "���� Ȯ��",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			Controller controller = new Controller();
			long score = controller.scoreCalc(buttonview.getLevel(), buttonview.getPlayTime(),
					buttonview.getStartTime(), buttonview.getEndTime());
			System.out.println(buttonview.getLevel() + "��, �÷���" + buttonview.getPlayTime() + "Ÿ��, ��ŸƮ : "
					+ buttonview.getStartTime() + "Ÿ��, ���� : " + buttonview.getEndTime());
			System.out.println("����" + score);
			buttonview.setPlayTime(0);
			mainFrame.initializeGamePanel();

			if (saveResult == JOptionPane.YES_OPTION) {
				// ������ �������
				// ���� ����
				// ������ ����

			}

		}
	}
}
