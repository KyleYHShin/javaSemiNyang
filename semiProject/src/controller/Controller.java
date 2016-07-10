package controller;

public class Controller {

	private long score = 0;
	int level;
	
	public Controller(){}
	
	public long scoreCalc(int level, long clearTime, long startTime, long pauseTime){
		long totalPlayTime = 0;
		totalPlayTime +=(pauseTime - startTime); 
		
		//		    주어진시간	     clear에 소요시간               clear 당시 레벨
		score -= ((15-(totalPlayTime))*level)/1000;
		
		return score;
		
	}
}
