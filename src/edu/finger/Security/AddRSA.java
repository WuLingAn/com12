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
			cipher = Cipher.getInstance("RSA");
			createKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
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

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String addSecurity(String src) {
		byte[] result =null;
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					rsaPrivateKey.getEncoded());
			PrivateKey privateKey = keyFactory
					.generatePrivate(pkcs8EncodedKeySpec);
			//cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			//加密前先进行判断需要加密的数组的长度
			result = cipher.doFinal(Base64.decode(src));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("--"+java.util.Base64.getEncoder().encodeToString(result));
		//System.out.println("++"+Base64.encode(result));
		//return Base64.encode(result);
		return java.util.Base64.getEncoder().encodeToString(result);
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
		return Base64.encode(result);
	}
}
