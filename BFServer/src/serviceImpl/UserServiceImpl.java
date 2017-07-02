package serviceImpl;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.RemoteHelper;
import service.IOService;
import service.UserService;;

public class UserServiceImpl implements UserService {

	@Override
	//登陆
	public String login(String username, String password) throws RemoteException {
		String isLogin = "0";
		if (users_Map.containsKey(username)) {
			if (users_Map.get(username).equals(password)) {
				isLogin = "0"; // 登陆成功
			} else {
				isLogin = "1";// 密码错误
			}
		} else {
			isLogin = "-1";// 用户名错误
		}
		return isLogin;
	}

	@Override
	public boolean logout() throws RemoteException {
		return false;
	}

	@Override
	//注册
	public boolean register(String username, String password) throws RemoteException {
		boolean isRegister = false;
		if (!users_Map.containsKey(username)) {
			users_Map.put(username, password);
			File file = new File(username + "/password.txt");
			try {
				if (file.createNewFile()) {
					FileWriter filewriter = new FileWriter(username + "/password.txt");
					filewriter.write(password);
					filewriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			isRegister = true;
		}
		return isRegister;
	}

	@Override
	//删除账号
	public boolean deleteUser(String username) throws RemoteException {
		users_Map.remove(username);
		return true;
	}

	@Override
	//修改密码
	public String modifyPassword(String username, String oldpassword, String newPassword) throws RemoteException {
		String b = "";
		if (users_Map.containsKey(username)) {
			if (users_Map.get(username).equals(oldpassword)) {
				users_Map.put(username, newPassword);
				b = "0";// 修改成功
				File file2 = new File(username + "/password.txt");
				FileWriter filewriter;
				try {
					filewriter = new FileWriter(file2);
					filewriter.write(newPassword);
					filewriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				b = "1";// 原密码输入错误
			}
		} else {
			b = "-1";// 用户名不存在
		}

		return b;
	}
}
