package com.kingspammernerd.shannon;

import java.util.*;

public class Shannon {
	//Find the Shannon Entropy of the given bytes:
	public static double entropy(byte[] data) {
		//HashMap, with each unique byte, and its number of occurences:
		HashMap occurences=new HashMap<Byte,Long>();
		for(byte b: data) {
			if(occurences.containsKey(b))
				occurences.put(b, (int)(occurences.get(b))+1);
			else
				occurences.put(b, 1);
		}
		//Calculate probabilities for each byte:
		HashMap probabilities=new HashMap<Byte, Double>();
		for(byte b: (Set<Byte>)occurences.keySet()) {
			double probability=(int)occurences.get(b);
			probability/=(double)data.length;
			probabilities.put(b, probability);
		}
		//Calculate entropy:
		double entropy=0, mul=1/Math.log(2);
		for(byte b: (Set<Byte>)probabilities.keySet()) {
			//Get probability:
			double prob=(double)probabilities.get(b);
			//Add probability + log2(1/probability):
			entropy+=prob*Math.log(1/prob)*mul;
		}
		return entropy;
	}
	
	//Main method, converts the given String argument to a byte array:
	public static void main(String[] args) {
		if(args.length==1) System.out.println(entropy(args[0].getBytes()));
	}
}
