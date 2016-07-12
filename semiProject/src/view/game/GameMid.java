package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Linker;
import java.util.*;

public class GameMid extends JPanel {
	// 각 객체 노드 저장
	private Linker link;

	private final int WRONG_ANSWER = 0; // 오답

	public GameMid(Linker link) {
		this.link = link;
		this.link.setGameMid(this);
	}

	// default 화면 설정
	public JPanel setDefaultScreen() {
		System.out.println("GameMid : default 화면 설정");
		JPanel defaultScreen = new JPanel();
		defaultScreen.setSize(600, 600);
		defaultScreen.setBackground(Color.BLACK);

		return defaultScreen;
	}

	// pause 화면 설정
	public JPanel setPauseScreen() {
		System.out.println("GameMid : pause 화면 설정");
		JPanel pauseScreen = new JPanel();
		pauseScreen.setSize(600, 600);
		pauseScreen.setBackground(Color.BLACK);

		return pauseScreen;
	}

	// Clear 화면 설정
	public JPanel setClearScreen(){
		System.out.println("GameMid : Clear 화면 설정");
		JPanel clearScreen = new JPanel();
		clearScreen.setSize(600, 600);
		clearScreen.setBackground(Color.BLACK);

		return clearScreen;		
	}

	// 나뉜 블럭이 있는 화면 설정
	public JPanel makeBlockPanel(int level) {
		// level에 따른 설정
		// ■■■ 일정레벨 이상일 때 칸 갯수 더이상 증가 못하게
		int block = level / 2 + 2;
		int blockNum = block * block;
		int blockTerm = 23 - level;
		// int difColor = (int) (100 - (985/13) *

		// 게임용 패널 생성
		JPanel blockPanel = new JPanel();
		blockPanel.setSize(600, 600);
		blockPanel.setLayout(new GridLayout(block, block, blockTerm, blockTerm));

		// ■■■ 색상 변경 알고리즘 추가-> Btn[i].setBackground(Color.BLACK);
		
		Random r = new Random(); 
		
		int difColor = 13-level/2; //임의로 설정 (20탄 진행동안 12~3까지 줄어들게)
		int red = r.nextInt(256-difColor);
		int green = r.nextInt(256-difColor);
		int blue = r.nextInt(256-difColor);
		// int difColor = (int) (100 - (985 / 13) * java.lang.Math.log((double)
		// level));
		
		Color wrongColor = new Color(red,green,blue);
		Color rightColor = new Color(red + difColor, green + difColor, blue + difColor);

		// 버튼 갯수 초기화
		JButton[] Btn = new JButton[blockNum];
		// 정답버튼 결정
		int answer = (int) (new Random().nextInt(blockNum));

		// ■■■ 이벤트 연결
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
			link.getGameController().clearLevel(e.getWhen());
		}

	}

	private class WrongButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			link.getGameController().endGame(WRONG_ANSWER);
		}
	}
}
