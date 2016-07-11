package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import view.*;

public class GameController extends JPanel {
	
	private int level = 1;
	private long totalscore = 0;
	private GameView gameView;
	private JPanel gameScreenPanel;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(long totalscore) {
		this.totalscore = totalscore;
	}
	public JPanel makeGameScreenPanel(int newLevel) {
		this.level = newLevel;
		gameScreenPanel = new JPanel();
		gameScreenPanel.setSize(600, 600);
		gameScreenPanel.setLocation(0, 0);
		gameScreenPanel.setLayout(new GridLayout(level, level));
		
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
				
				Btn[i].setBackground(Color.BLACK);
				Btn[i].addActionListener(btnListener1);
			} else {

				Btn[i].setBackground(Color.YELLOW);
				Btn[i].addActionListener(btnListener2);
				
			}
			gameScreenPanel.add(Btn[i]);
		}
		return gameScreenPanel;
	}

	
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
			//�� �������� ��� ó���� �Ǵµ� �ջ��� �ȵ�
			e.getActionCommand();
			long clearTime = e.getWhen();
			String time = sdf.format(clearTime - mainFrame.getStartTime());
			System.out.println("Ŭ���� �ҿ� �ð� : "+Double.parseDouble(time));
			long score = (long)((10.000-Double.parseDouble(time))*level*100);
			//���⼭���� �����ִ°ǵ� �ȵ� ��
			setTotalscore(getTotalscore()+score);
			System.out.println("level : "+ level);
			mainFrame.setStartTime(clearTime);
			level++;
			System.out.println("���� : " + getTotalscore());
			// ������ �ִ� ������������ ȭ�鰻�� �޼���(resetGamePanel()) ȣ��
			mainFrame.resetGamePanel(level);*/
		}

	}

	

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
/*
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
*/
		}
	}

	
	public void resetGamePanel() {
		
		
		gameView.setScreenPanel(makeGameScreenPanel(2));
		
		gameScreenPanel.revalidate();
		gameScreenPanel.repaint();
		
		
	}

	//
	public void initializeGamePanel() {
		/*// ����ȭ������ ���ư���
		// ���� ������ �������� �ʱ�ȭ�� �ʿ�
		// (���Ӹ��̶���� �ܼ��� �̹��� �Ѹ�������...)
		colorPanel.removeAll();
		colorPanel.revalidate();
		colorPanel.repaint();
		
	}
	*/
	}
}
