package view;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
	private JPanel gamePanel;
	private JPanel rankPanel;
	private JPanel buttonPanel;

	public MainFrame() {
	}

	public void setMainFrame() {
		// ������ â �ʱ�ȭ ----------------------------------------
		this.setTitle("�����϶�! ���� ����");
		this.setBounds(new Rectangle(0, 0, 1000, 850));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// ����
		gamePanel = new JPanel();
		 rankPanel = new JPanel();
		buttonPanel = new JPanel();

		// ��ġ������ ���� �����Ӱ� ���������� �����̳�
		this.setLayout(null);

		gamePanel.setBounds(0, 0, 700, 850);
		gamePanel.setBackground(Color.RED);// �׽�Ʈ
		// gamePanel.add(/*���� ������ UI ��ü ����*/);
		this.add(gamePanel);

		rankPanel.setLayout(new BorderLayout());
		rankPanel.setBounds(700, 0, 300, 650);
		rankPanel.setBackground(Color.GREEN);
		rankPanel.add(new RankView().makeRankView());
		this.add(rankPanel);

		buttonPanel.setBounds(700, 650, 300, 200);
		buttonPanel.setBackground(Color.BLUE);
		// buttonPanel.add(/*���� ������ UI ��ü ����*/);
		this.add(buttonPanel);

		this.setVisible(true);
	}

}
