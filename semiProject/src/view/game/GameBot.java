package view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import model.Linker;

public class GameBot extends JProgressBar{
	//각 객체 노드 저장
	private Linker link;
	
	JPanel botView;
	JProgressBar timeBar;
	private long remainTime;
	
	public GameBot(Linker link){
		this.link = link;
		this.link.setGameBot(this);		
		
		botView = new JPanel();
		botView.setLayout(new FlowLayout());
		botView.setSize(new Dimension(700, 100));
		botView.setBackground(Color.RED);
		
		timeBar = new JProgressBar(0, 100);
		
		botView.add(timeBar);
	}
	
	public JPanel getBotView(){
		return botView;
	}
	
	
	
	
	
	// ■■■ 상수화 %를 받아 바로 적용 ■■■
	public void setTime(long nowTime){
		timeBar = new JProgressBar(0, 100);
		timeBar.setBounds(10, 10, 200, 30); 

		timeBar.setValue(100);
		timeBar.setStringPainted(false);
		setSize(400, 400);
	}
	public void resetTime(){
		
	}
	public void setNowTime(long nowTime){
		this.remainTime = nowTime;
	}
}
