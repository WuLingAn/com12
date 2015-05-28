package edu.finger.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.finger.Utils.Hello;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.Result;

public class Client {

	public static void main(String[] args) {
		OutputStream os = null;
		// PrintWriter pw = null;
		InputStream is = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String info = null;
		String finger = null;
		Socket socket = null;
		try {
			socket = new Socket("localhost", 9000);
			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			finger = Result.outFinger2();
			//发送c的hello
			bw.write(JsonHelper.cHelloToJson());
			bw.newLine();
			bw.flush();
			//得到s的hello
			info = br.readLine();
			System.out.println(info);
			//得到存入cRcvHello
			JsonHelper.cRcvToHello(info);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				os.close();
				bw.close();
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}