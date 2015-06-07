package edu.finger.Utils;

public class JudgeUtil {

	public static String judeg(String fingerFromSelf,String fingerFromOpposite){
		if (fingerFromSelf.equals(fingerFromOpposite))
			return "平局";
		if ("Paper".equals(fingerFromSelf)) {
			if ("Rock".equals(fingerFromOpposite))
				return "赢了";
			else if ("Scissors".equals(fingerFromOpposite))
				return "输了";
		}
		if ("Rock".equals(fingerFromSelf)) {
			if ("Scissors".equals(fingerFromOpposite))
				return "赢了";
			else if ("Paper".equals(fingerFromOpposite))
				return "输了";
		}
		if ("Scissors".equals(fingerFromSelf)) {
			if ("Paper".equals(fingerFromOpposite))
				return "赢了";
			else if ("Rock".equals(fingerFromOpposite))
				return "输了";
		}
		return "can't judge!!!";
	}
}
