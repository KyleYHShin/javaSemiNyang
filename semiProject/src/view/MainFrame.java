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

	// �ӽ� ����
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
		
		// ������ â �ʱ�ȭ --------------------------------
		this.setTitle("�����϶�! ���� ����");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// ũ�� ���� �Ұ�
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
		// ����ȭ������ ���ư���
		// ���� ������ �������� �ʱ�ȭ�� �ʿ�
		// (���Ӹ��̶���� �ܼ��� �̹��� �Ѹ�������...)
		colorPanel.removeAll();
		colorPanel.revalidate();
		colorPanel.repaint();
		
		
	}	

}
