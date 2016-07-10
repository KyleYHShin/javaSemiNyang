package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
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
	
	private JToggleButton startBtn;
	private JButton exitBtn;
	private JButton stageBtn;
	private JButton timeBtn;
	private JButton scoreBtn;
	
	
	public static void main(String[] args) {
		mainFrame = new MainFrame();
		mainFrame.setMainFrame();
	}

	public MainFrame() {
	}

	public void setMainFrame() {
		ButtonView buttonView = new ButtonView();
		GameView gameView = new GameView();
		
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
		
		
		startBtn = buttonView.makeStartButton();
		exitBtn = buttonView.makeExitButton();
		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);
		
		this.add(buttonPanel);
		
		
		scorePanel = buttonView.makeScorePanel();
		colorPanel = gameView.makeColorPanel();
		progressbarPanel = buttonView.MakeProgressbarPanel();
		
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);


		this.setVisible(true);
	}

	public void resetGamePanel(int level) {
		colorPanel.removeAll();
		colorPanel.add(new ColorStageScore().makeColorPanel(level, this));
		colorPanel.revalidate();
		colorPanel.repaint();
	}

	
	public void initializeGamePanel() {
		// 메인화면으로 돌아가기
		// 게임 중이지 않을때의 초기화면 필요
		// (게임명이라던가 단순한 이미지 뿌리기라던가...)
		colorPanel.removeAll();
		colorPanel.revalidate();
		colorPanel.repaint();
		
		
	}	

}
