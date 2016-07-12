package model;

import controller.game.GameController;
import view.MainFrame;
import view.game.GameView;
import view.game.GameTop;
import view.game.GameMid;
import view.game.GameBot;
import view.button.ButtonView;
import view.rank.RankView;

public class Linker {

	private MainFrame mainFrame;
	private GameController gameController;
	private GameView gameView;
	private GameTop gameTop;
	private GameMid gameMid;
	private GameBot gameBot;
	private ButtonView buttonView;
	private RankView rankView;

	public Linker() {
		// TODO Auto-generated constructor stub
	}

	public Linker(MainFrame mainFrame, GameController gameController, GameView gameView, GameTop gameTop,
			GameMid gameMid, GameBot gameBot, ButtonView buttonView) {
		super();
		this.mainFrame = mainFrame;
		this.gameController = gameController;
		this.gameView = gameView;
		this.gameTop = gameTop;
		this.gameMid = gameMid;
		this.gameBot = gameBot;
		this.buttonView = buttonView;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public GameController getGameController() {
		return gameController;
	}

	public GameView getGameView() {
		return gameView;
	}

	public GameTop getGameTop() {
		return gameTop;
	}

	public GameMid getGameMid() {
		return gameMid;
	}

	public GameBot getGameBot() {
		return gameBot;
	}

	public ButtonView getButtonView() {
		return buttonView;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	public void setGameTop(GameTop gameTop) {
		this.gameTop = gameTop;
	}

	public void setGameMid(GameMid gameMid) {
		this.gameMid = gameMid;
	}

	public void setGameBot(GameBot gameBot) {
		this.gameBot = gameBot;
	}

	public void setButtonView(ButtonView buttonView) {
		this.buttonView = buttonView;
	}

	public RankView getRankView() {
		return rankView;
	}

	public void setRankView(RankView rankView) {
		this.rankView = rankView;
	}

	
}
