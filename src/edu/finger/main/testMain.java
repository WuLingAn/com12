package edu.finger.main;

import edu.finger.Security.addAES;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.SecurityHelper;

public class testMain {

	public static void main(String[] args) {

		addAES aes = new addAES();

		JsonHelper.cRcvToHello(JsonHelper.sHelloToJson());
		JsonHelper.cRcvtoPlay(JsonHelper.sPlaytoJson(1, "ccccc", aes));
		JsonHelper.cRcvtoPassoword(JsonHelper.sPasswordtoJson(1,
				aes.getAesKey()));
		JsonHelper.sRcvToHello(JsonHelper.cHelloToJson());
		JsonHelper.sRcvtoPlay(JsonHelper.cPlaytoJson(1, "3333", aes));
		JsonHelper.sRcvtoPassoword(JsonHelper.cPasswordtoJson(1,
				aes.getAesKey()));

		/*
		 * System.out.println(SecurityHelper.DecAll(JsonHelper.getcRcPlay()
		 * .getPlay(), JsonHelper.getcRcvPassword().getPassword(),
		 * JsonHelper.getcRcvHello()));
		 */

		System.out.println(JsonHelper.getsRcPlay().getPlay());
		System.out.println(JsonHelper.getsRcvPassword().getPassword());
		System.out.println(JsonHelper.getsRcvHello().getPublicKey());
		System.out.println();
		System.out.println(JsonHelper.getcRcPlay().getPlay());
		System.out.println(JsonHelper.getcRcvPassword().getPassword());
		System.out.println(JsonHelper.getcRcvHello().getPublicKey());

		System.out.println(SecurityHelper.DecAll(JsonHelper.getcRcPlay()
				.getPlay(), JsonHelper.getcRcvPassword().getPassword(),
				JsonHelper.getcRcvHello().getPublicKey()));
		System.out.println(SecurityHelper.DecAll(JsonHelper.getsRcPlay()
				.getPlay(), JsonHelper.getsRcvPassword().getPassword(),
				JsonHelper.getsRcvHello().getPublicKey()));

	}

}