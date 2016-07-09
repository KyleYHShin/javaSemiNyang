package controller;

public class Controller {

	private long score = 0;
	int level;
	
	public Controller(){}
	
	public long scoreCalc(int level, long playtime, long startTime, long endTime){
		long totalPlayTime = 0;
		totalPlayTime += (playtime + (endTime - startTime)); 
		
		
		score += totalPlayTime*level;
		
		return score;
		
	}
}
