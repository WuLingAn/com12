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
import edu.finger.Utils.JudgeUtil;
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
		String fingerFromOpposite = null;
		String result = null;
		int j=0;
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
				//System.out.println("play" + info);

				// 发送s的password
				bw.write(JsonHelper.sPasswordtoJson(i, aes.getAesKey()));
				bw.newLine();
				bw.flush();

				// 接收c发送的password
				info = br.readLine();
				JsonHelper.sRcvtoPassoword(info);
				// 解密对方发送的出拳
				fingerFromOpposite = SecurityHelper.DecAll(JsonHelper.SaddRSA,
						JsonHelper.getsRcPlay().getPlay(), JsonHelper
								.getsRcvPassword().getPassword(), JsonHelper
								.getsRcvHello().getPublicKey());
				// 判断这局的胜负
				result = JudgeUtil.judeg(finger, fingerFromOpposite);
//				System.out.println(finger + "-->" + fingerFromOpposite + ":"
//						+ result);
				if("赢了".equals(result)) j++;
				// 进行签名判断，当不是对方发送时结束发送
				// if (!true) {
				// break;
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("一共赢了："+j+"局");
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
}
