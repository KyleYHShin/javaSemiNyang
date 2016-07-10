package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import controller.Button;
import controller.ColorStageScore;

public class ButtonView extends JFrame {
	private JToggleButton startBtn;
	private JButton exitBtn;
	private JPanel scorePanel;
	private JPanel progressbarPanel;
	
	private int level = 1;
	private int score = 0;
	private double totalScore = 0;
	private double startTime;
	private double playTime;
	private double pauseTime;
	private double endTime;

	
	
	public ButtonView() {
	}
	
	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	
	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}
	
		public void setLevel(int level) {
		this.level = level;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getPlayTime() {
		return playTime;
	}

	public void setPlayTime(double playTime) {
		this.playTime = playTime;
	}

	public double getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(double pauseTime) {
		this.pauseTime = pauseTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public JPanel makeScorePanel(){
		
		scorePanel = new JPanel();
		scorePanel.setLayout(null);
		scorePanel.setSize(700, 100);
		scorePanel.setLocation(0, 0);
		scorePanel.setBackground(Color.ORANGE);
		return scorePanel;
	}

	
public JPanel MakeProgressbarPanel(){
	
	progressbarPanel = new JPanel();

	progressbarPanel.setLayout(null);
	progressbarPanel.setSize(700, 150);
	progressbarPanel.setLocation(0, 700);
	progressbarPanel.setBackground(Color.RED);
return progressbarPanel;
}
	
	
	
	
	
	public JButton makeExitButton() {
		MainFrame main = new MainFrame();
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(200, 100));
		exitBtn.addActionListener(new ActionEventExitHandler(main));
		
		return exitBtn;
	}

	public JToggleButton makeStartButton() {
		MainFrame main = new MainFrame();
		startBtn = new JToggleButton();
		startBtn.setBackground(Color.blue);
		startBtn.addActionListener(new ActionEventStartHandler(main));
		ColorStageScore colorPanel = new ColorStageScore();
		startBtn.addActionListener(new startButtonActionListener());
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
			// ���� �߿� pause ���
			SimpleDateFormat sdf = new SimpleDateFormat("mm�� ss.SSS��");
			System.out.println("����");
			pause = false;
			buttonView.setPauseTime(e.getWhen());
			System.out.println("pauseTime : " + (buttonView.getPauseTime()));
			System.out.println("���� ��ŸƮŸ��" + buttonView.getStartTime());
			
			tempPlayTime += (buttonView.getPauseTime() - buttonView.getStartTime());
			buttonView.setPlayTime(tempPlayTime);
			System.out.println("�÷���Ÿ�� : " + sdf.format(buttonView.getPlayTime()));
			// �׽�Ʈ�� ����ð� ����
			// �׽�Ʈ�� �����ð� ���= e.getWhen();
			// Ư�� �޼��忡 ����ð� ����

		} else {
			// ������� ���� ����
			System.out.println("��ŸƮ");
			pause = true;
			buttonView.setStartTime(e.getWhen());
			System.out.println("��ŸƮ�ð�" + buttonView.getStartTime());
			// startTime, playTime-����
			// ������ �Ϸ�ÿ� playTime += listTime - startTime;
			// Ư�� �޼��忡 ����ð� ����
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
		int result = JOptionPane.showConfirmDialog(parent, "���� �����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (result == JOptionPane.NO_OPTION) {
		}
	}
	  
	}
  
// ����Ŭ������ startButton �̺�Ʈ ó��  
class startButtonActionListener implements ActionListener {  
	@Override  
	public void actionPerformed(ActionEvent e) {  
		ButtonView buttonView = new ButtonView();
		MainFrame mainFrame = new MainFrame();
		if(buttonView.getStartTime()==0){
		ColorStageScore colorPanel = new ColorStageScore(); 
		mainFrame.resetGamePanel(2);
		
		buttonView.setStartTime(0.001);
		}
		
		
 	}  
}


