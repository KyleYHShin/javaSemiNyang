package view.game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Linker;

public class GameTop extends JPanel {
	// 각 객체 노드 저장
	private Linker link;

	private JPanel topView;
	private JLabel stageField;
	private JLabel timeField;
	private JLabel scoreField;

	public GameTop(Linker link) {
		this.link = link;
		this.link.setGameTop(this);
	}

	public JPanel setDefaultScreen() {
		topView = new JPanel();
		topView.setLayout(null);
		topView.setBounds(0, 0, 700, 150);

		stageField = new JLabel("", JLabel.CENTER);
		stageField.setBounds(50, 25, 150, 100);
		TitledBorder stageBorder = BorderFactory.createTitledBorder("Level");
		stageBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
		stageBorder.setTitleColor(Color.GRAY);
		stageField.setBorder(stageBorder);
		stageField.setFont(new Font("Arial", Font.BOLD, 40));
		// ■■■ +글자 크기 설정

		timeField = new JLabel("", JLabel.LEFT);
		timeField.setBounds(250, 25, 150, 100);
		TitledBorder timeBorder = BorderFactory.createTitledBorder("Remain Time");
		timeBorder.setTitleFont(new Font("Arial", Font.BOLD, 18));
		timeBorder.setTitleColor(Color.GRAY);
		timeField.setBorder(timeBorder);
		timeField.setFont(new Font("Arial", Font.BOLD, 30));

		scoreField = new JLabel("", JLabel.CENTER);
		scoreField.setBounds(450, 25, 200, 100);
		TitledBorder scoreBorder = BorderFactory.createTitledBorder("Score");
		scoreBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
		scoreBorder.setTitleColor(Color.GRAY);
		scoreField.setBorder(scoreBorder);
		scoreField.setFont(new Font("Arial", Font.BOLD, 40));

		topView.add(stageField);
		topView.add(timeField);
		topView.add(scoreField);

		return topView;
	}

	public void setStage(int level, int score) {
		stageField.setText(Integer.toString(level));
		scoreField.setText(Integer.toString(score));
	}

	public void setRemainTime(long remainTime) {
		// 소수점 둘째자리까지만(0.01초)으로 설정
		 remainTime /= 10;
		 float time = (float) remainTime / (float) 100;
		 timeField.setText("     "+Float.toString(time));
	}

}
