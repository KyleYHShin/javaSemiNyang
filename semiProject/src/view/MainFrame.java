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
		// 윈도우 창 초기화 ----------------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(new Rectangle(50, 50, 1000, 700));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 크기 변경 불가

		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();

		// 배치관리자 없이 자유롭게 설정가능한 컨테이너
		this.setLayout(null);

		gamePanel.setSize(700, 700);
		gamePanel.setLocation(0, 0);
		// gamePanel.setBackground(Color.RED);// 테스트
		// gamePanel.add(/*각자 생성한 UI 객체 리턴*/);
		
		this.add(gamePanel);

		rankPanel.setSize(300, 500);
		rankPanel.setLocation(700, 0);
		rankPanel.setBackground(Color.GREEN);
//		rankPanel.add(new RankView().makeRankView());
		this.add(rankPanel);

		
		// buttonPanel.add(/*각자 생성한 UI 객체 리턴*/);
		buttonPanel.setSize(300, 200);
		buttonPanel.setLocation(700, 500);
		buttonPanel.setBackground(Color.BLUE);
		
		buttonPanel.add(new ButtonView().makeButtonView());
		

		
		this.add(buttonPanel);

		this.setVisible(true);
	}
	
	

}