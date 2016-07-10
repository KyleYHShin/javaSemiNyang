package view;

import javax.swing.*;

public class TimeBar extends JProgressBar{
	
	JProgressBar timeBar;
	private long nowTime;

	public JProgressBar setTime(long nowTime){
		timeBar = new JProgressBar(0, 100);
		timeBar.setBounds(10, 10, 200, 30); 

		timeBar.setValue(100);
		timeBar.setStringPainted(false);
		setSize(400, 400);

		return timeBar;
	}
	
	public void setNowTime(long nowTime){
		this.nowTime = nowTime;
	}
	
	public long getNowTime(){
		return this.nowTime;
	}
}
