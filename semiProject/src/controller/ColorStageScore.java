package controller;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import view.*;

public class ColorStageScore extends JPanel {
	private JPanel colorPanel;
	private MainFrame mainFrame;
	// 시작 레벨
	private int level = 1;
	
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
		//////////////////////////////////////////////////////
		// 버튼 갯수 초기화
		JButton[] Btn = new JButton[level * level];
		// 정답버튼 결정
		Random r = new Random();
		int answer = (int) (r.nextInt(level * level));
		//색상 변경 및 정답 색상 결정 메소드 추가
		//Color selectColor =  SelectColor(level);
		//Color answerColor =  AnswerColor(SelectColor(level),level);
		// 이벤트 연결
		GameButtonActionListener1 btnListener1 = new GameButtonActionListener1();
		GameButtonActionListener2 btnListener2 = new GameButtonActionListener2();
		// 조건에 맞게 버튼 생성
		for (int i = 0; i < level * level; i++) {
			Btn[i] = new JButton();
			if (i == answer) {
				Btn[i].setBackground(Color.BLACK);
				//정답 색상
				//Btn[i].setBackground(answerColor);
				Btn[i].addActionListener(btnListener1);
			} else {
				Btn[i].setBackground(Color.YELLOW);
				//색상 결정
				//Btn[i].setBackground(selectColor);
				Btn[i].addActionListener(btnListener2);
				
			}
		colorPanel.add(Btn[i]);
		}
		return colorPanel;
	}
	//색상 선택 메소드 미완성
	public Color SelectColor(int level){
		Color color = new Color(level,level,level);
		return color;
	}
	//정답 색상 선택 메소드 미완성
	public Color AnswerColor(Color color,int level){
		color = new Color(level,level,level);
		return color;
	}
	
	// 내부클래스로 Button 이벤트 처리
	private class GameButtonActionListener1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonView buttonView = new ButtonView();
			SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
			e.getActionCommand();
			double clearTime = e.getWhen();
			String time = sdf.format(clearTime - buttonView.getStartTime());
			System.out.println("클리어 소요 시간 : "+Double.parseDouble(time));
			long score = (long) ((10-Double.parseDouble(time))*level*10);
			buttonView.setTotalScore(buttonView.getTotalScore()+score);
			System.out.println("점수 : " + buttonView.getTotalScore());
			System.out.println("level : " + level);
			buttonView.setStartTime((long)clearTime);
			level++;
			mainFrame.resetGamePanel(level);
			
		}

	}

	

	class GameButtonActionListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ButtonView buttonView = new ButtonView();
			e.getActionCommand();

			buttonView.setEndTime(e.getWhen());
			System.out.println(buttonView.getEndTime() + "게임 종료");

			int saveResult = JOptionPane.showConfirmDialog(colorPanel, "게임종료\n점수를 업로드 하시겠습니까?", "저장 확인",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			//유화 임시 작성 계산식
			Button buttonPanel = new Button();
			long score = buttonPanel.scoreCalc(buttonView.getLevel(), buttonView.getPlayTime(),
					buttonView.getStartTime(), buttonView.getEndTime());
			System.out.println(buttonView.getLevel() + "렙, 플레이" + buttonView.getPlayTime() + "타임, 스타트 : "
					+ buttonView.getStartTime() + "타임, 엔드 : " + buttonView.getEndTime());
			System.out.println("점수" + score);
			
			
			buttonView.setPlayTime(0);
			mainFrame.initializeGamePanel();
			buttonView.setStartTime(0);

			if (saveResult == JOptionPane.YES_OPTION) {
				
				// 마지막 점수계산
				// 게임 종료
				// 서버로 전송

			}
		}
	}
	}
	
