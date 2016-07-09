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
	// 시작 레벨
	private int Level = 2;

	public JPanel makeColorPanel(int Num) {
		//게임용 패널 재생성
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0, 0);
		colorPanel.setLayout(new GridLayout(Num, Num));
		
		//버튼 갯수 초기화
		JButton[] Btn = new JButton[Num * Num];
		//정답버튼 결정
		Random r = new Random();
		int answer = (int) (r.nextInt(Num * Num));
		addActionListener 
		
		// 조건에 맞게 버튼 생성
		for (int i = 0; i < Num * Num; i++) {
			Btn[i] = new JButton();
			Btn[i].setBackground(Color.YELLOW);
			
			if (i == answer){
				Btn[i].setText("정답");
				Btn[i].addActionListener(l);
			}
				Btn[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "정답입니다 !");
						// 1.오류남
						Level++;
						MainFrame mf = new MainFrame();
						mf.removeLevel();
						mf.addLevel(Level);
						// 2.오류는 안나지만 안뜸
						// mf.newLevel();

					}
				});
			else
				Btn[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(colorPanel, "틀렸습니다.");

					}
				});
			colorPanel.add(Btn[i]);
		}
		Btn[answer].setBackground(Color.BLACK);
		return colorPanel;
	}

	// 내부클래스로 JRadioButton 이벤트 처리
	private class GameButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			JOptionPane.showMessageDialog(colorPanel, "정답입니다 !");
			// 1.오류남
			Level++;
			MainFrame mf = new MainFrame();
			mf.removeLevel();
			mf.addLevel(Level);
			// 2.오류는 안나지만 안뜸
			// mf.newLevel();
		}

	}

}
