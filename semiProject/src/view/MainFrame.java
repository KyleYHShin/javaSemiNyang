package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import controller.*;

public class MainFrame extends JFrame {
	
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel scorePanel;
	private JPanel progressbarPanel;
	
	//임의의 시작버튼 추가
	private JButton StartBtn;
	
	

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
		
		//임의의 시작버튼 추가
		StartBtn = new JButton("시작");
		buttonPanel.add(StartBtn);
		StartBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				GameColorPanel cp = new GameColorPanel();
				colorPanel.add(cp.makeColorPanel(2));
				colorPanel.updateUI();
			}});
		
		this.add(buttonPanel);
		
		//gamePanel에 추가
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);
		
		addColorPanel();
		addScorePanel();
		addProgressbarPanel();
		
		this.setVisible(true);
	}


	public void addColorPanel(){
		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		colorPanel.setBackground(Color.BLACK);
	}
	public void addScorePanel(){
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);
	}
	public void addProgressbarPanel(){
		progressbarPanel.setLayout(null);
		progressbarPanel.setSize(700, 150);
		progressbarPanel.setLocation(0, 700);
		progressbarPanel.setBackground(Color.RED);
	}
	
	//1.레벨에 맞는 화면 추가
	public void addLevel(int Level){
		GameColorPanel cp = new GameColorPanel();
		colorPanel.add(cp.makeColorPanel(Level));
		
	}
	//기존 화면 삭제
	public void removeLevel(){
		
		colorPanel.removeAll();
		colorPanel.updateUI();
	}
	
	//2.새로 만들어서 추가 : 오류는 안났지만 안보임
	public void newLevel(){
		GameColorPanel cp = new GameColorPanel();
		colorPanel = new JPanel();
		colorPanel.add(cp.makeColorPanel(3));
		colorPanel.updateUI();
	}
}
