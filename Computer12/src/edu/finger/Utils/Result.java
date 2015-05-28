package edu.finger.Utils;

import java.io.UnsupportedEncodingException;


public class Result {

	private static int createResult(){
		
		return (int)(Math.random()*3);
	}
	
	public static String outFinger1(){
		try {
			String result=null;
			switch (createResult()) {
			case 0:
				result="Paper";
				break;
			case 1:
				result="Rock";
				break;
			case 2:
				result="Scissors";
				break;
			}
			return new String(result.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String outFinger2(){
		String result=null;
		switch (createResult()) {
		case 0:
			result="Paper";
			break;
		case 1:
			result="Rock";
			break;
		case 2:
			result="Scissors";
			break;
		}
		return result;
	}
}
