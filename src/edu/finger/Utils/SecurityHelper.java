package edu.finger.Utils;

import edu.finger.Security.addAES;

public class SecurityHelper {
	private static addAES aesForDec = new addAES();

	// RSA加密
	public static String useRSA(String src) {
		return JsonHelper.SaddRSA.addSecurity(src);
	}

	// AES加密,为了能取出对称密钥，改变aes的声明位置
	public static String useAES(addAES aes, String src) {
		// addAES aes = new addAES();
		return aes.addAesSecurity(src);
	}

	// 调用两次加密
	public static String playToSend(addAES aes, String src) {
		return useRSA(useAES(aes, src));
	}

	// 对加密后的加密得到sign
	public static String signToSend(String toSendPlay) {
		return useRSA(toSendPlay);
	}

	// 使用收到的publickey解密
	public static String DecRSA(String get, String helloPublicKey) {
		return JsonHelper.SaddRSA.decSecurity(get,
				helloPublicKey);
	}

	// 使用AES解密
	public static String DecAES(String src, String key) {
		return aesForDec.DecryptAes(src, key);
	}

	// 解密方法组装机器
	public static String DecAll(String get, String key,String helloPublicKey) {
		return DecAES(DecRSA(get,helloPublicKey), key);
	}
}
