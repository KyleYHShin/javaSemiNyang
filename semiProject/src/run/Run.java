package run;

import controller.game.GameController;

public class Run {
	public static void main(String[] args) {
		GameController game = new GameController();
		game.getMainFrame().setMainFrame();
	}
}
