package edu.finger.Utils;

import java.io.UnsupportedEncodingException;

public class Result {

	private static int createResult(int p, int r, int s, double ran) {
		double sum = p + r + s;
		if(sum==0){
			return (int)(Math.random()*3);
		}
		double s_p = p / sum;
		double s_r = r / sum;

		double random = ran;

		if (random < s_p) {
			return 2;
		} else if (random < (s_p + s_r)) {
			return 0;
		} else
			return 1;
	}

	public static String outFinger1(int p, int r, int s) {
		try {
			String result = null;
			double ran=Math.random();
			switch (createResult(p, r, s,ran)) {
			case 0:
				result = "Paper";
				break;
			case 1:
				result = "Rock";
				break;
			case 2:
				result = "Scissors";
				break;
			}
			return new String(result.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String outFinger2(int p, int r, int s) {
		String result = null;
		double ran=Math.random();
		switch (createResult(p, r, s, ran)) {
		case 0:
			result = "Paper";
			break;
		case 1:
			result = "Rock";
			break;
		case 2:
			result = "Scissors";
			break;
		}
		return result;
	}
}
