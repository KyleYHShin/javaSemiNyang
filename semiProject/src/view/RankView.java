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
	private String[] columnNames = { "랭킹", "닉네임", "점수", "날짜" };
	private Object[][] data;
	private DefaultTableModel model;

	private String myNickName = "";
	private JTable myTable;

	private JButton btnNickName, btnUpdate;

	public RankView() {
		rController = new RankListController();
	}
	
	public JPanel makeRankView() {
		// 큰 패널 생성(반환되는 패널)
		// 크기 : 300 * 650
		JPanel rankPanel = new JPanel();
		rankPanel.setSize(300, 650);
		rankPanel.setLayout(new FlowLayout());

		// 1단 패널 : JRadioButton*3
		firstPane = new JPanel();
		// first.setLayout(new GridLayout(1, 3));
		firstPane.setPreferredSize(new Dimension(300, 30));
		// 객체 생성
		jrBtnMonthly = new JRadioButton("월별", true);
		jrBtnWeekly = new JRadioButton("주별");
		jrBtnDaily = new JRadioButton("일별");
		// JRadioButton 그룹화
		groupJRadioBtn = new ButtonGroup();
		groupJRadioBtn.add(jrBtnMonthly);
		groupJRadioBtn.add(jrBtnWeekly);
		groupJRadioBtn.add(jrBtnDaily);
		// event 연결
		RankJRadioButtonActionListener jbListener = new RankJRadioButtonActionListener();
		jrBtnMonthly.addActionListener(jbListener);
		jrBtnWeekly.addActionListener(jbListener);
		jrBtnDaily.addActionListener(jbListener);
		// 1단 패널에 추가
		firstPane.add(jrBtnMonthly);
		firstPane.add(jrBtnWeekly);
		firstPane.add(jrBtnDaily);

		// 2단 패널 : JTable (사용자 닉네임칸은 유색 처리?)
		secondPane = new JPanel();
		secondPane.setPreferredSize(new Dimension(300, 400));
		// 객체 생성
		rankTable = new JTable();
		
		// controller에 있는 점수 목록 불러오기
		// data = rController.getRankList(jrBtnMonthly.getText(), false);
		data = rController.getRankList(jrBtnMonthly.getText(), true);
		
		model = new DefaultTableModel(data, columnNames);
		rankTable.setModel(model);

		rankTable.getColumn("랭킹").setPreferredWidth(10);
		rankTable.getColumn("닉네임").setPreferredWidth(20);
		rankTable.getColumn("점수").setPreferredWidth(30);
		rankTable.getColumn("날짜").setPreferredWidth(40);
		// 편집 불가
		rankTable.setEnabled(isValid());
		// 열 구분 제거
		rankTable.setShowVerticalLines(false);
		// 스크롤바 적용하여 추가
		rankScroll = new JScrollPane(rankTable);
		// 2단 패널에 추가
		secondPane.add(rankScroll);

		// 3단 : 사용자 닉네임이 기입된 경우 최고 순위 출력
		thirdPane = new JPanel();
		thirdPane.setPreferredSize(new Dimension(300, 80));
		// 객체 생성
		Object rowData1[][] = { { 1, "맛동산", 100, "오리온" } };
		myTable = new JTable(rowData1, columnNames);
		// 후에 열 너비 설정 필요
		// myTable.getColumn("랭킹").setPreferredWidth(30);
		// myTable.getColumn("닉네임").setPreferredWidth(70);
		// myTable.getColumn("점수").setPreferredWidth(80);
		// myTable.getColumn("날짜").setPreferredWidth(100);
		myTable.setEnabled(isValid());
		myTable.setShowVerticalLines(false);
		// 3단 패널에 추가
		thirdPane.add(myTable);

		// 4단 : button*2 (nickName reader/refresh)
		fourthPane = new JPanel();
		fourthPane.setPreferredSize(new Dimension(300, 140));
		// 객체 생성
		fourthPane.setLayout(new GridLayout(1, 2));
		btnNickName = new JButton("닉네임 검색");
		btnUpdate = new JButton("랭킹 업데이트");
		// 이벤트 연결
		RankButtonActionListener btnListener = new RankButtonActionListener();
		btnNickName.addActionListener(btnListener);
		btnUpdate.addActionListener(btnListener);
		// 4단 패널에 추가
		fourthPane.add(btnNickName);
		fourthPane.add(btnUpdate);

		rankPanel.add(firstPane);
		rankPanel.add(secondPane);
		rankPanel.add(thirdPane);
		rankPanel.add(fourthPane);

		return rankPanel;
	}

	// 내부클래스로 JRadioButton 이벤트 처리
	private class RankJRadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton jBtn = (JRadioButton) e.getSource();

			data = rController.getRankList(jBtn.getText(), false);
			model = new DefaultTableModel(data, columnNames);
			rankTable.setModel(model);
		}

	}
	
	//미구현
	public class RankButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton rBtn = (JButton) e.getSource();
			if (rBtn.getText().equals("닉네임 검색"))
				System.out.println(rBtn.getText());
			else if (rBtn.getText().equals("랭킹 업데이트")){
				data = rController.getRankList(rBtn.getText(), false);
				model = new DefaultTableModel(data, columnNames);
				rankTable.setModel(model);
			}
		}

	}
}
