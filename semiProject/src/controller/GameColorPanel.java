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
	// ���� ����
	private int Level = 2;

	public JPanel makeColorPanel(int Num) {
		//���ӿ� �г� �����
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0, 0);
		colorPanel.setLayout(new GridLayout(Num, Num));
		
		//��ư ���� �ʱ�ȭ
		JButton[] Btn = new JButton[Num * Num];
		//�����ư ����
		Random r = new Random();
		int answer = (int) (r.nextInt(Num * Num));
		addActionListener 
		
		// ���ǿ� �°� ��ư ����
		for (int i = 0; i < Num * Num; i++) {
			Btn[i] = new JButton();
			Btn[i].setBackground(Color.YELLOW);
			
			if (i == answer){
				Btn[i].setText("����");
				Btn[i].addActionListener(l);
			}
				Btn[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "�����Դϴ� !");
						// 1.������
						Level++;
						MainFrame mf = new MainFrame();
						mf.removeLevel();
						mf.addLevel(Level);
						// 2.������ �ȳ����� �ȶ�
						// mf.newLevel();

					}
				});
			else
				Btn[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "Ʋ�Ƚ��ϴ�.");

					}
				});
			colorPanel.add(Btn[i]);
		}
		Btn[answer].setBackground(Color.BLACK);
		return colorPanel;
	}

	// ����Ŭ������ JRadioButton �̺�Ʈ ó��
	private class GameButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			JOptionPane.showMessageDialog(colorPanel, "�����Դϴ� !");
			// 1.������
			Level++;
			MainFrame mf = new MainFrame();
			mf.removeLevel();
			mf.addLevel(Level);
			// 2.������ �ȳ����� �ȶ�
			// mf.newLevel();
		}

	}

}
