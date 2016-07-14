package view.game;

import javax.swing.*;

import controller.game.GameController;
import view.MainFrame;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class GameMid extends JPanel {
	private final ImageIcon GAME_MAIN = new ImageIcon("image/Game_Main.jpg");
	private final ImageIcon GAME_CLEAR = new ImageIcon("image/Game_Clear.jpg");
	private final ImageIcon GAME_OVER = new ImageIcon("image/Game_Over.jpg");
	private final ImageIcon GAME_PAUSE = new ImageIcon("image/Game_Pause.jpg");

	private GameController gameController;

	private JPanel midPanel;
	private JPanel tempMidPanel;

	private JPanel leftSide;
	private JPanel middleSide;
	private JPanel rightSide;

	private final int WRONG_ANSWER = 0; // 오답

	public GameMid(MainFrame mainFrame) {
		gameController = mainFrame.getGameController();

		midPanel = new JPanel();
		midPanel.setLayout(null);
		midPanel.setBounds(0, 0, 700, 750);

		// ■■■ 좌측사이드 추가 ■■■
		leftSide = new JPanel();
		leftSide.setBounds(0, 150, 50, 600);

		middleSide = new JPanel();
		middleSide.setBounds(50, 150, 600, 600);

		// ■■■ 우측사이드 추가 ■■■
		rightSide = new JPanel();
		rightSide.setBounds(650, 150, 50, 600);

		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
	}

	// default 화면(초기화) 설정
	public JPanel setDefaultScreen() {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_MAIN.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);
		middleSide.setBackground(Color.RED);

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// game -> pause 화면 설정
	public JPanel getPauseScreen() {
		// 기존 middle 화면 저장
		tempMidPanel = middleSide;

		// 화면 설정
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_PAUSE.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// pause -> game 화면 설정
	public JPanel getPreScreen() {
		// 저장한 middle 화면 불러오기
		middleSide = tempMidPanel;

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// game 시작 화면 설정
	public JPanel getGameScreen(int level) {
		middleSide = new JPanel();
		middleSide.setBounds(50, 150, 600, 600);
		middleSide = makeBlockPanel(level);

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// Fail 화면 설정
	public JPanel getFailScreen(int score) {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_OVER.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// Clear 화면 설정
	public JPanel getSuccessScreen(int score) {
		middleSide = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(GAME_CLEAR.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		middleSide.setBounds(50, 150, 600, 600);

		// 재설정
		midPanel.removeAll();
		midPanel.add(leftSide);
		midPanel.add(middleSide);
		midPanel.add(rightSide);
		midPanel.revalidate();
		midPanel.repaint();

		return midPanel;
	}

	// 나뉜 블럭이 있는 화면 설정
	private JPanel makeBlockPanel(int level) {
		// ■■■ (현재 최대레벨 제한으로 필요 없음)일정레벨 이상일 때 칸 갯수 더이상 증가 못하게 ■■■

		// level에 따른 설정
		int block = level / 2 + 2;
		int blockNum = block * block;
		int blockTerm = 25 - level;

		// 게임용 패널 생성
		JPanel blockPanel = new JPanel();
		blockPanel = new JPanel();
		blockPanel.setLayout(new GridLayout(block, block, blockTerm, blockTerm));
		blockPanel.setBounds(50, 150, 600, 600);

		// 블럭 색상 선택
		final int MAX_HALF = 128;
		int difColor = 5 + 110 / level;
		int red, green, blue;
		Color wrongColor, rightColor;

		Random r = new Random();

		red = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		green = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		blue = r.nextInt(MAX_HALF - difColor) + MAX_HALF;
		
		if (r.nextBoolean()) {
			wrongColor = new Color(red + difColor, green + difColor, blue + difColor);
		} else {
			wrongColor = new Color(red - difColor, green - difColor, blue - difColor);
		}
		rightColor = new Color(red, green, blue);

		// 버튼 갯수 초기화
		JButton[] Btn = new JButton[blockNum];
		// 정답버튼 결정
		int answer = (int) (new Random().nextInt(blockNum));

		// 이벤트 연결
		RightButtonActionListener rightBtnListener = new RightButtonActionListener();
		WrongButtonActionListener wrongBtnListener = new WrongButtonActionListener();

		// 조건에 맞게 버튼 생성
		for (int i = 0; i < blockNum; i++) {
			Btn[i] = new JButton();
			if (i == answer) {
				Btn[i].setBackground(rightColor);
				Btn[i].addActionListener(rightBtnListener);
			} else {
				Btn[i].setBackground(wrongColor);
				Btn[i].addActionListener(wrongBtnListener);
			}
			blockPanel.add(Btn[i]);
		}
		return blockPanel;
	}

	// 내부클래스로 Button 이벤트 처리
	private class RightButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameController.Sound("bgm/Click.wav").start();
			gameController.clearLevel(e.getWhen());
		}

	}

	private class WrongButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameController.Sound("bgm/Click.wav").start();
			gameController.endGame(WRONG_ANSWER);
		}
	}
}
