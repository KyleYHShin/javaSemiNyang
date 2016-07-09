package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import controller.Controller;

public class ButtonView extends JFrame {

	private JToggleButton startBtn;
	private JButton endTempBtn;
	private JButton exitBtn;

	private long endTime;
	private int level = 1;
	private int score = 0;

	private static long startTime;
	private static long playTime;
	private long pauseTime;

	public ButtonView() {
	}

	public static long getStartTime() {
		return startTime;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public long getPlayTime() {
		return playTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public JButton makeEndTempBtn() {
		MainFrame main = new MainFrame();
		endTempBtn = new JButton("End");
		endTempBtn.addActionListener(new ActionEventHandler(main));
		return endTempBtn;
	}

	public JButton makeExitButton() {
		MainFrame main = new MainFrame();
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(150, 75));
		exitBtn.addActionListener(new ActionEventExitHandler(main));
		return exitBtn;
	}

	public JToggleButton makeStartButton() {
		MainFrame main = new MainFrame();
		startBtn = new JToggleButton();
		startBtn.setBackground(Color.blue);
		startBtn.addActionListener(new ActionEventStartHandler(main));
		try {
			Image tempStartImg = ImageIO.read(new File("image/play.png"));
			Image StartImg = tempStartImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			startBtn.setIcon(new ImageIcon(StartImg));

			Image tempPauseImg = ImageIO.read(new File("image/pause.png"));
			Image PauseImg = tempPauseImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			startBtn.setSelectedIcon(new ImageIcon(PauseImg));
		} catch (Exception e) {
		}

		return startBtn;
	}
}

class ActionEventStartHandler implements ActionListener {

	private JFrame parent;

	private boolean pause = false;
	private long tempPlayTime = 0;

	public ActionEventStartHandler(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ButtonView buttonView = new ButtonView();
		if (pause) {
			// 게임 중에 pause 기능
			System.out.println("멈춤");
			pause = false;
			buttonView.setPauseTime(e.getWhen());
			System.out.println("pauseTime : " + buttonView.getPauseTime());
			System.out.println("지난 스타트타임" + buttonView.getStartTime());
			tempPlayTime += buttonView.getPlayTime() + (buttonView.getPauseTime() - buttonView.getStartTime());
			buttonView.setPlayTime(tempPlayTime);
			System.out.println("플레이타임 : " + buttonView.getPlayTime());
			// 테스트용 현재시간 추출
			// 테스트용 현지시간 출력= e.getWhen();
			// 특정 메서드에 현재시간 전달

		} else {
			// 여기부터 게임 시작
			System.out.println("스타트");
			pause = true;
			buttonView.setStartTime(e.getWhen());
			System.out.println("스타트시간" + buttonView.getStartTime());
			// startTime, playTime-전달
			// 마지막 완료시에 playTime += listTime - startTime;
			// 특정 메서드에 현재시간 전달
		}

	}

}

//기존의 2개에서 Exit 떼어서 Exit 버튼 처리, 이거랑 재생버튼이랑 기능 합쳐야됨
class ActionEventHandler implements ActionListener {

	private JFrame parent;

	public ActionEventHandler(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ButtonView buttonview = new ButtonView();
		switch (e.getActionCommand()) {

		case "End":
			buttonview.setEndTime(e.getWhen());
			System.out.println(buttonview.getEndTime() + "게임 종료");

			int saveResult = JOptionPane.showConfirmDialog(parent, "저장하시겠습니까?", "저장 확인", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (saveResult == JOptionPane.YES_OPTION) {
				// 마지막 점수계산
				// 게임 종료
				Controller controller = new Controller();
				long score = controller.scoreCalc(buttonview.getLevel(), buttonview.getPlayTime(),
						buttonview.getStartTime(), buttonview.getEndTime());
				System.out.println(buttonview.getLevel() + "렙, 플레이" + buttonview.getPlayTime() + "타임, 스타트 : "
						+ buttonview.getStartTime() + "타임, 엔드 : " + buttonview.getEndTime());
				System.out.println("점수" + score);
			} else {
				buttonview.setPlayTime(0);
				// 게임 종료
			} // 게임 시작전 플레이 타임 초기화
			break;

		}
	}

	
}
class ActionEventExitHandler implements ActionListener {
	private JFrame parent;

	public ActionEventExitHandler(MainFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonView buttonview = new ButtonView();
		e.getActionCommand();
		int result = JOptionPane.showConfirmDialog(parent, "정말 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (result == JOptionPane.NO_OPTION) {
		}
	}
}

