package view.button;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.event.*;

import controller.game.GameController;
import model.Linker;
import view.MainFrame;

public class ButtonView extends JPanel {
	//각 객체 노드 저장
	private Linker link;
	
	private JToggleButton startBtn;
	Image startImg;
	Image pauseImg;
	
	private JButton exitBtn;
	
	boolean inGame;

	public ButtonView(Linker link) {
		this.link = link;
		this.link.setButtonView(this);
		
		inGame = false;
		// ■■■ 스타트 버튼 이미지 처리 : 좀더 깔끔하게 ■■■
		try {
			startImg = ImageIO.read(new File("image/play.png")).getScaledInstance(100, 100,
					java.awt.Image.SCALE_SMOOTH);
			pauseImg = ImageIO.read(new File("image/pause.png")).getScaledInstance(100, 100,
					java.awt.Image.SCALE_SMOOTH);

		} catch (Exception e) {
		}
	}

	public JPanel makeButtonView() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setSize(300, 200);

		// 스타트 생성
		startBtn = new JToggleButton();

		startBtn.setIcon(new ImageIcon(startImg));
		startBtn.setSelectedIcon(new ImageIcon(pauseImg));

		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(125, 110));
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 40));

		startBtn.addActionListener(new ActionStartEventHandler());
		exitBtn.addActionListener(new ActionEventHandler());

		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);

		return buttonPanel;
	}	
	
	// 게임 종료시 시작버튼 이미지 초기화
	public void setRefreshStartButton(){
		startBtn.setSelected(false);		
	}

	// Exit Button Action
	class ActionEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(link.getMainFrame(), "정말 종료하시겠습니까?");

			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	// Start & Pause Button Action
	class ActionStartEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// pause 실행 : 게임중(inGame==true)에 버튼을 누를 경우
			if (!inGame) {
				inGame = false;
				link.getGameController().startSignal();
			}
			// start 실행 : 게임을 처음 누를 경우 / 일시정지 상태에서 누를 경우(재시작)
			else {
				inGame = true;
				link.getGameController().startSignal();
			}
		}

	}
}
