package controller.rank;

import java.util.Comparator;

import model.User;

public class DescRank implements Comparator{

	public DescRank(){}
	
	@Override
	public int compare(Object ob1, Object ob2) {		
		return ((User)ob2).getScore() - ((User)ob1).getScore();
	}
}
