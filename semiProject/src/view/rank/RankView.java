package view.rank;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import controller.rank.RankController;
import model.Linker;

public class RankView extends JPanel {
	// �� ��ü ��� ����
	private Linker link;
	private RankController rankController;

	private JPanel firstPane, secondPane, thirdPane, fourthPane;
	private ButtonGroup groupJRadioBtn;
	private JRadioButton jrBtnDaily, jrBtnWeekly, jrBtnMonthly;

	private String[] columnNames = { "��ŷ", "�г���", "����", "��¥" };

	private JTable rankTable;
	private DefaultTableModel model;
	private JScrollPane rankScroll;

	private JTable myTable;
	private DefaultTableModel myModel;
	private JScrollPane myScroll;
	private String myNickName;

	private JButton btnNickName, btnUpdate;

	public RankView(Linker link) {
		this.link = link;
		this.link.setRankView(this);
		rankController = new RankController(link);
	}

	public JPanel makeRankView() {
		// ū �г� ����(��ȯ�Ǵ� �г�)
		// ũ�� : 300 * 650
		JPanel rankPanel = new JPanel();
		rankPanel.setBounds(700, 0, 300, 650);
		rankPanel.setLayout(null);
		Border rankBorder = BorderFactory.createEtchedBorder();
		rankPanel.setBorder(rankBorder);

		// 1�� �г� : JRadioButton*3
		firstPane = new JPanel();
		firstPane.setBounds(10, 10, 275, 30);
		firstPane.setLayout(new FlowLayout());
		// ��ü ����
		jrBtnMonthly = new JRadioButton("����", true);
		jrBtnWeekly = new JRadioButton("�ֺ�");
		jrBtnDaily = new JRadioButton("�Ϻ�");
		// JRadioButton �׷�ȭ
		groupJRadioBtn = new ButtonGroup();
		groupJRadioBtn.add(jrBtnMonthly);
		groupJRadioBtn.add(jrBtnWeekly);
		groupJRadioBtn.add(jrBtnDaily);
		// event ����
		RankJRadioButtonActionListener jbListener = new RankJRadioButtonActionListener();
		jrBtnMonthly.addActionListener(jbListener);
		jrBtnWeekly.addActionListener(jbListener);
		jrBtnDaily.addActionListener(jbListener);
		// 1�� �гο� �߰�
		firstPane.add(jrBtnMonthly);
		firstPane.add(jrBtnWeekly);
		firstPane.add(jrBtnDaily);

		// 2�� �г� : JTable
		secondPane = new JPanel();
		secondPane.setBounds(5, 40, 275, 400);
		// ��ü ����
		rankTable = new JTable();
		// ������ �ִ� ���� ��� �ҷ�����
		model = new DefaultTableModel(rankController.getRankList(jrBtnMonthly.getText(), true), columnNames);
		rankTable.setModel(model);
		// ������ �� �ʺ� ���� �Ұ� ���� �ʿ� ������
		// �� �ʺ� ����
		rankTable.setPreferredScrollableViewportSize(new Dimension(260, 370));
		rankTable.getColumn("��ŷ").setPreferredWidth(20);
		rankTable.getColumn("�г���").setPreferredWidth(80);
		rankTable.getColumn("����").setPreferredWidth(70);
		rankTable.getColumn("��¥").setPreferredWidth(130);
		// ���� �Ұ�
		rankTable.setEnabled(isValid());
		// �� ���� ����
		rankTable.setShowVerticalLines(false);
		rankScroll = new JScrollPane(rankTable);
		// 2�� �гο� �߰�
		secondPane.add(rankScroll);

		// ������ 3�� : ����� �г����� ���Ե� ��� �ְ� ���� ��� ������
		thirdPane = new JPanel();
		thirdPane.setBounds(5, 440, 275, 80);
		// ��ü ����
		myTable = new JTable();
		// Object rowData1[][] = { { 1, "������", 100, "������" } };
		myModel = new DefaultTableModel(null, columnNames);
		myTable.setModel(myModel);
		myTable.setPreferredScrollableViewportSize(new Dimension(260, 50));
		myTable.getColumn("��ŷ").setPreferredWidth(20);
		myTable.getColumn("�г���").setPreferredWidth(80);
		myTable.getColumn("����").setPreferredWidth(70);
		myTable.getColumn("��¥").setPreferredWidth(130);
		myTable.setEnabled(isValid());
		myTable.setShowVerticalLines(false);
		myScroll = new JScrollPane(myTable);
		// 3�� �гο� �߰�
		thirdPane.add(myScroll);

		// 4�� : button*2 (nickName reader/refresh)
		fourthPane = new JPanel();
		fourthPane.setBounds(10, 530, 265, 80);
		fourthPane.setLayout(new GridLayout(1, 2, 10, 10));
		// ��ü ����
		btnNickName = new JButton("�г��� �˻�");
		btnUpdate = new JButton("��ŷ ������Ʈ");
		// �̺�Ʈ ����
		RankButtonActionListener btnListener = new RankButtonActionListener();
		btnNickName.addActionListener(btnListener);
		btnUpdate.addActionListener(btnListener);
		// 4�� �гο� �߰�
		fourthPane.add(btnNickName);
		fourthPane.add(btnUpdate);

		rankPanel.add(firstPane);
		rankPanel.add(secondPane);
		rankPanel.add(thirdPane);
		rankPanel.add(fourthPane);

		return rankPanel;
	}

