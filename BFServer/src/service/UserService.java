//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface UserService extends Remote {
	// 使用一个treemap来存储用户名及对应的密码
	public Map<String, String> users_Map = new TreeMap<String, String>();

	public String login(String username, String password) throws RemoteException;

	public boolean logout() throws RemoteException;

	public boolean register(String username, String password) throws RemoteException;

	public boolean deleteUser(String username) throws RemoteException;

	public String modifyPassword(String username, String oldpassword, String newPassword) throws RemoteException;

}
