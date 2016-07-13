package view.game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.*;

import model.Linker;

public class GameBot extends JProgressBar {
	// 각 객체 노드 저장
	private Linker link;
	private final int MAX_VALUE = 10000;

	private JPanel botView;
	private JProgressBar timeBar;
	
	private final String FIRE = "image/fire.gif";
	private ImageIcon img;
	private Image image;
	private JLabel imgBox;
	private JPanel fireView;

	public GameBot(Linker link) {
		this.link = link;
		this.link.setGameBot(this);

		// Image 처리
		img = new ImageIcon(FIRE);
//		img = new ImageIcon(this.getClass().getResource("fire.gif"));
		imgBox = new JLabel();
		imgBox.setIcon(img);
	}

	public JPanel setDefaultScreen() {

		botView = new JPanel();
		botView.setLayout(null);
		botView.setBounds(0, 750, 700, 100);

		timeBar = new JProgressBar(0, MAX_VALUE);
		timeBar.setBounds(60, 20, 590, 30);

		fireView = new JPanel();
		fireView.setLayout(new FlowLayout());
		fireView.setBounds(10, 5, 50, 50);

		botView.add(fireView);
		botView.add(timeBar);

		return botView;
	}
	public void setFire(){
		fireView.removeAll();
		fireView.add(imgBox);
		fireView.revalidate();
		fireView.repaint();
	}
	public void disFire(){
		fireView.removeAll();
		fireView.revalidate();
		fireView.repaint();		
	}

	// 남은 시간과 제한시간을 받아 화면 갱신
	public void setTime(long remainTime, long TIME_LIMIT) {
		timeBar.setValue((int) (((float) remainTime / (float) TIME_LIMIT) * MAX_VALUE));
	}
	//남은시간 스레드로 처리
}
