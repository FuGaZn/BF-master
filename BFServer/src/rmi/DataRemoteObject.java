package rmi;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService, ExecuteService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;

	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService = new ExecuteServiceImpl();

	}

	@Override
	public boolean writeFile(String code, String fileName) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.writeFile(code, fileName);
	}

	@Override
	public boolean newFile(String userName) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.newFile(userName);
	}

	@Override
	public String readFile(String username, String fileName) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.readFile(username, fileName);
	}

	@Override
	public boolean deleteFile(String username, String filename) throws RemoteException {
		return iOService.deleteFile(username, filename);
	}

	@Override
	public ArrayList<String> readFileList(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.readFileList(username);
	}

	@Override
	public boolean isExisted(String username, String filename) throws RemoteException {
		return iOService.isExisted(username, filename);
	}

	@Override
	public String readFileThroughPath(String path) throws RemoteException {
		return iOService.readFileThroughPath(path);
	}

	@Override
	public boolean makeDir(String string) throws RemoteException {
		return iOService.makeDir(string);
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean register(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		boolean b = userService.register(username, password);

		return b;
	}

	@Override
	public boolean deleteUser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		boolean b = userService.deleteUser(username);
		return b;
	}

	@Override
	public String modifyPassword(String username, String oldpassword, String newPassword) throws RemoteException {

		return userService.modifyPassword(username, oldpassword, newPassword);
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		return executeService.execute(code, param);
	}

	@Override
	public boolean logout() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearFile(File file) throws RemoteException {
		iOService.clearFile(file);
	}

	@Override
	public boolean deleteDir(String username) throws RemoteException {
		return iOService.deleteDir(username);
	}
}
