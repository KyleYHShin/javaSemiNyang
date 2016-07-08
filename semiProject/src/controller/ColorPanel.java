package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import view.*;

public class ColorPanel extends JPanel {
	JPanel colorPanel;
	int Level = 2;
	public JPanel makeColorPanel(int Level) {
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0,0);
		colorPanel.setLayout(new GridLayout(Level, Level));
		JButton[] Btn = new JButton[Level * Level];
		Random r = new Random();
		int answer = (int) (r.nextInt(Level * Level));
		for (int i = 0; i < Level * Level; i++) {
			Btn[i] = new JButton();
			Btn[i].setBackground(Color.YELLOW);
			if (i == answer)
				Btn[i].addActionListener(new Correct());
			else
				Btn[i].addActionListener(new Incorrect());
			colorPanel.add(Btn[i]);
		}
		Btn[answer].setBackground(Color.BLACK);
		return colorPanel;
	}


	public class Correct implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JOptionPane.showMessageDialog(colorPanel, "정답입니다 !");
			MainFrame mf = new MainFrame();
			
			Level++;
			mf.replaceColorPanel(makeColorPanel(Level));
			
		}
	}

	public class Incorrect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JOptionPane.showMessageDialog(colorPanel, "틀렸습니다.");
			
			
		}
	}
}

	

	

