package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import view.*;

public class GameColorPanel extends JPanel {
	private JPanel colorPanel;
	private MainFrame mainFrame;
	// 시작 레벨
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public JPanel makeColorPanel(int newLevel, MainFrame getFrame) {
		this.level = newLevel;
		// 기존에 있는 메인프레임 레퍼런스 저장
		this.mainFrame = getFrame;
		// 게임용 패널 재생성
		colorPanel = new JPanel();
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(0, 0);
		colorPanel.setLayout(new GridLayout(level, level));
		//////////////////////////////////////////////////////
		// 일정레벨 이상일 때 칸 갯수 더이상 증가 못하게
		// 색상 변경 알고리즘 추가-> Btn[i].setBackground(Color.BLACK);
		//
		//////////////////////////////////////////////////////
		// 버튼 갯수 초기화
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
				// 버튼에 있는 text 안보이게 설정 필요

				Btn[i].setBackground(Color.BLACK);
				Btn[i].addActionListener(btnListener1);
			} else {

				Btn[i].setBackground(Color.YELLOW);
				Btn[i].addActionListener(btnListener2);
				ButtonView bv = new ButtonView();

				// 끝냈을 때 이벤트 연동 필요
			}
			colorPanel.add(Btn[i]);
		}
		return colorPanel;
	}

	// 내부클래스로 Button 이벤트 처리
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			e.getActionCommand();

			level++;
			
			// 기존에 있는 메인프레임의 화면갱신 메서드(resetGamePanel()) 호출
			mainFrame.resetGamePanel(level);
		}

	}

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

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

		}
	}
}
