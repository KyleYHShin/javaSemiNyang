package controller;

public class Button {

	private long score = 0;
	int level;
	
	public Button(){}
	
	public long scoreCalc(int level, double clearTime, double startTime, double pauseTime){
		long totalPlayTime = 0;
		totalPlayTime +=(pauseTime - startTime); 
		
		//		    주어진시간	     clear에 소요시간                clear 당시 레벨
		//score += (15-(clearTime-startTime))*level
		
		return score;
		
	}
}
