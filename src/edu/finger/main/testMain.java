package edu.finger.main;

import com.google.gson.Gson;

import edu.finger.Security.AddRSA;
import edu.finger.Security.addAES;
import edu.finger.Utils.Hello;
import edu.finger.Utils.JsonHelper;

public class testMain {

	public static void main(String[] args) {
		//Gson gson=new Gson();
		//AddRSA addRSA=new AddRSA();
		//Hello hello=new Hello("com12",addRSA.strRasPublicKey);
		//System.out.println(addRSA.addSecurity("zhangwei"));
		//System.out.println(addRSA.decSecurity(addRSA.addSecurity("zhangwei"),addRSA.strRasPublicKey));
		//System.out.println(gson.toJson(hello));
		
		//addAES aes=new addAES();
		//aes.createKey();
		//System.out.println(aes.DecryptAes(aes.addAesSecurity("zhangwei"),aes.aesKey));
		
		JsonHelper.sRcvToHello("{\"name\":\"finger-guessing game\",\"publicKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFeGIkDI4DmvGN56FN73AggsOFGe7vusstswHaTS3Xg3RQEhW4Uu6jh4+Slx31knBLb9ChvMqrzAxDHrsT7ZgJu/nGGuRDbTL2pOIOqeGRvJ2uKzz1Ov2xVFATuHMhLXNJOA4fXgZY1HEUCmMMMYCIatXs12UaBVw3TcIvbksZ3wIDAQAB\"}");
		System.out.println(JsonHelper.getsRcvHello().getPublicKey());
	}

}