package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import service.IOService;

public class IOServiceImpl implements IOService {

	@Override
	//新建文件
	public boolean newFile(String fileName) throws RemoteException {
		File newFile = new File(fileName);
		boolean isSucceed = false;
		try {
			isSucceed = newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			isSucceed = false;
		}
		return isSucceed;
	}

	@Override
	//把代码写到文件里
	public boolean writeFile(String code, String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			try {
				FileWriter fw = new FileWriter(fileName);
				fw.write(code);
				fw.flush();
				fw.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public String readFile(String username, String fileName) {
		try {
			return readFileThroughPath(username + "\\" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	// 文件列表
	public ArrayList<String> readFileList(String username) {
		File f = new File(username);
		String[] tempList = f.list();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < tempList.length; i++) {
			if (!(new File(username + "\\" + tempList[i]).isDirectory())) {
				list.add(tempList[i]);
			}
		}
		return list;
	}
	/**
	 * 通过path读取文件
	 * 
	 * @param path
	 * @return
	 */
	public String readFileThroughPath(String path) throws RemoteException {
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	// 判断文件是否存在
	public boolean isExisted(String username, String filename) throws RemoteException {
		for (String name : readFileList(username)) {
			if (filename.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	// 创建一个目录（文件夹）
	public boolean makeDir(String username) throws RemoteException {
		boolean isSucceed = false;
		File file = new File(username);
		isSucceed = file.mkdir();
		if (isSucceed) {
			JOptionPane.showMessageDialog(null, "create file directory successfully");
		}
		return isSucceed;

	}

	@Override
	// 清空文件的内容
	public void clearFile(File file) throws RemoteException {
		try {
			FileWriter fw = new FileWriter(file, false);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	// 删除整个文件夹的内容
	public boolean deleteDir(String username) throws RemoteException {
		File userDir = new File(username);
		String[] children = userDir.list();
		// 将子文件全部删除
		for (int i = 0; i < children.length; i++) {
			File child = new File(username + "\\" + children[i]);
			if (child.isDirectory()) {
				String[] children2 = child.list();
				for (int j = 0; j < children2.length; j++) {
					File child2 = new File(username + "\\" + children[i] + "\\" + children2[j]);
					child2.delete();
				}
			}
			child.delete();
		}
		userDir.delete();
		return true;
	}

	@Override
	// 删除文件
	public boolean deleteFile(String username, String filename) {
		File file = new File(username + "\\" + filename);
		file.delete();
		return true;
	}
}
