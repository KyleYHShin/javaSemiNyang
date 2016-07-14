package view.game;

import javax.swing.*;
import view.MainFrame;

public class GameView extends JPanel {
	// 각 객체 노드 저장
	private GameTop gameTop;
	private GameMid gameMid;
	private GameBot gameBot;

	private JPanel gameTotalPanel;
	
	private JPanel gameTopPanel;
	private JPanel gameMidPanel;
	private JPanel gameBotPanel;

	public GameView(MainFrame mainFrame) {

		gameTop = new GameTop();
		gameMid = new GameMid(mainFrame);
		gameBot = new GameBot();

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
		gameMidPanel = gameMid.getPauseScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}

	public void setMidPre() {
		gameMidPanel = gameMid.getPreScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}

	public void setMidLevel(int level) {
		gameMidPanel = gameMid.getGameScreen(level);
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}
	public void setMidSuccess(){
		gameMidPanel = gameMid.getSuccessScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}
	public void setMidFail(){
		gameMidPanel = gameMid.getFailScreen();
		gameTotalPanel.revalidate();
		gameTotalPanel.repaint();
	}
	
	public GameTop getGameTop() {
		return gameTop;
	}

	public GameBot getGameBot() {
		return gameBot;
	}

	// 나중에 제거
	public JPanel getGameTotalPanel() {
		return gameTotalPanel;
	}
}
