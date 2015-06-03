package edu.finger.Security;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.ArrayUtils;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class AddRSA {

	public String strRasPublicKey;
	public String strRsaPrivateKey;
	public RSAPublicKey rsaPublicKey;
	RSAPrivateKey rsaPrivateKey;
	KeyFactory keyFactory;
	Cipher cipher;

	public AddRSA() {
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			createKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void createKey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

			strRasPublicKey = Base64.encode(rsaPublicKey.getEncoded());
			strRsaPrivateKey = Base64.encode(rsaPrivateKey.getEncoded());
			// System.out.println("Public Key:" + strRasPublicKey);
			// System.out.println("Private Key:" + strRsaPrivateKey);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String addSecurity(String src) {
		byte[] result =null;
		byte[] temp=null;
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					rsaPrivateKey.getEncoded());
			PrivateKey privateKey = keyFactory
					.generatePrivate(pkcs8EncodedKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			int length=Base64.decode(src).length;
			//加密前先进行判断需要加密的数组的长度
			if(length<117){
				result = cipher.doFinal(Base64.decode(src));
			}else{
				for(int i=0;i<length;i+=100){
					temp=cipher.doFinal(ArrayUtils.subarray(Base64.decode(src), i, i+100));
					result=ArrayUtils.addAll(result, temp);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encode(result);
	}

	public String decSecurity(String src, String rcvKey) {
		byte[] result = null;
		try {
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					Base64.decode(rcvKey));
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(Base64.decode(src));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		//System.out.println(Base64.encode(result));
		return Base64.encode(result);
	}
}
