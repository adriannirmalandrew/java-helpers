package com.kingspammernerd.levenshtein;

public class Levenshtein {
	//Return minimum of three values:
	private static int minimum(int n1, int n2, int n3) {
		if(n1<=n2) {
			if(n3<=n1) return n3;
			else return n1;
		} else {
			if(n3<=n2) return n3;
			else return n2;
		}
	}
	
	//Calculate Levenshtein edit distance between two strings, with a choice to operate in case-insensitive mode:
	public static int distance(String string1, String string2, boolean caseInsensitive) {
		//Check if case-insensitive comparison is required:
		if(caseInsensitive) {
			string1=string1.toLowerCase();
			string2=string2.toLowerCase();
		}
		//length1 is the number of rows, length2 is the number of columns:
		int length1=string1.length()+1, length2=string2.length()+1;
		//Initialize matrix. Take string1 as the vertical string, string2 as the horizontal string:
		int[][] distances=new int[length1][length2];
		//First cell is zero:
		distances[0][0]=0;
		//Initialize first column:
		for(int i=1; i<length1; ++i) distances[i][0]=i;
		//Initialize first row:
		for(int i=1; i<length2; ++i) distances[0][i]=i;
		//Calculate initial edit costs:
		for(int row=1; row<length1; ++row) {
			for(int col=1; col<length2; ++col) {
				//If characters are equal, cost is zero, otherwise it is 1:
				if(string1.charAt(row-1)==string2.charAt(col-1))
					distances[row][col]=0;
				else distances[row][col]=1;
			}
		}
		/*
		Calculate edit costs:
		Set cell d[i][j] of the matrix equal to the minimum of:
		1) The cell immediately above plus 1: d[i-1][j] + 1.
		2) The cell immediately to the left plus 1: d[i][j-1] + 1.
		3) The cell diagonally above and to the left plus the cost: d[i-1][j-1] + cost.
		*/
		for(int row=1; row<length1; ++row) {
			for(int col=1; col<length2; ++col) {
				distances[row][col]=minimum(distances[row-1][col]+1, distances[row][col-1]+1, distances[row-1][col-1]+distances[row][col]);
			}
		}
		//Return Levenshtein distance:
		return distances[length1-1][length2-1];
	}
	
	//By default, let distance() operate in case-sensitive mode if called without third argument:
	public static int distance(String string1, String string2) {
		return distance(string1, string2, false);
	}
	
	//Main method, so that the utility can be used directly:
	public static void main(String[] args) {
		//Check arguments:
		if(args.length!=2) {
			System.out.println("Usage: Levenshtein <string1> <string2>");
			System.out.println("Output is the Levenshtein distance between string1 and string2.");
			System.exit(1);
		}
		//Print distance:
		System.out.println(distance(args[0], args[1]));
	}
}
