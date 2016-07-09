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
		// ���� ���� �˰��� �߰�->	Btn[i].setBackground(Color.BLACK);
		//
		//////////////////////////////////////////////////////
		// ��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[level * level];
		// �����ư ����
		Random r = new Random();
		int answer = (int) (r.nextInt(level * level));
		// �̺�Ʈ ����
		GameButtonActionListener btnListener = new GameButtonActionListener();

		// ���ǿ� �°� ��ư ����
		for (int i = 0; i < level * level; i++) {
			Btn[i] = new JButton();

			if (i == answer) {
				//��ư�� �ִ� text �Ⱥ��̰� ���� �ʿ�
				Btn[i].setText("����");
				Btn[i].setBackground(Color.BLACK);
				Btn[i].addActionListener(btnListener);
			} else {
				Btn[i].setText("����");
				Btn[i].setBackground(Color.YELLOW);
				Btn[i].addActionListener(btnListener);
				ButtonView bv = new ButtonView();
				
				//������ �� �̺�Ʈ ���� �ʿ�
			}
			colorPanel.add(Btn[i]);
		}
		return colorPanel;
	}

	// ����Ŭ������ Button �̺�Ʈ ó��
	private class GameButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			if (button.getText().equals("����")) {
				level++;
				// ������ �ִ� ������������ ȭ�鰻�� �޼���(resetGamePanel()) ȣ��
				mainFrame.resetGamePanel(level);
			} else {
				// �ʱ�ȭ������ ���ư���
				mainFrame.initializeGamePanel();
				// ����ȭ�� ���� �߰�
				// "����� ������ " + score + "������ ���ε� �Ͻðڽ��ϱ�?" -> ��/�ƴϿ�
				// �� -> �г��� ���� �� ���� -> ��/�ƴϿ�
				// �� -> ��ȣ : Ŭ���̾�Ʈ.java ȣ��
				JOptionPane.showMessageDialog(colorPanel, "��������\n������ ���ε� �Ͻðڽ��ϱ�?");
			}

		}

	}

}
