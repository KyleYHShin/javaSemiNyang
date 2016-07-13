package model;

import controller.game.GameController;
import controller.rank.RankController;
import view.MainFrame;
import view.game.GameView;
import view.game.GameTop;
import view.game.GameMid;
import view.game.GameBot;
import view.button.ButtonView;
import view.rank.RankView;

public class Linker {

	private GameController gameController;
	private RankController rankController;
	private MainFrame mainFrame;
	private GameView gameView;
	private GameTop gameTop;
	private GameMid gameMid;
	private GameBot gameBot;
	private ButtonView buttonView;
	private RankView rankView;

	public Linker() {
		// TODO Auto-generated constructor stub
	}

	public Linker(GameController gameController, RankController rankController, MainFrame mainFrame, GameView gameView,
			GameTop gameTop, GameMid gameMid, GameBot gameBot, ButtonView buttonView, RankView rankView) {
		super();
		this.gameController = gameController;
		this.rankController = rankController;
		this.mainFrame = mainFrame;
		this.gameView = gameView;
		this.gameTop = gameTop;
		this.gameMid = gameMid;
		this.gameBot = gameBot;
		this.buttonView = buttonView;
		this.rankView = rankView;
	}

	public GameController getGameController() {
		return gameController;
	}

	public RankController getRankController() {
		return rankController;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
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

	public RankView getRankView() {
		return rankView;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setRankController(RankController rankController) {
		this.rankController = rankController;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
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

	public void setRankView(RankView rankView) {
		this.rankView = rankView;
	}

	
}
