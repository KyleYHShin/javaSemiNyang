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
	//�� ��ü ��� ����
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
		// ���� ��ŸƮ ��ư �̹��� ó�� : ���� ����ϰ� ����
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

		// ��ŸƮ ����
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
	
	// ���� ����� ���۹�ư �̹��� �ʱ�ȭ
	public void setRefreshStartButton(){
		startBtn.setSelected(false);		
	}

	// Exit Button Action
	class ActionEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(link.getMainFrame(), "���� �����Ͻðڽ��ϱ�?");

			if (result == JOptionPane.YES_OPTION) {
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
