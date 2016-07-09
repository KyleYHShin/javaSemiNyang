package controller;

import java.util.Comparator;

import model.User;

public class RankDescending implements Comparator{

	public RankDescending(){}
	
	@Override
	public int compare(Object ob1, Object ob2) {		
		return ((User)ob2).getScore() - ((User)ob1).getScore();
	}
}
