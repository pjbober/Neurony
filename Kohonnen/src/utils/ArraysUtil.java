package utils;

public class ArraysUtil {
	public static String toString(double... array){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append('[');
		
		for(double d : array){
			buffer.append(d);
			buffer.append(", ");
		}
		
		buffer.delete(buffer.length() - 2, buffer.length());
		
		buffer.append(']');
		
		return buffer.toString();
	}
	
	/**
	 * Returns array that is copied count times, i.e. when the array is
	 * [1,2,3] and count is 3, we get: [1,2,3,1,2,3,1,2,3]
	 * @param array Given array to copy
	 * @param count Number of copies
	 * @return Copied array
	 */
	public static double[] multiplyArray(double[] array, int count){
		double []ret = new double[array.length * count];
		
		int index = 0;
		
		for(int i=0;i<count;i++){
			for(int j=0;j<array.length;j++){
				ret[index] = array[j];
				index ++;
			}
		}
		
		return ret;
	}
}
