package view;

import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel{
	private JPanel gamePanel;
	private JPanel scorePanel;
	private JPanel colorPanel;
	private JPanel progressbarPanel;
	
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

	colorPanel = new JPanel();
	this.setLayout(null);
	colorPanel.setPreferredSize(new Dimension(600, 600));
	colorPanel.setLocation(50, 100);
	colorPanel.setBackground(Color.BLACK);

	progressbarPanel = new JPanel();
	this.setLayout(null);
	progressbarPanel.setPreferredSize(new Dimension(700, 150));
	progressbarPanel.setLocation(0, 700);
	progressbarPanel.setBackground(Color.RED);

	gamePanel.add(scorePanel);
	gamePanel.add(colorPanel);
	gamePanel.add(progressbarPanel);
	
	return gamePanel;
	}
}
