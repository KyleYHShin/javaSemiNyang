package view.game;

import javax.swing.*;

import model.Linker;

public class GameView extends JPanel {
	// 각 객체 노드 저장
	private Linker link;

	private JPanel gameTotalPanel;
	private JPanel gameTopPanel;
	private JPanel gameMidPanel;
	private JPanel gameBotPanel;

	private GameTop gameTop;
	private GameMid gameMid;
	private GameBot gameBot;

	public GameView(Linker link) {
		this.link = link;
		this.link.setGameView(this);

		gameTop = new GameTop(this.link);
		gameMid = new GameMid(this.link);
		gameBot = new GameBot(this.link);

		gameTotalPanel = new JPanel();
		gameTotalPanel.setLayout(null);
		gameTotalPanel.setBounds(0, 0, 700, 850);

		gameTopPanel = gameTop.setDefaultScreen();
		gameMidPanel = gameMid.setDefaultScreen();
		gameBotPanel = gameBot.setDefaultScreen();

		gameTotalPanel.add(gameTopPanel);
		gameTotalPanel.add(gameMidPanel);
		gameTotalPanel.add(gameBotPanel);
	}
	
	public void setMidPause() {
		gameMidPanel = link.getGameMid().getPauseScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}

	public void setMidPre() {
		gameMidPanel = link.getGameMid().getPreScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}

	public void setMidLevel(int level) {
		gameMidPanel = link.getGameMid().getGameScreen(level);
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}
	public void setMidSuccess(int score){
		gameMidPanel = link.getGameMid().getSuccessScreen(score);
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}
	public void setMidFail(int score){
		gameMidPanel = link.getGameMid().getFailScreen(score);
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}

	public JPanel getGameTotalPanel() {
		return gameTotalPanel;
	}
}