	public void addNewUser(String nickName, int score, long playTime) {
		this.myNickName = nickName;
		updateMainTable(rankController.updateNewUser(getCheckedButton().getText(), nickName, score, playTime));
	}

	private void updateMainTable(Object[][] data) {
		// ������ ������Ʈ
		model = new DefaultTableModel(data, columnNames);
		rankTable.setModel(model);
		// �� �ʺ� ����
		rankTable.getColumn("��ŷ").setPreferredWidth(20);
		rankTable.getColumn("�г���").setPreferredWidth(80);
		rankTable.getColumn("����").setPreferredWidth(70);
		rankTable.getColumn("��¥").setPreferredWidth(130);

		updateSubTable();
	}

	private void updateSubTable() {
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		Object[] temp = null;
		for (int i = 0; i < rankTable.getRowCount(); i++) {
			// �г��Ӱ� ��ġ�ϴ� �͸�
			if (rankTable.getValueAt(i, 1).equals(myNickName)) {
				temp = new Object[] { rankTable.getValueAt(i, 0), rankTable.getValueAt(i, 1),
						rankTable.getValueAt(i, 2), rankTable.getValueAt(i, 3) };
			}
			// ����
			data.add(temp);
		}
		Object[][] myData = new Object[data.size()][4];
		for (int i = 0; i < data.size(); i++) {
			myData[i] = data.get(i);
		}

		myModel = new DefaultTableModel(myData, columnNames);
		myTable.setModel(myModel);
		// �� �ʺ� ����
		myTable.getColumn("��ŷ").setPreferredWidth(20);
		myTable.getColumn("�г���").setPreferredWidth(80);
		myTable.getColumn("����").setPreferredWidth(70);
		myTable.getColumn("��¥").setPreferredWidth(130);
	}

	private void setMyNickName(String myNickName) {
		this.myNickName = myNickName;
	}

	private JRadioButton getCheckedButton() {
		JRadioButton bTn = null;

		if (jrBtnDaily.isSelected())
			bTn = jrBtnDaily;
		else if (jrBtnWeekly.isSelected())
			bTn = jrBtnWeekly;
		else
			bTn = jrBtnMonthly;

		return bTn;
	}

	// ����Ŭ������ JRadioButton �̺�Ʈ ó��
	private class RankJRadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton jBtn = (JRadioButton) e.getSource();
			updateMainTable(rankController.getRankList(jBtn.getText(), false));
		}
	}

	// �ϴ� ��ư
	public class RankButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton rBtn = (JButton) e.getSource();
			if (rBtn.getText().equals("�г��� �˻�")) {
				String nickName = "";
				while (true) {
					nickName = JOptionPane.showInputDialog("�г����� �Է����ּ���.");
					// ��� ���ý�
					if (nickName == null) {
						System.out.println("�������");
						break;
					}
					// Ư������ �Է��ϰ� Ȯ�� ���ý�
					if (!nickName.equals("") && nickName != null) {
						setMyNickName(nickName);
						updateSubTable();
						break;
					}
				}
			} else if (rBtn.getText().equals("��ŷ ������Ʈ")) {
				updateMainTable(rankController.getRankList(getCheckedButton().getText(), true));
			}
		}
	}

	public RankController getRankController() {
		return rankController;
	}
}
