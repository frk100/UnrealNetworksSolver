package gti310.tp3.solver;

public class Solution {

	private int[][] solution;
	private int depart;
	
	public Solution(int[][] solution, int depart){
		this.solution = solution;
		this.depart = depart;
	}
	
	public int[][] getSolution(){
		return solution;
	}
	
	public int getDepart(){
		return depart;
	}
	
}
