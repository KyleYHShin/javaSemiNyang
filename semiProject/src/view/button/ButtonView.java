package view.button;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import model.Linker;

public class ButtonView extends JPanel {
	// �� ��ü ��� ����
	private Linker link;
	
	private Image startImg;
	private Image pauseImg;

	private JToggleButton startBtn;
	private JButton exitBtn;

	boolean inGame;

	public ButtonView(Linker link) {
		this.link = link;
		this.link.setButtonView(this);

		inGame = false;
		// ���� ��ŸƮ ��ư �̹��� ó�� : ���� ����ϰ� ����
		try {
			startImg = ImageIO.read(new File("image/play.png")).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			pauseImg = ImageIO.read(new File("image/pause.png")).getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
		}
	}
	public JPanel makeButtonView() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 0, 265, 140);
		buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

		// ��ŸƮ ����
		startBtn = new JToggleButton();

		startBtn.setIcon(new ImageIcon(startImg));
		startBtn.setSelectedIcon(new ImageIcon(pauseImg));

		exitBtn = new JButton("Exit");
		exitBtn.setSize(100, 100);
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 40));

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
			if (JOptionPane.showConfirmDialog(link.getMainFrame(), "���� �����Ͻðڽ��ϱ�?", "���α׷� ����",
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
				link.getGameController().startSignal();
			}
			// start ���� : ������ ó�� ���� ��� / �Ͻ����� ���¿��� ���� ���(�����)
			else {
				inGame = true;
				link.getGameController().startSignal();
			}
		}

	}
}
