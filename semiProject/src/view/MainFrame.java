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
	// 객체 목록
	private GameController gameController;
	private GameView gameView;
	private RankView rankView;
	private ButtonView buttonView;

	// 내부 패널 목록
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;

	public MainFrame(GameController gameController) {
		this.gameController = gameController;

		gameView = new GameView(this);
		buttonView = new ButtonView(this);
		rankView = new RankView(this);
	}

	public void setMainFrame() {
		// 윈도우 창 초기화 ----------------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(0, 0, 1015, 850);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);

		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBounds(0, 0, 700, 850);
		gamePanel.add(gameView.getGameTotalPanel());
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

	public GameController getGameController() {
		return gameController;
	}

	public GameView getGameView() {
		return gameView;
	}

	public ButtonView getButtonView() {
		return buttonView;
	}

	public RankView getRankView() {
		return rankView;
	}
}
