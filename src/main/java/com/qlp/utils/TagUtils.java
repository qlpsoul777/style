package com.qlp.utils;

public class TagUtils {
	public static String BLOCK = "__override__";  
	public static String getOverrideVariableName(String name) {  
	  return BLOCK + name;  
	}  
}
