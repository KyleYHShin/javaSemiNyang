package view;

import java.awt.*;
import javax.swing.*;

public class GameTop extends JPanel {
	private JPanel gameTopPanel;
	private JTextField stageField;
	private JTextField timeField;
	private JTextField scoreField;

	public void getLevel(int level) {
		stageField.setText("Stage" + level);
	}

	public void getNowtime(long nowTime) {
		timeField.setText(nowTime + "√ ");
	}

	public void getScore(long score) {
		scoreField.setText(score + "¡°");
	}

	public JPanel makeGameTop() {
		gameTopPanel = new JPanel();
		gameTopPanel.setLayout(new FlowLayout());

		stageField = new JTextField();
		timeField = new JTextField();
		scoreField = new JTextField();

		
		gameTopPanel.add(stageField);
		gameTopPanel.add(timeField);
		gameTopPanel.add(scoreField);

		return gameTopPanel;
	}

}
