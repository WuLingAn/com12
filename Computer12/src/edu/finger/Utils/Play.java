package edu.finger.Utils;
/**
 * {
	"round":��ǰ�ֵı��,
	"play":"��ȭ�ַ����������ܵõ����ֽ�����ʹ��BASE64����",
	"sign":"ʹ��ǩ��˽Կ��������ȭ�������ݽ����ټ��ܵõ����ֽ�����ʹ��BASE64���룻���ֽ�������ǩ����Կ���ܿɵõ�������ȭ��������"
}
 * @author zLing
 *
 */
public class Play {
	/**
	 * ��ǰ�ֵı��
	 */
	private int round;
	/**
	 * ��ȭ�ַ����������ܵõ����ֽ�����ʹ��BASE64����
	 */
	private String play;
	/**
	 * ʹ��ǩ��˽Կ��������ȭ�������ݽ����ټ��ܵõ����ֽ�����ʹ��BASE64���룻���ֽ�������ǩ����Կ���ܿɵõ�������ȭ��������
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
