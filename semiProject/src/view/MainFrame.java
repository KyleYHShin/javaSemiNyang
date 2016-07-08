package view;

import java.awt.*;
import javax.swing.*;

import controller.*;

public class MainFrame extends JFrame {
	ColorPanel cp = new ColorPanel();
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel scorePanel;
	private JPanel progressbarPanel;
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setMainFrame();
	}

	public MainFrame() {
	}

	public void setMainFrame() {
		
		
		
		// 윈도우 창 초기화 --------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 크기 변경 불가
		this.setLayout(null);
		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();
		colorPanel = new JPanel();
		scorePanel = new JPanel();
		progressbarPanel = new JPanel();
		
		// 배치관리자 없이 자유롭게 설정가능한 컨테이너
		gamePanel.setLayout(null);
	    gamePanel.setSize(700, 850);
		gamePanel.setLocation(0, 0);
		this.add(gamePanel);

	    rankPanel.setSize(300, 500);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
		this.add(rankPanel);

		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(700, 500);
		buttonPanel.setBackground(Color.BLUE);
		this.add(buttonPanel);
		
		//gamePanel에 추가
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);

		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		
		
		colorPanel.add(cp.makeColorPanel(2));
		
		
		
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);
		
		progressbarPanel.setLayout(null);
		progressbarPanel.setSize(700, 150);
		progressbarPanel.setLocation(0, 700);
		progressbarPanel.setBackground(Color.RED);
		
		this.setVisible(true);
	}


	public void replaceColorPanel(JPanel jPanel){
		
		colorPanel.removeAll();
		colorPanel.add(jPanel);
		
	}

	
}
