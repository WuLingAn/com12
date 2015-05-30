package edu.finger.Utils;

import com.google.gson.Gson;

import edu.finger.Security.AddRSA;

public class JsonHelper {

	public static Gson gson = new Gson();
	//hello阶段的数据对象
	private static Hello sHello;
	private static Hello sRcvHello;
	private static Hello cHello;
	private static Hello cRcvHello;
	//play阶段的数据对象
	private static Play sPlay;
	private static Play sRcvPlay;
	private static Play cPlay;
	private static Play cRcvPlay;
	
	public static AddRSA SaddRSA=new AddRSA();
	public static AddRSA CaddRSA=new AddRSA();
	

	//将对象封装成json串
	public static String toJson(Object object) {
		return gson.toJson(object);
	}
	
	//解析服务器端接收到的Hello的json数据，【服务器端调用】
	public static void sRcvToHello(String sRcvJson) {
		sRcvHello=toHello(sRcvJson);
		System.out.println(sRcvHello.getPublicKey());
	}
	
	//解析客户端接收到的hello的json数据，【客户端调用】
	public static void cRcvToHello(String cRcvJson) {
		cRcvHello=toHello(cRcvJson);
		System.out.println(cRcvHello.getPublicKey());
	}

	//hello阶段json数据解析
	public static Hello toHello(String json) {
		return (Hello) gson.fromJson(json, Hello.class);
	}
	
	//将服务器端要发送的hello数据进行json封装，【服务器端调用】
	public static String sHelloToJson() {
		sHello=new Hello("com12-S", SaddRSA.strRasPublicKey);
		//System.out.println("shello:"+sHello.getPublicKey());
		return gson.toJson(sHello);
	}
	
	//将客户端要发送的hello数据进行json数据封装，【客户端调用】
	public static String cHelloToJson() {
		cHello=new Hello("com12-C", CaddRSA.strRasPublicKey);
		return gson.toJson(cHello);
	}
	
	//解析服务器端接收到的paly的json数据，【服务器端调用】
	public static void sRcvtoPlay(String sRcvJson){
		sRcvPlay= toPlay(sRcvJson);
		System.out.println("sRcvPlay"+sRcvPlay.getSign());
	}
	
	//解析客户端接收到的paly的json数据，【客户端调用】
	public static void cRcvPlay(String cRcvJson){
		cRcvPlay=toPlay(cRcvJson);
		System.out.println("cRcvPlay"+cRcvPlay.getSign());
	}
	
	//将服务器端的要发送的paly数据进行json封装，【服务器端调用】
	public static String sPlaytoJson(){
		//调用aes算法
		return gson.toJson(sPlay);
	}
	
	//将客户端要发送的play数据进行json封装，【客户端调用】
	public static String cPlaytoJson(){
		//调用aes算法
		return gson.toJson(cPlay);
	}
	
	//Play阶段json数据的解析
	public static Play toPlay(String json){
		return (Play) gson.fromJson(json, Play.class);
	}

	public static Hello getsRcvHello() {
		return sRcvHello;
	}
}
