package edu.finger.Utils;
/**
 * @author zLing
 *
 */
public class Hello {
	private String name;
	private String publicKey;
	
	public Hello(String name, String publicKey) {
		super();
		this.name = name;
		this.publicKey = publicKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
