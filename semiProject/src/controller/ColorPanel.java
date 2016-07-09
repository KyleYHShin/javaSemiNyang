package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import view.*;

public class ColorPanel extends JPanel {
	JPanel colorPanel;
	//���� ����
	int Level = 2;
	

	public JPanel makeColorPanel(int Num) {
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0, 0);
		colorPanel.setLayout(new GridLayout(Num, Num));
		JButton[] Btn = new JButton[Num * Num];
		Random r = new Random();
		int answer = (int) (r.nextInt(Num * Num));
		for (int i = 0; i < Num * Num; i++) {
			Btn[i] = new JButton();
			Btn[i].setBackground(Color.YELLOW);
			if (i == answer)
				Btn[i].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "�����Դϴ� !");
						//1.������
						Level++;
						MainFrame mf = new MainFrame();
						mf.removeLevel();
						mf.addLevel(Level);
						//2.������ �ȳ����� �ȶ�
						//mf.newLevel();
						
					}});
			else
				Btn[i].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "Ʋ�Ƚ��ϴ�.");
						
					}});
			colorPanel.add(Btn[i]);
		}
		Btn[answer].setBackground(Color.BLACK);
		return colorPanel;
	}

	
}
