package utils;

import java.util.Arrays;
import java.util.List;

public class Lists {
	@SafeVarargs
	public static <T> List<T> listOf(T... t){
		return Arrays.asList(t);
	}
}
