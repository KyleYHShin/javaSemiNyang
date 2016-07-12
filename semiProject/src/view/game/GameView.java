package view.game;

import java.awt.*;
import javax.swing.*;

import model.Linker;
import view.MainFrame;

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
	
	private JPanel tempPausePanel;

	public GameView(Linker link) {
		this.link = link;
		this.link.setGameView(this);

		gameTop = new GameTop(this.link);
		gameMid = new GameMid(this.link);
		gameBot = new GameBot(this.link);

		// Game Main Panel
		gameTotalPanel = new JPanel();
		gameTotalPanel.setLayout(null);
		gameTotalPanel.setSize(new Dimension(700, 850));
		gameTotalPanel.setLocation(0, 0);

		// 1.Top Panel
		gameTopPanel = new JPanel();
		gameTopPanel.setLayout(null);
		gameTopPanel.setSize(new Dimension(700, 150));
		gameTopPanel.setLocation(0, 0);
		gameTopPanel.setBackground(Color.ORANGE);
		// gameTopPanel.add();
		gameTotalPanel.add(gameTopPanel);

		// 2.Middle Panel
		gameMidPanel = new JPanel();
		gameMidPanel.setLayout(null);
		gameMidPanel.setSize(new Dimension(600, 600));
		gameMidPanel.setLocation(50, 150);
		gameMidPanel.setBackground(Color.BLACK);
		setMidDefault();
		gameTotalPanel.add(gameMidPanel);

		// 3.Bottom Panel
		gameBotPanel = new JPanel();
		gameBotPanel.setLayout(null);
		gameBotPanel.setSize(new Dimension(700, 100));
		gameBotPanel.setLocation(0, 750);
		gameBotPanel.setBackground(Color.GREEN);
		// gameTopPanel.add();
		gameTotalPanel.add(gameBotPanel);
	}

	public void setMidDefault() {
		gameMidPanel.removeAll();
		gameMidPanel.add(link.getGameMid().setDefaultScreen());
		gameMidPanel.revalidate();
		gameMidPanel.repaint();

	}

	public void setMidPause() {
		 gameMidPanel.removeAll();
		 gameMidPanel.add(link.getGameMid().setPauseScreen());
		 gameMidPanel.revalidate();
		 gameMidPanel.repaint();
	}

	public void resetPreMid() {
		 gameMidPanel.removeAll();
		 gameMidPanel.add(tempPausePanel);
		 gameMidPanel.revalidate();
		 gameMidPanel.repaint();
	}

	public void resetMidLevel(int level) {
		// 기존 화면내의 객체들 모두 제거
		gameMidPanel.removeAll();
		// Level에 맞는 화면 생성하여 추가
		tempPausePanel = link.getGameMid().makeBlockPanel(level);
		gameMidPanel.add(tempPausePanel);
		// 갱신
		gameMidPanel.revalidate();
		gameMidPanel.repaint();
	}

	public JPanel getGameTotalPanel() {
		return gameTotalPanel;
	}
}
