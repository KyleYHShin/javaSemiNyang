package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.game.GameView;
import view.rank.RankView;
import view.button.ButtonView;

import controller.game.GameController;
import model.Linker;

public class MainFrame extends JFrame {
	//각 객체 노드 저장
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
		// 윈도우 창 초기화 ----------------------------------------
		this.setTitle("반응하라! 절대 색감");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		gamePanel = new JPanel();
		rankPanel = new JPanel();
		buttonPanel = new JPanel();

		gamePanel.setBounds(0, 0, 700, 850);
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.RED);// 테스트
		gamePanel.add(link.getGameView().getGameTotalPanel());
		this.add(gamePanel);

		buttonPanel.setBounds(700, 650, 300, 200);
		buttonPanel.setBackground(Color.BLUE);
		buttonPanel.add(buttonView.makeButtonView());
		this.add(buttonPanel);

		rankPanel.setLayout(new BorderLayout());
		rankPanel.setBounds(700, 0, 300, 650);
		rankPanel.setBackground(Color.GREEN);
		rankPanel.add(rankView.makeRankView());
		this.add(rankPanel);

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
