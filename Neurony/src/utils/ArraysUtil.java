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
}
