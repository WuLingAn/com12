package edu.finger.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileTool {
	File targetFile;
	
	public FileTool(String fileName) {
		targetFile = new File(fileName);
		if(targetFile.exists()){
			targetFile.delete();
		}
		
	}
	public void savedToText(String StringToWrite) {
		
		OutputStreamWriter osw = null;

		try {
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				osw = new OutputStreamWriter(new FileOutputStream(targetFile),
						"utf-8");
				osw.write(StringToWrite);
			} else {
				osw = new OutputStreamWriter(new FileOutputStream(targetFile,
						true), "utf-8");
				osw.write("\n" + StringToWrite);
				osw.flush();
			}
		} catch (IOException e) {
			e.toString();
		} finally {
			try {
				if (osw != null)
					osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String readFromFile(String fileName) {
		String foldername = fileName;
		File folder = new File(foldername);
		if (folder == null || !folder.exists()) {
			folder.mkdirs();
		}
		// 创建文件
		File targetFile = new File(fileName);
		String readStr = "";

		try {
			if (!targetFile.exists()) {
				// 文件不存在
				targetFile.createNewFile();
				return "NO FILE ERROR";
			} else {
				// 读取
				InputStream in = new BufferedInputStream(new FileInputStream(
						targetFile));
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "utf-8"));

				String tmp;
				while ((tmp = br.readLine()) != null) {
					readStr += tmp;
				}
				br.close();
				in.close();
				return readStr;
			}

		} catch (Exception e) {
			return e.toString();
		}
	}

/*	public static void main(String[] args) {
		FileTool fileTool = new FileTool();
		fileTool.savedToText("G:log.txt", "name");
		System.out.println(fileTool.readFromFile("G:log.txt"));
	}*/
}
