package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import controller.RankListController;

public class RankView extends JPanel {
	private RankListController rController;

	private JPanel firstPane, secondPane, thirdPane, fourthPane;
	private ButtonGroup groupJRadioBtn;
	private JRadioButton jrBtnDaily, jrBtnWeekly, jrBtnMonthly;

	private JTable rankTable;
	private JScrollPane rankScroll;
	private String[] columnNames = { "��ŷ", "�г���", "����", "��¥" };
	private Object[][] data;
	private DefaultTableModel model;

	private String myNickName = "";
	private JTable myTable;

	private JButton btnNickName, btnUpdate;

	public RankView() {
		rController = new RankListController();
	}
	
	public JPanel makeRankView() {
		// ū �г� ����(��ȯ�Ǵ� �г�)
		// ũ�� : 300 * 650
		JPanel rankPanel = new JPanel();
		rankPanel.setSize(300, 650);
		rankPanel.setLayout(new FlowLayout());

		// 1�� �г� : JRadioButton*3
		firstPane = new JPanel();
		// first.setLayout(new GridLayout(1, 3));
		firstPane.setPreferredSize(new Dimension(300, 30));
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

		// 2�� �г� : JTable (����� �г���ĭ�� ���� ó��?)
		secondPane = new JPanel();
		secondPane.setPreferredSize(new Dimension(300, 400));
		// ��ü ����
		rankTable = new JTable();
		
		// controller�� �ִ� ���� ��� �ҷ�����
		// data = rController.getRankList(jrBtnMonthly.getText(), false);
		data = rController.getRankList(jrBtnMonthly.getText(), true);
		
		model = new DefaultTableModel(data, columnNames);
		rankTable.setModel(model);

		rankTable.getColumn("��ŷ").setPreferredWidth(10);
		rankTable.getColumn("�г���").setPreferredWidth(20);
		rankTable.getColumn("����").setPreferredWidth(30);
		rankTable.getColumn("��¥").setPreferredWidth(40);
		// ���� �Ұ�
		rankTable.setEnabled(isValid());
		// �� ���� ����
		rankTable.setShowVerticalLines(false);
		// ��ũ�ѹ� �����Ͽ� �߰�
		rankScroll = new JScrollPane(rankTable);
		// 2�� �гο� �߰�
		secondPane.add(rankScroll);

		// 3�� : ����� �г����� ���Ե� ��� �ְ� ���� ���
		thirdPane = new JPanel();
		thirdPane.setPreferredSize(new Dimension(300, 80));
		// ��ü ����
		Object rowData1[][] = { { 1, "������", 100, "������" } };
		myTable = new JTable(rowData1, columnNames);
		// �Ŀ� �� �ʺ� ���� �ʿ�
		// myTable.getColumn("��ŷ").setPreferredWidth(30);
		// myTable.getColumn("�г���").setPreferredWidth(70);
		// myTable.getColumn("����").setPreferredWidth(80);
		// myTable.getColumn("��¥").setPreferredWidth(100);
		myTable.setEnabled(isValid());
		myTable.setShowVerticalLines(false);
		// 3�� �гο� �߰�
		thirdPane.add(myTable);

		// 4�� : button*2 (nickName reader/refresh)
		fourthPane = new JPanel();
		fourthPane.setPreferredSize(new Dimension(300, 140));
		// ��ü ����
		fourthPane.setLayout(new GridLayout(1, 2));
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

	// ����Ŭ������ JRadioButton �̺�Ʈ ó��
	private class RankJRadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton jBtn = (JRadioButton) e.getSource();

			data = rController.getRankList(jBtn.getText(), false);
			model = new DefaultTableModel(data, columnNames);
			rankTable.setModel(model);
		}

	}
	
	//�̱���
	public class RankButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton rBtn = (JButton) e.getSource();
			if (rBtn.getText().equals("�г��� �˻�"))
				System.out.println(rBtn.getText());
			else if (rBtn.getText().equals("��ŷ ������Ʈ")){
				data = rController.getRankList(rBtn.getText(), false);
				model = new DefaultTableModel(data, columnNames);
				rankTable.setModel(model);
			}
		}

	}
}
