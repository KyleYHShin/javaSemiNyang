package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RankButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource();
		if (jb.getText().equals("닉네임 입력"))
			System.out.println("닉네임");
		else if (jb.getText().equals("Refresh"))
			System.out.println("Refresh");
	}

}
