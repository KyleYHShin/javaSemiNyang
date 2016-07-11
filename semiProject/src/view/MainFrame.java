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

		this.setLayout(null);
		gamePanel.setSize(700, 850);
		gamePanel.setLocation(0, 0);
		gamePanel.add(gameView.makeGameView());
		this.add(gamePanel);

		this.setLayout(null);
		rankPanel.setSize(300, 650);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
		this.add(rankPanel);

		this.setLayout(null);
		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(700, 650);
		buttonPanel.setBackground(Color.BLUE);
		buttonPanel.add(buttonView.makeButtonView());
		this.add(buttonPanel);
		
		
		this.setVisible(true);
	}
	
}
