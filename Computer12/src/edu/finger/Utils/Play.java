package edu.finger.Utils;
/**
 * {
	"round":当前轮的编号,
	"play":"出拳字符串经过加密得到的字节流，使用BASE64编码",
	"sign":"使用签名私钥对上述出拳加密数据进行再加密得到的字节流，使用BASE64编码；此字节流经过签名公钥解密可得到上述出拳加密数据"
}
 * @author zLing
 *
 */
public class Play {
	/**
	 * 当前轮的编号
	 */
	private int round;
	/**
	 * 出拳字符串经过加密得到的字节流，使用BASE64编码
	 */
	private String play;
	/**
	 * 使用签名私钥对上述出拳加密数据进行再加密得到的字节流，使用BASE64编码；此字节流经过签名公钥解密可得到上述出拳加密数据
	 */
	private int sign;
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public String getPlay() {
		return play;
	}
	public void setPlay(String play) {
		this.play = play;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	
}
