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
	// 각 객체 노드 저장
	private Linker link;
	private RankController rankController;

	private JPanel firstPane, secondPane, thirdPane, fourthPane;
	private ButtonGroup groupJRadioBtn;
	private JRadioButton jrBtnDaily, jrBtnWeekly, jrBtnMonthly;

	private String[] columnNames = { "랭킹", "닉네임", "점수", "날짜" };

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
		// 큰 패널 생성(반환되는 패널)
		// 크기 : 300 * 650
		JPanel rankPanel = new JPanel();
		rankPanel.setBounds(700, 0, 300, 650);
		rankPanel.setLayout(null);
		Border rankBorder = BorderFactory.createEtchedBorder();
		rankPanel.setBorder(rankBorder);

		// 1단 패널 : JRadioButton*3
		firstPane = new JPanel();
		firstPane.setBounds(10, 10, 275, 30);
		firstPane.setLayout(new FlowLayout());
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

		// 2단 패널 : JTable
		secondPane = new JPanel();
		secondPane.setBounds(5, 40, 275, 400);
		// 객체 생성
		rankTable = new JTable();
		// 서버에 있는 점수 목록 불러오기
		model = new DefaultTableModel(rankController.getRankList(jrBtnMonthly.getText(), true), columnNames);
		rankTable.setModel(model);
		// ■■■■■ 열 너비 편집 불가 설정 필요 ■■■■■
		// 열 너비 설정
		rankTable.setPreferredScrollableViewportSize(new Dimension(260, 370));
		rankTable.getColumn("랭킹").setPreferredWidth(20);
		rankTable.getColumn("닉네임").setPreferredWidth(80);
		rankTable.getColumn("점수").setPreferredWidth(70);
		rankTable.getColumn("날짜").setPreferredWidth(130);
		// 편집 불가
		rankTable.setEnabled(isValid());
		// 열 구분 제거
		rankTable.setShowVerticalLines(false);
		rankScroll = new JScrollPane(rankTable);
		// 2단 패널에 추가
		secondPane.add(rankScroll);

		// ■■■■■ 3단 : 사용자 닉네임이 기입된 경우 최고 순위 출력 ■■■■■
		thirdPane = new JPanel();
		thirdPane.setBounds(5, 440, 275, 80);
		// 객체 생성
		myTable = new JTable();
		// Object rowData1[][] = { { 1, "맛동산", 100, "오리온" } };
		myModel = new DefaultTableModel(null, columnNames);
		myTable.setModel(myModel);
		myTable.setPreferredScrollableViewportSize(new Dimension(260, 50));
		myTable.getColumn("랭킹").setPreferredWidth(20);
		myTable.getColumn("닉네임").setPreferredWidth(80);
		myTable.getColumn("점수").setPreferredWidth(70);
		myTable.getColumn("날짜").setPreferredWidth(130);
		myTable.setEnabled(isValid());
		myTable.setShowVerticalLines(false);
		myScroll = new JScrollPane(myTable);
		// 3단 패널에 추가
		thirdPane.add(myScroll);

		// 4단 : button*2 (nickName reader/refresh)
		fourthPane = new JPanel();
		fourthPane.setBounds(10, 530, 265, 80);
		fourthPane.setLayout(new GridLayout(1, 2, 10, 10));
		// 객체 생성
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

	public void addNewUser(String nickName, int score, long playTime) {
		this.myNickName = nickName;
		updateMainTable(rankController.updateNewUser(getCheckedButton().getText(), nickName, score, playTime));
	}

	private void updateMainTable(Object[][] data) {
		// 데이터 업데이트
		model = new DefaultTableModel(data, columnNames);
		rankTable.setModel(model);
		// 열 너비 설정
		rankTable.getColumn("랭킹").setPreferredWidth(20);
		rankTable.getColumn("닉네임").setPreferredWidth(80);
		rankTable.getColumn("점수").setPreferredWidth(70);
		rankTable.getColumn("날짜").setPreferredWidth(130);

		updateSubTable();
	}

	private void updateSubTable() {
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		Object[] temp = null;
		for (int i = 0; i < rankTable.getRowCount(); i++) {
			// 닉네임과 일치하는 것만
			if (rankTable.getValueAt(i, 1).equals(myNickName)) {
				temp = new Object[] { rankTable.getValueAt(i, 0), rankTable.getValueAt(i, 1),
						rankTable.getValueAt(i, 2), rankTable.getValueAt(i, 3) };
			}
			// 저장
			data.add(temp);
		}
		Object[][] myData = new Object[data.size()][4];
		for (int i = 0; i < data.size(); i++) {
			myData[i] = data.get(i);
		}

		myModel = new DefaultTableModel(myData, columnNames);
		myTable.setModel(myModel);
		// 열 너비 설정
		myTable.getColumn("랭킹").setPreferredWidth(20);
		myTable.getColumn("닉네임").setPreferredWidth(80);
		myTable.getColumn("점수").setPreferredWidth(70);
		myTable.getColumn("날짜").setPreferredWidth(130);
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

	// 내부클래스로 JRadioButton 이벤트 처리
	private class RankJRadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton jBtn = (JRadioButton) e.getSource();
			updateMainTable(rankController.getRankList(jBtn.getText(), false));
		}
	}

	// 하단 버튼
	public class RankButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton rBtn = (JButton) e.getSource();
			if (rBtn.getText().equals("닉네임 검색")) {
				String nickName = "";
				while (true) {
					nickName = JOptionPane.showInputDialog("닉네임을 입력해주세요.");
					// 취소 선택시
					if (nickName == null) {
						System.out.println("저장취소");
						break;
					}
					// 특정값을 입력하고 확인 선택시
					if (!nickName.equals("") && nickName != null) {
						setMyNickName(nickName);
						updateSubTable();
						break;
					}
				}
			} else if (rBtn.getText().equals("랭킹 업데이트")) {
				updateMainTable(rankController.getRankList(getCheckedButton().getText(), true));
			}
		}
	}

	public RankController getRankController() {
		return rankController;
	}
}
