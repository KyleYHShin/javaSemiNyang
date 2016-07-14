package view.button;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import view.MainFrame;

public class ButtonView extends JPanel {
	// �� ��ü ��� ����
	private MainFrame mainFrame;

	private Image startImg;
	private Image pauseImg;

	private JToggleButton startBtn;
	private JButton exitBtn;

	boolean inGame;

	public ButtonView(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		inGame = false;
		// ���� ��ŸƮ ��ư �̹��� ó�� : ���� ����ϰ� ����
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
		// ����ȭ
		startBtn.setOpaque(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setBorderPainted(false);

		exitBtn = new JButton("Exit");
		exitBtn.setSize(100, 100);
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 40));
		// ����ȭ
		exitBtn.setOpaque(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);

		startBtn.addActionListener(new ActionStartEventHandler());
		exitBtn.addActionListener(new ActionEventHandler());

		buttonPanel.add(startBtn);
		buttonPanel.add(exitBtn);

		return buttonPanel;
	}

	// ���� ����� ���۹�ư �̹��� �ʱ�ȭ
	public void setRefreshStartButton() {
		startBtn.setSelected(false);
	}

	// Exit Button Action
	class ActionEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(mainFrame, "���� �����Ͻðڽ��ϱ�?", "���α׷� ����",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	// Start & Pause Button Action
	class ActionStartEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// pause ���� : ������(inGame==true)�� ��ư�� ���� ���
			if (!inGame) {
				inGame = false;
				mainFrame.getGameController().startSignal();
			}
			// start ���� : ������ ó�� ���� ��� / �Ͻ����� ���¿��� ���� ���(�����)
			else {
				inGame = true;
				mainFrame.getGameController().startSignal();
			}
		}

	}
}
