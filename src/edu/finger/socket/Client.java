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
import edu.finger.Utils.Context;
import edu.finger.Utils.FileTool;
import edu.finger.Utils.JsonHelper;
import edu.finger.Utils.JudgeUtil;
import edu.finger.Utils.Result;
import edu.finger.Utils.SecurityHelper;

/**
 * @author zLing & Fang-SQ
 *
 */
public class Client {

	public static void main(String[] args) {
		OutputStream os = null;
		InputStream is = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String info = null;
		String finger = null;
		String fingerFromOpposite = null;
		String result = null;
		Socket socket = null;
		int j = 0;
		int k = 0;
		int sum_p = 0;
		int sum_r = 0;
		int sum_s = 0;
		FileTool fileTool = new FileTool("G:\\client.log");
		int msgType = -1;
		try {
			socket = new Socket("localhost", 9000);
			os = socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(os));
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			// 发送c的hello
			packageToSend(Context.MSG_TYPE_HELLO, bw, JsonHelper.cHelloToJson());
			// 得到s的hello
			msgType = br.read();
			info = br.readLine();
			// 得到存入cRcvHello
			getMsg(msgType, info);

			for (int i = 1; i <= 1000; i++) {
				addAES aes = new addAES();
				finger = Result.outFinger2(sum_p, sum_r, sum_s);
				// 从s得到play数据
				msgType = br.read();
				info = br.readLine();
				// 得到存入cRcvtoPlay
				getMsg(msgType, info);
				// 进行签名判断，确定是否是对方发送的数据包
				// 发送c的play
				packageToSend(Context.MSG_TYPE_PLAY, bw,
						JsonHelper.cPlaytoJson(i, finger, aes));
				// 得到s的password
				msgType = br.read();
				info = br.readLine();
				getMsg(msgType, info);
				// 发送c的password
				packageToSend(Context.MSG_TYPE_PASSWORD, bw, JsonHelper.cPasswordtoJson(i, aes.getAesKey()));
				// 解密从对方发送的数据
				fingerFromOpposite = SecurityHelper.DecAll(JsonHelper.CaddRSA,
						JsonHelper.getcRcPlay().getPlay(), JsonHelper
								.getcRcvPassword().getPassword(), JsonHelper
								.getcRcvHello().getPublicKey());
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
				// 对当前局进行判断
				result = JudgeUtil.judeg(finger, fingerFromOpposite);
				if ("赢了".equals(result))
					j++;
				if ("输了".equals(result))
					k++;
				fileTool.savedToText("round:" + i + ": " + "get paly:"
						+ JsonHelper.getcRcPlay().getPlay() + " get sign:"
						+ JsonHelper.getcRcPlay().getSign() + " get finger:"
						+ fingerFromOpposite + " my finger:" + finger
						+ " result:" + result);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			JsonHelper.cRcvToHello(info);
			break;
		case 1:
			return JsonHelper.cRcvtoPlay(info);
		case 2:
			JsonHelper.cRcvtoPassoword(info);
			break;
		}
		return true;
	}
}