package edu.finger.Utils;

import com.google.gson.GsonBuilder;

import edu.finger.Security.AddRSA;
import edu.finger.Security.addAES;

public class JsonHelper {

	public static GsonBuilder gson = new GsonBuilder();

	// hello阶段的数据对象
	private static Hello sHello;
	private static Hello sRcvHello;
	private static Hello cHello;
	private static Hello cRcvHello;
	// play阶段的数据对象
	private static Play sPlay;
	private static Play sRcvPlay;
	private static Play cPlay;
	private static Play cRcvPlay;
	// 真的全用静态对象吗？
	// password阶段的数据对象
	private static Password sPassword;
	private static Password cPassword;
	private static Password sRcvPassword;
	private static Password cRcvPassword;

	public static AddRSA SaddRSA = new AddRSA();
	public static AddRSA CaddRSA = new AddRSA();

	// 解析服务器端接收到的Hello的json数据，【服务器端调用】
	public static void sRcvToHello(String sRcvJson) {
		sRcvHello = toHello(sRcvJson);
	}

	// 解析客户端接收到的hello的json数据，【客户端调用】
	public static void cRcvToHello(String cRcvJson) {
		cRcvHello = toHello(cRcvJson);
	}

	// hello阶段json数据解析
	public static Hello toHello(String json) {
		return (Hello) gson.create().fromJson(json, Hello.class);
	}

	// 将服务器端要发送的hello数据进行json封装，【服务器端调用】
	public static String sHelloToJson() {
		gson.disableHtmlEscaping();
		SaddRSA = new AddRSA();
		sHello = new Hello("com12-S", SaddRSA.strRasPublicKey);
		return gson.create().toJson(sHello);
	}

	// 将客户端要发送的hello数据进行json数据封装，【客户端调用】
	public static String cHelloToJson() {
		gson.disableHtmlEscaping();
		CaddRSA = new AddRSA();
		cHello = new Hello("com12-C", CaddRSA.strRasPublicKey);
		return gson.create().toJson(cHello);
	}

	// 解析服务器端接收到的paly的json数据，【服务器端调用】
	public static boolean sRcvtoPlay(String sRcvJson) {
		sRcvPlay = toPlay(sRcvJson);
		String ming = sRcvPlay.getPlay();
		String mi = sRcvPlay.getSign();
		if (SecurityHelper.DecRSA(SaddRSA, mi, sRcvHello.getPublicKey())
				.equals(ming)) {
			return true;
		}
		System.out.println("error sign 不匹配");
		return false;
	}

	// 解析客户端接收到的paly的json数据，【客户端调用】
	public static boolean cRcvtoPlay(String cRcvJson) {
		cRcvPlay = toPlay(cRcvJson);
		String ming = cRcvPlay.getPlay();
		String mi = cRcvPlay.getSign();
		if (ming.equals(SecurityHelper.DecRSA(CaddRSA, mi,
				cRcvHello.getPublicKey()))) {
			return true;
		}
		System.out.println("error sign 不匹配");
		return false;
	}

	// 解析服务器端接收到的Password的json数据，【服务器端调用】
	public static void sRcvtoPassoword(String sRcvJson) {
		sRcvPassword = toPassword(sRcvJson);
	}

	// 解析客户器端接收到的Password的json数据，【服务器端调用】
	public static void cRcvtoPassoword(String sRcvJson) {
		cRcvPassword = toPassword(sRcvJson);
	}

	// 将服务器端的要发送的paly数据进行json封装，【服务器端调用】
	public static String sPlaytoJson(int roundId, String src, addAES aes) {
		// 一次加密得到的数据
		String playToSend = SecurityHelper.playToSend(aes, src);
		// 二次加密后得到的数据
		String signToSend = SecurityHelper.signToSend(SaddRSA, playToSend);
		sPlay = new Play(roundId, playToSend, signToSend);
		return gson.create().toJson(sPlay);
	}

	// 将客户端要发送的play数据进行json封装，【客户端调用】
	public static String cPlaytoJson(int roundId, String src, addAES aes) {
		// 二次加密得到的数据
		String playToSend = SecurityHelper.playToSend(aes, src);
		// 三次加密后得到的数据
		String signToSend = SecurityHelper.signToSend(CaddRSA, playToSend);
		cPlay = new Play(roundId, playToSend, signToSend);
		return gson.create().toJson(cPlay);
	}

	// 将服务器端的要发送的Password数据进行json封装，【服务器端调用】
	public static String sPasswordtoJson(int roundId, String pwd) {
		sPassword = new Password(roundId, pwd);
		return gson.create().toJson(sPassword);
	}

	// 将客户端要发送的Password数据进行json封装，【客户端调用】
	public static String cPasswordtoJson(int roundId, String pwd) {
		cPassword = new Password(roundId, pwd);
		return gson.create().toJson(cPassword);
	}

	// Play阶段json数据的解析
	public static Play toPlay(String json) {
		return (Play) gson.create().fromJson(json, Play.class);
	}

	// password阶段json数据的解析
	public static Password toPassword(String json) {
		return (Password) gson.create().fromJson(json, Password.class);
	}

	public static Hello getsRcvHello() {
		return sRcvHello;
	}

	public static Hello getcRcvHello() {
		return cRcvHello;
	}

	public static Play getsRcPlay() {
		return sRcvPlay;
	}

	public static Play getcRcPlay() {
		return cRcvPlay;
	}

	public static Password getsRcvPassword() {
		return sRcvPassword;
	}

	public static Password getcRcvPassword() {
		return cRcvPassword;
	}

}
