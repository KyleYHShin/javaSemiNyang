package view;

import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel{
	JPanel colorPanel;
	
	public JPanel getColorPanel() {
		return colorPanel;
	}
	public void setColorPanel(JPanel colorPanel) {
		this.colorPanel = colorPanel;
	}
	
	public JPanel makeColorPanel(){
		colorPanel= new JPanel();
		colorPanel.setLayout(null);
		colorPanel.setSize(600, 600);
		colorPanel.setLocation(50, 100);
		colorPanel.setBackground(Color.BLACK);
		return colorPanel;
	}
}
