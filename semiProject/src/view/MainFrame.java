package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
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
	
	//��ȭ ��ư �߰�
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
		
		
		
		// ��ȭ ��ư��� ��ư�гο� ����
		startBtn = bv.makeStartButton();
		startButtonActionListener startAction = new startButtonActionListener();  
		startBtn.addActionListener(startAction);  
		exitBtn = bv.makeExitButton();
		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);
		
		this.add(buttonPanel);
		
		
		// gamePanel �Ҽ� �гε� �ʱ�ȭ
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

		// gamePanel�� �߰�
		gamePanel.add(scorePanel);
		gamePanel.add(colorPanel);
		gamePanel.add(progressbarPanel);

		this.setVisible(true);
	}

	
	public void resetGamePanel(int level) {
		
		GameColorPanel gcp = new GameColorPanel();
		// �гο� �ִ� ��ü�� ��� ����
		colorPanel.removeAll();
		// �� ��ü(�г�) �����Ͽ� �߰�
		colorPanel.add(gcp.makeColorPanel(level, this));
		colorPanel.revalidate();
		colorPanel.repaint();
	}

	//
	public void initializeGamePanel() {
		// ����ȭ������ ���ư���
		// ���� ������ �������� �ʱ�ȭ�� �ʿ�
		// (���Ӹ��̶���� �ܼ��� �̹��� �Ѹ�������...)
		colorPanel.removeAll();
		colorPanel.setBackground(Color.BLACK);
		colorPanel.revalidate();
		colorPanel.repaint();
	}
	
	// ����Ŭ������ startButton �̺�Ʈ ó��  
	private class startButtonActionListener implements ActionListener {  
		@Override  
		public void actionPerformed(ActionEvent e) {  
		mainFrame.resetGamePanel(2);
		
	 	}  
	}  
		
}
