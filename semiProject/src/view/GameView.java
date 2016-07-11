package view;

import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel{
	private JPanel gamePanel;
	private JPanel scorePanel;
	private JPanel screenPanel;
	private JPanel progressbarPanel;
	
	public JPanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(JPanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public JPanel getScorePanel() {
		return scorePanel;
	}

	public void setScorePanel(JPanel scorePanel) {
		this.scorePanel = scorePanel;
	}

	public JPanel getScreenPanel() {
		return screenPanel;
	}

	public void setScreenPanel(JPanel screenPanel) {
		this.screenPanel = screenPanel;
	}

	public JPanel getProgressbarPanel() {
		return progressbarPanel;
	}

	public void setProgressbarPanel(JPanel progressbarPanel) {
		this.progressbarPanel = progressbarPanel;
	}

	public Component makeGameView(){
	
	gamePanel = new JPanel();
	this.setLayout(null);
	gamePanel.setPreferredSize(new Dimension(700, 850));
	gamePanel.setLocation(0, 0);
	
	
	scorePanel = new JPanel();
	this.setLayout(null);
	scorePanel.setPreferredSize(new Dimension(700, 100));
	scorePanel.setLocation(0, 0);
	scorePanel.setBackground(Color.ORANGE);

	screenPanel = new JPanel();
	this.setLayout(null);
	screenPanel.setPreferredSize(new Dimension(600, 600));
	screenPanel.setLocation(50, 100);
	screenPanel.setBackground(Color.BLACK);

	progressbarPanel = new JPanel();
	this.setLayout(null);
	progressbarPanel.setPreferredSize(new Dimension(700, 150));
	progressbarPanel.setLocation(0, 700);
	progressbarPanel.setBackground(Color.RED);

	gamePanel.add(scorePanel);
	gamePanel.add(screenPanel);
	gamePanel.add(progressbarPanel);
	
	return gamePanel;
	}
}
