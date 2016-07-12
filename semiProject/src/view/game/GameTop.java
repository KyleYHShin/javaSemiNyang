package view.game;

import java.awt.*;
import javax.swing.*;

import model.Linker;

public class GameTop extends JPanel {
	// 각 객체 노드 저장
	private Linker link;

	private JPanel topView;
	private JTextField stageField;
	private JTextField timeField;
	private JTextField scoreField;

	public GameTop(Linker link) {
		this.link = link;
		this.link.setGameTop(this);

		// JPanel topView = new JPanel();
		// topView.setLayout(new FlowLayout());

		topView = new JPanel();
		topView.setLayout(new FlowLayout());
		// gameTopPanel.setPreferredSize(new Dimension(700, 100));
		// topView.setLocation(0, 0);
		topView.setSize(new Dimension(700, 150));
		topView.setBackground(Color.ORANGE);

		// ■■■ 크기나 이미지 설정 필요 ■■■
		stageField = new JTextField();
		timeField = new JTextField();
		scoreField = new JTextField();

		topView.add(stageField);
		topView.add(timeField);
		topView.add(scoreField);
	}

	public JPanel getTopView() {
		return topView;
	}

	public void setLevel(int level) {
		stageField.setText("Stage" + level);
	}

	public void setRemainTime(long nowTime) {
		timeField.setText(nowTime + "초");
	}

	public void setScore(long score) {
		scoreField.setText(score + "점");
	}

}
