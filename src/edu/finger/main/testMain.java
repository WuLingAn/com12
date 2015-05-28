package edu.finger.main;

import com.google.gson.Gson;

import edu.finger.RSA.AddRSA;
import edu.finger.Utils.Hello;

public class testMain {

	public static void main(String[] args) {
		Gson gson=new Gson();
		AddRSA addRSA=new AddRSA();
		Hello hello=new Hello("com12",addRSA.strRasPublicKey);
		System.out.println(addRSA.addSecurity("zhangwei"));
		System.out.println(addRSA.decSecurity(addRSA.addSecurity("zhangwei"),addRSA.strRasPublicKey));
		//System.out.println(gson.toJson(hello));
	}

}