package edu.finger.Security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class addAES {

	public static void useAes(String src){
		try {
			KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
			keyGenerator.init(new SecureRandom());
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] keyBytes=secretKey.getEncoded();
			
			Key key=new SecretKeySpec(keyBytes, "AES");
			
			Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println(Base64.encode(result));
			
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			result =cipher.doFinal(result);
			System.out.println(new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		useAes("zwwwww");
	}
}
