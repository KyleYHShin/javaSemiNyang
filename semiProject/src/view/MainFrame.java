package view;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;

	public MainFrame() {
	}

	public void setMainFrame() {
		// 윈도우 창 초기화 ----------------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 생성
		gamePanel = new JPanel();
		 rankPanel = new JPanel();
		buttonPanel = new JPanel();

		// 배치관리자 없이 자유롭게 설정가능한 컨테이너
		this.setLayout(null);

		gamePanel.setBounds(0, 0, 700, 850);
		gamePanel.setBackground(Color.RED);// 테스트
		// gamePanel.add(/*각자 생성한 UI 객체 리턴*/);
		this.add(gamePanel);

		rankPanel.setLayout(new BorderLayout());
		rankPanel.setBounds(700, 0, 300, 650);
		rankPanel.setBackground(Color.GREEN);
		rankPanel.add(new RankView().makeRankView());
		this.add(rankPanel);

		buttonPanel.setBounds(700, 650, 300, 200);
		buttonPanel.setBackground(Color.BLUE);
		// buttonPanel.add(/*각자 생성한 UI 객체 리턴*/);
		this.add(buttonPanel);

		this.setVisible(true);
	}

}
