package edu.finger.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.finger.Security.addAES;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.Result;
import edu.finger.Utils.SecurityHelper;
/**
 * now ,现在想一想我们得到的play信息和password信息怎么保存和处理
 * @author zLing
 *
 */
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

			// 发送c的hello
			bw.write(JsonHelper.cHelloToJson());
			bw.newLine();
			bw.flush();
			// 得到s的hello
			info = br.readLine();
			// 得到存入cRcvHello
			JsonHelper.cRcvToHello(info);

			for (int i = 1; i <= 1000; i++) {
				addAES aes=new addAES();
				finger = Result.outFinger2();
//				System.out.println(finger);
				// 从s得到play数据
				info = br.readLine();
				// 得到存入cRcvtoPlay
				JsonHelper.cRcvtoPlay(info);
				// 进行签名判断，确定是否是对方发送的数据包
				if (true) {
					// 发送c的play
					bw.write(JsonHelper.cPlaytoJson(i, finger,aes));
					bw.newLine();
					bw.flush();
				}
				//得到s的password
				info = br.readLine();
				JsonHelper.cRcvtoPassoword(info);
				//发送c的password
				bw.write(JsonHelper.cPasswordtoJson(i, aes.getAesKey()));
				bw.newLine();
				bw.flush();
				
				System.out.println(SecurityHelper.DecAll(JsonHelper.CaddRSA,JsonHelper.getcRcPlay()
						.getPlay(), JsonHelper.getcRcvPassword().getPassword(),
						JsonHelper.getcRcvHello().getPublicKey()));
			}

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