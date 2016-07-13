package view;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.game.GameView;
import view.rank.RankView;
import view.button.ButtonView;

import controller.game.GameController;
import model.Linker;

public class MainFrame extends JFrame {
	//�� ��ü ��� ����
	private Linker link;
	
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;

	private GameView gameView;
	private RankView rankView;
	private ButtonView buttonView;
	
	private GameController gameController;

	public MainFrame() {
		link = new Linker();
		link.setMainFrame(this);
		
		gameController = new GameController(link);
		gameView = new GameView(link);
		buttonView = new ButtonView(link);
		rankView = new RankView(link);
	}

	public void setMainFrame() {
		// ������ â �ʱ�ȭ ----------------------------------------
		this.setTitle("�����϶�! ���� ����");
		this.setBounds(0, 0, 1015, 850);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 700, 850);
		gamePanel.add(link.getGameView().getGameTotalPanel());
		this.add(gamePanel);

		rankPanel = new JPanel();
		rankPanel.setLayout(new BorderLayout());
		rankPanel.setBounds(705, 10, 285, 620);
		rankPanel.add(rankView.makeRankView());
		this.add(rankPanel);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(705, 650, 285, 230);
		buttonPanel.add(buttonView.makeButtonView());
		this.add(buttonPanel);

		this.setVisible(true);
	}
	
	public GameController getGameController(){
		return gameController;
	}

	public GameView getGameView() {
		return gameView;
	}

	public RankView getRankView() {
		return rankView;
	}

	public ButtonView getButtonView() {
		return buttonView;
	}
}
