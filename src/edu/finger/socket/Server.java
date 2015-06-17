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
import edu.finger.Utils.Context;
import edu.finger.Utils.FileTool;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.JudgeUtil;
import edu.finger.Utils.Result;
import edu.finger.Utils.SecurityHelper;
/**
 * 
 * @author zLing & Fang-SQ
 *
 */
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
		int j = 0;
		int k = 0;
		int sum_p = 0;
		int sum_r = 0;
		int sum_s = 0;
		int msgType = -1;
		FileTool fileTool = new FileTool("G:\\server.log");
		try {
			serverSocket = new ServerSocket(9000);
			Socket socket = serverSocket.accept();

			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			// 发送s的hello
			packageToSend(Context.MSG_TYPE_HELLO, bw, JsonHelper.sHelloToJson());

			msgType = br.read();
			info = br.readLine();
			getMsg(msgType, info);
			// System.out.println("hello:" + info);

			for (int i = 1; i <= 1000; i++) {
				// 对称加密工具对象声明位置修改到这
				addAES aes = new addAES();
				finger = Result.outFinger1(sum_p, sum_r, sum_s);// 服务器端的出拳放在这
				// 发送s的play
				packageToSend(Context.MSG_TYPE_PLAY, bw,
						JsonHelper.sPlaytoJson(i, finger, aes));

				// 接收c发送的play
				msgType = br.read();
				info = br.readLine();
				getMsg(msgType, info);

				// 发送s的password
				packageToSend(Context.MSG_TYPE_PASSWORD, bw,
						JsonHelper.sPasswordtoJson(i, aes.getAesKey()));

				// 接收c发送的password
				msgType = br.read();
				info = br.readLine();
				getMsg(msgType, info);
				// 解密对方发送的出拳
				fingerFromOpposite = SecurityHelper.DecAll(JsonHelper.SaddRSA,
						JsonHelper.getsRcPlay().getPlay(), JsonHelper
								.getsRcvPassword().getPassword(), JsonHelper
								.getsRcvHello().getPublicKey());
				switch (fingerFromOpposite) {
				case "Paper":
					sum_p++;
					break;
				case "Rock":
					sum_r++;
					break;
				case "Scissors":
					sum_s++;
					break;
				}
				// 判断这局的胜负
				result = JudgeUtil.judeg(finger, fingerFromOpposite);
				if ("赢了".equals(result))
					j++;
				if ("输了".equals(result))
					k++;
				fileTool.savedToText("round:" + i + ": " + "get paly:"
						+ JsonHelper.getsRcPlay().getPlay() + " get sign:"
						+ JsonHelper.getsRcPlay().getSign() + " get finger:"
						+ fingerFromOpposite + " my finger:" + finger
						+ " result:" + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (j > k) {
				System.out.println("胜利^.^");
				System.out.println("一共赢了：" + j + "局");
			} else {
				System.out.println("失败-_-");
				System.out.println("一共赢了：" + j + "局");
			}
			// System.out.println("共：" + (j * 3 + k * 1) + "分");
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

	/**
	 * 发送消息的打包方法
	 * 
	 * @param msgType
	 *            发送信息的类型 0,1,2
	 * @param bw
	 *            写用
	 * @param msgToJson
	 *            要发送的Json信息
	 */
	private static void packageToSend(int msgType, BufferedWriter bw,
			String msgToJson) throws IOException {
		bw.write(msgType);
		bw.write(msgToJson);
		bw.newLine();
		bw.flush();
	}
	
	private static boolean getMsg(int msgType, String info) {
		switch (msgType) {
		case 0:
			JsonHelper.sRcvToHello(info);
			break;
		case 1:
			return JsonHelper.sRcvtoPlay(info);
		case 2:
			JsonHelper.sRcvtoPassoword(info);
			break;
		}
		return true;
	}
}
