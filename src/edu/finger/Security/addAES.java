package edu.finger.Security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class addAES {

	private KeyGenerator keyGenerator;
	Cipher cipher;
	Key key;
	private String aesKey;

	public addAES() {
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			createKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createKey() {
		try {
			// 生成key
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(new SecureRandom());
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			// key转换
			aesKey = Base64.encode(keyBytes);
			key = new SecretKeySpec(keyBytes, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String addAesSecurity(String src) {
		try {
			// 加密
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(src.getBytes());
			return Base64.encode(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Security ERROR";
	}

	public String DecryptAes(String src, String StrKey) {
		try {
			// 解密
			Key key = new SecretKeySpec(Base64.decode(StrKey), "AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(Base64.decode(src));
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Decrypt Error";
	}
	//为取出需要发送的password使用
	public String getAesKey() {
		return aesKey;
	}
	
}
