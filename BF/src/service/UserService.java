//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
	// 存储用户名及对应的密码
	// public Map<String,String> users_InfoMap = new TreeMap<String,String>();

	public String login(String username, String password) throws RemoteException;

	public boolean logout() throws RemoteException;

	public boolean register(String username, String password) throws RemoteException;

	public boolean deleteUser(String username) throws RemoteException;

	public String modifyPassword(String username, String oldpassword, String newPassword) throws RemoteException;
}
