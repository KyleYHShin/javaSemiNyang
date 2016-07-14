package view.button;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import view.MainFrame;

public class ButtonView extends JPanel {
	// 각 객체 노드 저장
	private MainFrame mainFrame;

	private Image startImg;
	private Image pauseImg;

	private JToggleButton startBtn;
	private JButton exitBtn;

	boolean inGame;

	public ButtonView(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		inGame = false;
		// ■■■ 스타트 버튼 이미지 처리 : 좀더 깔끔하게 ■■■
		try {
			startImg = ImageIO.read(new File("image/play.png")).getScaledInstance(120, 120,
					java.awt.Image.SCALE_SMOOTH);
			pauseImg = ImageIO.read(new File("image/pause.png")).getScaledInstance(120, 120,
					java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
		}
	}

	public JPanel makeButtonView() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 0, 265, 140);
		buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

		startBtn = new JToggleButton();
		startBtn.setIcon(new ImageIcon(startImg));
		startBtn.setSelectedIcon(new ImageIcon(pauseImg));
		startBtn.setOpaque(false);
		// 투명화
		startBtn.setOpaque(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(false);

		exitBtn = new JButton("Exit");
		exitBtn.setSize(100, 100);
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 40));
		// 투명화
		exitBtn.setOpaque(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);

		startBtn.addActionListener(new ActionStartEventHandler());
		exitBtn.addActionListener(new ActionEventHandler());

		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);

		return buttonPanel;
	}

	// 게임 종료시 시작버튼 이미지 초기화
	public void setRefreshStartButton() {
		startBtn.setSelected(false);
	}

	// Exit Button Action
	class ActionEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(mainFrame, "정말 종료하시겠습니까?", "프로그램 종료",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
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
				mainFrame.getGameController().startSignal();
			}
			// start 실행 : 게임을 처음 누를 경우 / 일시정지 상태에서 누를 경우(재시작)
			else {
				inGame = true;
				mainFrame.getGameController().startSignal();
			}
		}

	}
}
