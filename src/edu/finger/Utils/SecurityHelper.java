package edu.finger.Utils;

import edu.finger.Security.addAES;

public class SecurityHelper {
	private static addAES aesForDec = new addAES();

	// RSA加密
	public static String useRSA(String src) {
		return JsonHelper.SaddRSA.addSecurity(src);
	}

	// AES加密
	public static String useAES(String src) {
		addAES aes = new addAES();
		return aes.addAesSecurity(src);
	}

	// 调用两次加密
	public static String playToSend(String src) {
		return useRSA(useAES(src));
	}

	// 对加密后的加密得到sign
	public static String signToSend(String toSendPlay) {
		return useRSA(toSendPlay);
	}

	// 使用收到的publickey解密
	public static String DecRSA(String get) {
		return JsonHelper.SaddRSA.decSecurity(get, JsonHelper.getsRcvHello()
				.getPublicKey());
	}

	//使用AES解密
	public static String DecAES(String src, String key) {
		return aesForDec.DecryptAes(src, key);
	}
}
