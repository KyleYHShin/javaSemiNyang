package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import view.*;

public class ColorPanel extends JPanel {
	JPanel colorPanel;
	//시작 레벨
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
						JOptionPane.showMessageDialog(colorPanel, "정답입니다 !");
						//1.오류남
						Level++;
						MainFrame mf = new MainFrame();
						mf.removeLevel();
						mf.addLevel(Level);
						//2.오류는 안나지만 안뜸
						//mf.newLevel();
						
					}});
			else
				Btn[i].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "틀렸습니다.");
						
					}});
			colorPanel.add(Btn[i]);
		}
		Btn[answer].setBackground(Color.BLACK);
		return colorPanel;
	}

	
}
