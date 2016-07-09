package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import controller.*;

public class MainFrame extends JFrame {

	// 임시 정의
	private static MainFrame mainFrame;
	
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel scorePanel;
	private JPanel progressbarPanel;
	
	//유화 버튼 추가
	private JToggleButton startBtn;
	private JButton exitBtn;
	
	
	
	public static void main(String[] args) {
		mainFrame = new MainFrame();
		mainFrame.setMainFrame();
	}

	public MainFrame() {
	}

	public void setMainFrame() {
		ButtonView bv = new ButtonView();
		
		// 윈도우 창 초기화 --------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 크기 변경 불가
		this.setLayout(null);
		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();

		gamePanel.setLayout(null);
		gamePanel.setSize(700, 850);
		gamePanel.setLocation(0, 0);
		this.add(gamePanel);

		rankPanel.setSize(300, 550);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
		this.add(rankPanel);

		buttonPanel.setSize(300, 300);
		buttonPanel.setLocation(700, 550);
		buttonPanel.setBackground(Color.BLUE);
		
		
		
		// 유화 버튼기능 버튼패널에 적용
		startBtn = bv.makeStartButton();
		startButtonActionListener startAction = new startButtonActionListener();  
		startBtn.addActionListener(startAction);  
		exitBtn = bv.makeExitButton();
		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);
		
		this.add(buttonPanel);
		
		
		// gamePanel 소속 패널들 초기화
		scorePanel = new JPanel();
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);

		colorPanel = new JPanel();
		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		colorPanel.setBackground(Color.BLACK);

		progressbarPanel = new JPanel();
		progressbarPanel.setLayout(null);
		progressbarPanel.setSize(700, 150);
		progressbarPanel.setLocation(0, 700);
		progressbarPanel.setBackground(Color.RED);

		// gamePanel에 추가
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);

		this.setVisible(true);
	}

	
	public void resetGamePanel(int level) {
		
		GameColorPanel gcp = new GameColorPanel();
		// 패널에 있는 객체들 모두 삭제
		colorPanel.removeAll();
		// 새 객체(패널) 생성하여 추가
		colorPanel.add(gcp.makeColorPanel(level, this));
		colorPanel.revalidate();
		colorPanel.repaint();
	}

	//
	public void initializeGamePanel() {
		// 메인화면으로 돌아가기
		// 게임 중이지 않을때의 초기화면 필요
		// (게임명이라던가 단순한 이미지 뿌리기라던가...)
		colorPanel.removeAll();
		colorPanel.setBackground(Color.BLACK);
		colorPanel.revalidate();
		colorPanel.repaint();
	}
	
	// 내부클래스로 startButton 이벤트 처리  
	private class startButtonActionListener implements ActionListener {  
		@Override  
		public void actionPerformed(ActionEvent e) {  
		mainFrame.resetGamePanel(2);
		
	 	}  
	}  
		
}
