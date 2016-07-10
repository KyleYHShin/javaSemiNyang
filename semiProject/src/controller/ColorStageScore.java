package controller;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import view.*;

public class ColorStageScore extends JPanel {
	private JPanel colorPanel;
	private MainFrame mainFrame;
	// ���� ����
	private int level = 1;
	
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
		//////////////////////////////////////////////////////
		// ��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[level * level];
		// �����ư ����
		Random r = new Random();
		int answer = (int) (r.nextInt(level * level));
		//���� ���� �� ���� ���� ���� �޼ҵ� �߰�
		//Color selectColor =  SelectColor(level);
		//Color answerColor =  AnswerColor(SelectColor(level),level);
		// �̺�Ʈ ����
		GameButtonActionListener1 btnListener1 = new GameButtonActionListener1();
		GameButtonActionListener2 btnListener2 = new GameButtonActionListener2();
		// ���ǿ� �°� ��ư ����
		for (int i = 0; i < level * level; i++) {
			Btn[i] = new JButton();
			if (i == answer) {
				Btn[i].setBackground(Color.BLACK);
				//���� ����
				//Btn[i].setBackground(answerColor);
				Btn[i].addActionListener(btnListener1);
			} else {
				Btn[i].setBackground(Color.YELLOW);
				//���� ����
				//Btn[i].setBackground(selectColor);
				Btn[i].addActionListener(btnListener2);
				
			}
		colorPanel.add(Btn[i]);
		}
		return colorPanel;
	}
	//���� ���� �޼ҵ� �̿ϼ�
	public Color SelectColor(int level){
		Color color = new Color(level,level,level);
		return color;
	}
	//���� ���� ���� �޼ҵ� �̿ϼ�
	public Color AnswerColor(Color color,int level){
		color = new Color(level,level,level);
		return color;
	}
	
	// ����Ŭ������ Button �̺�Ʈ ó��
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonView buttonView = new ButtonView();
			SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
			e.getActionCommand();
			double clearTime = e.getWhen();
			String time = sdf.format(clearTime - buttonView.getStartTime());
			System.out.println("Ŭ���� �ҿ� �ð� : "+Double.parseDouble(time));
			long score = (long) ((10-Double.parseDouble(time))*level*10);
			buttonView.setTotalScore(buttonView.getTotalScore()+score);
			System.out.println("���� : " + buttonView.getTotalScore());
			System.out.println("level : " + level);
			buttonView.setStartTime((long)clearTime);
			level++;
			mainFrame.resetGamePanel(level);
			
		}

	}

	

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ButtonView buttonView = new ButtonView();
			e.getActionCommand();

			buttonView.setEndTime(e.getWhen());
			System.out.println(buttonView.getEndTime() + "���� ����");

			int saveResult = JOptionPane.showConfirmDialog(colorPanel, "��������\n������ ���ε� �Ͻðڽ��ϱ�?", "���� Ȯ��",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			//��ȭ �ӽ� �ۼ� ����
			Button buttonPanel = new Button();
			long score = buttonPanel.scoreCalc(buttonView.getLevel(), buttonView.getPlayTime(),
					buttonView.getStartTime(), buttonView.getEndTime());
			System.out.println(buttonView.getLevel() + "��, �÷���" + buttonView.getPlayTime() + "Ÿ��, ��ŸƮ : "
					+ buttonView.getStartTime() + "Ÿ��, ���� : " + buttonView.getEndTime());
			System.out.println("����" + score);
			
			
			buttonView.setPlayTime(0);
			mainFrame.initializeGamePanel();
			buttonView.setStartTime(0);

			if (saveResult == JOptionPane.YES_OPTION) {
				
				// ������ �������
				// ���� ����
				// ������ ����

			}
		}
	}
	}
	
