package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import view.*;

public class GameController extends JPanel {
	
	private int level = 1;
	private long totalscore = 0;
	private GameView gameView;
	private JPanel gameScreenPanel;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(long totalscore) {
		this.totalscore = totalscore;
	}
	public JPanel makeGameScreenPanel(int newLevel) {
		this.level = newLevel;
		gameScreenPanel = new JPanel();
		gameScreenPanel.setSize(600, 600);
		gameScreenPanel.setLocation(0, 0);
		gameScreenPanel.setLayout(new GridLayout(level, level));
		
		JButton[] Btn = new JButton[level * level];
		// 정답버튼 결정
		Random r = new Random();
		int answer = (int) (r.nextInt(level * level));
		// 이벤트 연결
		GameButtonActionListener1 btnListener1 = new GameButtonActionListener1();
		GameButtonActionListener2 btnListener2 = new GameButtonActionListener2();
		// 조건에 맞게 버튼 생성
		for (int i = 0; i < level * level; i++) {
			Btn[i] = new JButton();

			if (i == answer) {
				
				Btn[i].setBackground(Color.BLACK);
				Btn[i].addActionListener(btnListener1);
			} else {

				Btn[i].setBackground(Color.YELLOW);
				Btn[i].addActionListener(btnListener2);
				
			}
			gameScreenPanel.add(Btn[i]);
		}
		return gameScreenPanel;
	}

	
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
			//각 스테이지 계산 처리는 되는데 합산이 안됨
			e.getActionCommand();
			long clearTime = e.getWhen();
			String time = sdf.format(clearTime - mainFrame.getStartTime());
			System.out.println("클리어 소요 시간 : "+Double.parseDouble(time));
			long score = (long)((10.000-Double.parseDouble(time))*level*100);
			//여기서부터 더해주는건데 안됨 ㅠ
			setTotalscore(getTotalscore()+score);
			System.out.println("level : "+ level);
			mainFrame.setStartTime(clearTime);
			level++;
			System.out.println("점수 : " + getTotalscore());
			// 기존에 있는 메인프레임의 화면갱신 메서드(resetGamePanel()) 호출
			mainFrame.resetGamePanel(level);*/
		}

	}

	

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
/*
			ButtonView buttonview = new ButtonView();
			e.getActionCommand();

			buttonview.setEndTime(e.getWhen());
			System.out.println(buttonview.getEndTime() + "게임 종료");

			int saveResult = JOptionPane.showConfirmDialog(colorPanel, "게임종료\n점수를 업로드 하시겠습니까?", "저장 확인",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			Controller controller = new Controller();
			long score = controller.scoreCalc(buttonview.getLevel(), buttonview.getPlayTime(),
					buttonview.getStartTime(), buttonview.getEndTime());
			System.out.println(buttonview.getLevel() + "렙, 플레이" + buttonview.getPlayTime() + "타임, 스타트 : "
					+ buttonview.getStartTime() + "타임, 엔드 : " + buttonview.getEndTime());
			System.out.println("점수" + score);
			buttonview.setPlayTime(0);
			mainFrame.initializeGamePanel();

			if (saveResult == JOptionPane.YES_OPTION) {
				// 마지막 점수계산
				// 게임 종료
				// 서버로 전송

			}
*/
		}
	}

	
	public void resetGamePanel() {
		
		
		gameView.setScreenPanel(makeGameScreenPanel(2));
		
		gameScreenPanel.revalidate();
		gameScreenPanel.repaint();
		
		
	}

	//
	public void initializeGamePanel() {
		/*// 메인화면으로 돌아가기
		// 게임 중이지 않을때의 초기화면 필요
		// (게임명이라던가 단순한 이미지 뿌리기라던가...)
		colorPanel.removeAll();
		colorPanel.revalidate();
		colorPanel.repaint();
		
	}
	*/
	}
}
