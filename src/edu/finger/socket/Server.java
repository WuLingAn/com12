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

import edu.finger.Security.addAES;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.Result;
import edu.finger.Utils.SecurityHelper;

public class Server {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		InputStream is = null;
		String info = null;
		OutputStream os = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String finger = null;

		try {
			serverSocket = new ServerSocket(9000);
			Socket socket = serverSocket.accept();

			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			// 发送s的hello
			bw.write(JsonHelper.sHelloToJson());
			bw.newLine();
			bw.flush();

			info = br.readLine();
			JsonHelper.sRcvToHello(info);
			// System.out.println("hello:" + info);

			for (int i = 1; i <= 1000; i++) {
				// 对称加密工具对象声明位置修改到这
				addAES aes = new addAES();
				finger = Result.outFinger1();// 服务器端的出拳放在这
				// 发送s的play
				bw.write(JsonHelper.sPlaytoJson(i, finger, aes));
				bw.newLine();
				bw.flush();

				// 接收c发送的play
				info = br.readLine();
				JsonHelper.sRcvtoPlay(info);

				// 发送s的password
				bw.write(JsonHelper.sPasswordtoJson(i, aes.getAesKey()));
				bw.newLine();
				bw.flush();

				// 接收c发送的password
				info = br.readLine();
				JsonHelper.sRcvtoPassoword(info);
				// 尝试解密
				System.out.println(SecurityHelper.DecAll(JsonHelper.SaddRSA,JsonHelper
						.getsRcPlay().getPlay(), JsonHelper.getsRcvPassword()
						.getPassword(), JsonHelper.getsRcvHello()
						.getPublicKey()));
				// 进行签名判断，当不是对方发送时结束发送
				// if (!true) {
				// break;
				// }
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
}
