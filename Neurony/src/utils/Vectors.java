package utils;

import exceptions.BadVectorDimensionException;

public class Vectors {
	public static double multiply(double[] vector1, double[] vector2)
			throws BadVectorDimensionException {
		if(vector1.length != vector2.length){
			throw new BadVectorDimensionException("Vectors have different sizes");
		}
		
		double value = 0;
		
		for(int i=0;i<vector1.length;i++){
			value += vector1[i] * vector2[i]; 
		}
		
		return value;
	}

	public static double[] multiply(double[] input, double delta) {
		double []ret = new double[input.length];
		
		for(int i=0;i<input.length;i++){
			ret[i] = input[i] * delta;
		}
		
		return ret;
	}
}