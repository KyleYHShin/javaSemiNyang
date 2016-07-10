package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;

	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setMainFrame();
	}

	public MainFrame() {
	}

	public void setMainFrame() {
		// ������ â �ʱ�ȭ ----------------------------------------
		this.setTitle("�����϶�! ���� ����");
		this.setBounds(new Rectangle(50, 50, 1000, 700));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// ũ�� ���� �Ұ�

		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();

		// ��ġ������ ���� �����Ӱ� ���������� �����̳�
		this.setLayout(null);

		gamePanel.setSize(700, 700);
		gamePanel.setLocation(0, 0);
		// gamePanel.setBackground(Color.RED);// �׽�Ʈ
		// gamePanel.add(/*���� ������ UI ��ü ����*/);
		
		this.add(gamePanel);

		rankPanel.setSize(300, 500);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
//		rankPanel.add(new RankView().makeRankView());
		this.add(rankPanel);

		
		// buttonPanel.add(/*���� ������ UI ��ü ����*/);
		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(700, 500);
		buttonPanel.setBackground(Color.BLUE);
		
		buttonPanel.add(new ButtonView().makeButtonView());
		

		
		this.add(buttonPanel);

		this.setVisible(true);
	}
	
	

}