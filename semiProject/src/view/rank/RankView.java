package view.rank;

import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import controller.rank.RankController;
import model.Linker;
import model.User;
import view.MainFrame;

public class RankView extends JPanel {
	//�� ��ü ��� ����
	private Linker link;
	private RankController rankController;

	private JPanel firstPane, secondPane, thirdPane, fourthPane;
	private ButtonGroup groupJRadioBtn;
	private JRadioButton jrBtnDaily, jrBtnWeekly, jrBtnMonthly;

	private JTable rankTable;
	private JScrollPane rankScroll;
	private String[] columnNames = { "��ŷ", "�г���", "����", "��¥" };
	private DefaultTableModel model;

	private String myNickName = "";
	private JTable myTable;

	private JButton btnNickName, btnUpdate;

	public RankView(Linker link) {
		this.link = link;
		this.link.setRankView(this);
		rankController = new RankController();
	}

	public JPanel makeRankView() {
		// ū �г� ����(��ȯ�Ǵ� �г�)
		// ũ�� : 300 * 650
		JPanel rankPanel = new JPanel();
		rankPanel.setSize(300, 650);
		rankPanel.setLayout(new FlowLayout());

		// 1�� �г� : JRadioButton*3
		firstPane = new JPanel();
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

		// 2�� �г� : JTable
		// ������ ����� �г���ĭ ���� ó�� ������
		secondPane = new JPanel();
		secondPane.setPreferredSize(new Dimension(300, 400));
		// ��ü ����
		rankTable = new JTable();
		// ������ �ִ� ���� ��� �ҷ�����
		model = new DefaultTableModel(rankController.getRankList(jrBtnMonthly.getText(), true), columnNames);
		rankTable.setModel(model);
		// ������ �� �ʺ� ���� �Ұ� ���� �ʿ� ������
		// �� �ʺ� ����
		rankTable.setPreferredScrollableViewportSize(new Dimension(300, 400));
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
		thirdPane.setPreferredSize(new Dimension(300, 80));
		// ��ü ����
		Object rowData1[][] = { { 1, "������", 100, "������" } };
		myTable = new JTable(rowData1, columnNames);
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
			updateJTable(rankController.getRankList(jBtn.getText(), false));
		}
	}

	private void updateJTable(Object[][] data) {
		model = new DefaultTableModel(data, columnNames);
		rankTable.setModel(model);
		// �� �ʺ� ����
		rankTable.getColumn("��ŷ").setPreferredWidth(20);
		rankTable.getColumn("�г���").setPreferredWidth(80);
		rankTable.getColumn("����").setPreferredWidth(70);
		rankTable.getColumn("��¥").setPreferredWidth(130);
		// ������ ����� �г����� �����ϴ� ĭ ���� ó�� ������
	}

	// ������ (�̱���) �г��� �˻� ������
	public class RankButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton rBtn = (JButton) e.getSource();
			// ������ �г��� ���� �̱��� ������ 
			if (rBtn.getText().equals("�г��� �˻�")) {
				// ������ �������� �� �ڱ� ��� ������Ʈ��(����ȭ�鿡�� ȣ���� ����) ������
				// ������ �Ŀ� ���� ������ ȭ������ ������Ʈ Ŭ���� ���� �� ���    ������ 
				User newUser = new User("newMember", 77777, new GregorianCalendar());
				updateJTable(rankController.updateNewUser(newUser));
				jrBtnMonthly.setSelected(true);
				////////////////////////////////////////////////////////////////
			} else if (rBtn.getText().equals("��ŷ ������Ʈ")) {
				updateJTable(rankController.getRankList(jrBtnMonthly.getText(), true));
				jrBtnMonthly.setSelected(true);
			}
		}

	}

	public RankController getRankController() {
		return rankController;
	}
}
