package edu.finger.Utils;

import com.google.gson.Gson;

import edu.finger.Security.AddRSA;

public class JsonHelper {

	public static Gson gson = new Gson();
	private static Hello sHello;
	private static Hello sRcvHello;
	private static Hello cHello;
	private static Hello cRcvHello;
	public static AddRSA SaddRSA=new AddRSA();
	public static AddRSA CaddRSA=new AddRSA();
	

	public static String toJson(Object object) {
		return gson.toJson(object);
	}
	
	public static void sRcvToHello(String sRcvJson) {
		sRcvHello=toHello(sRcvJson);
		System.out.println(sRcvHello.getPublicKey());
	}
	public static void cRcvToHello(String cRcvJson) {
		cRcvHello=toHello(cRcvJson);
		System.out.println(cRcvHello.getPublicKey());
	}

	public static Hello toHello(String json) {
		return (Hello) gson.fromJson(json, Hello.class);
	}
	
	public static String sHelloToJson() {
		sHello=new Hello("com12-S", SaddRSA.strRasPublicKey);
		System.out.println("shello:"+sHello.getPublicKey());
		return gson.toJson(sHello);
	}
	
	public static String cHelloToJson() {
		cHello=new Hello("com12-C", CaddRSA.strRasPublicKey);
		return gson.toJson(cHello);
	}
}
