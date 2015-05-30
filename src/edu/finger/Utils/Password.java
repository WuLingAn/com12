package edu.finger.Utils;

public class Password {

	private int round;
	private String password;
	
	public Password() {
		super();
	}
	public Password(int round, String password) {
		super();
		this.round = round;
		this.password = password;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
