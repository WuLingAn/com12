package edu.finger.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.finger.Security.AddRSA;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.Play;
import edu.finger.Utils.Result;

public class Server {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		InputStream is = null;
		String info = null;
		OutputStream os = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String finger = null;
		Play play;

		try {
			serverSocket = new ServerSocket(9000);
			Socket socket = serverSocket.accept();

			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			finger = Result.outFinger1();// 服务器端的出拳放在这
			bw.write(JsonHelper.sHelloToJson());
			bw.newLine();
			bw.flush();

			info = br.readLine();
			JsonHelper.sRcvToHello(info);
			
			for (int i = 0; i < 1000; i++) {
				//finger
				//bw.write("");
				bw.newLine();
				bw.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
				serverSocket.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String com(String finger, String info) {
		if (finger.equals(info))
			return "平局";
		if ("Paper".equals(finger)) {
			if ("Rock".equals(info))
				return "赢了";
			else if ("Scissors".equals(info))
				return "输了";
		}
		if ("Rock".equals(finger)) {
			if ("Scissors".equals(info))
				return "赢了";
			else if ("Paper".equals(info))
				return "输了";
		}
		if ("Scissors".equals(finger)) {
			if ("Paper".equals(info))
				return "赢了";
			else if ("Rock".equals(info))
				return "输了";
		}
		return "no";
	}

	private static String line(String S) {
		if (S.length() < 6)
			return S + "    ";
		return S;
	}

}
