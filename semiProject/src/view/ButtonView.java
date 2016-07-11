package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.event.*;

import controller.GameController;


public class ButtonView extends JPanel{
	private JPanel buttonPanel;
	private JToggleButton startBtn;
	private JButton exitBtn;
	private GameView gameView;

	public Component makeButtonView() {

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setSize(300, 200);
		
		//스타트 버튼...

		 startBtn = new JToggleButton();
		 
		  try {
		    Image tempStartImg = ImageIO.read(new File("image/play.png"));
		    Image startImg = tempStartImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		    startBtn.setIcon(new ImageIcon(startImg));
		    
		    Image tempPauseImg = ImageIO.read(new File("image/pause.png"));
		    Image pauseImg = tempPauseImg.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		    startBtn.setSelectedIcon(new ImageIcon(pauseImg));
		  } catch (Exception e) {
		  }
		  
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(125, 110));
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 40));
		
		MainFrame main = new MainFrame();
		startBtn.addActionListener(new ActionStartEventHandler(main));
		exitBtn.addActionListener(new ActionEventHandler(main));

		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);
		
		return buttonPanel;
	}
}

	class ActionEventHandler implements ActionListener {
	
		private JFrame parent;
		
		public ActionEventHandler(JFrame parent){
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
	
				int result = JOptionPane.showConfirmDialog(parent, "정말 종료하시겠습니까?");
				
				if(result == JOptionPane.YES_OPTION){
					System.exit(0); 
				}else{
				}
		}
	}
	
	
	class ActionStartEventHandler implements ActionListener {
	private JFrame parent;
	
	public ActionStartEventHandler(JFrame parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonView buttonView = new ButtonView();
		boolean pause = false;
		if (pause) {
			// 게임 중에 pause 기능
			pause = false;
			System.out.println("멈춤");			
			System.out.println("일시정시 시작" + e.getWhen());

		} else {
			// 여기부터 게임 시작
			System.out.println("스타트");
			pause = true;
			System.out.println("게임 시작 혹은 재시작 " + e.getWhen());
			
			new GameController().resetGamePanel();
		}		
	}

	/*// 내부클래스로 startButton 이벤트 처리  
		private class startButtonActionListener implements ActionListener {  
			@Override  
			public void actionPerformed(ActionEvent e) {  
				setStartTime(e.getWhen());
				mainFrame.resetGamePanel(2);
				
		 	}  
		}  */
		
}
	