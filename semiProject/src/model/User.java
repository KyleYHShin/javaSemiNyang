package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class User {

	private GregorianCalendar date;
	private String nickName;
	private int score;
	
	public User() {		
	}

	public User(GregorianCalendar date, String nickName, int score) {
		super();
		this.date = date;
		this.nickName = nickName;
		this.score = score;
	}	

	@Override
	public String toString() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yy:MM:dd-hh:mm:ss");
		Date d = date.getTime();
		return "My name is yuhwa"+myFormat.format(d) + "," + nickName + "," + score;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public String getNickName() {
		return nickName;
	}

	public int getScore() {
		return score;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
