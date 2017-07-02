//服务器IOService的Stub，内容相同
package service;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface IOService extends Remote {

	public boolean newFile(String fileName) throws RemoteException;

	public boolean writeFile(String code, String fileName) throws RemoteException;

	public String readFile(String username, String fileName) throws RemoteException;

	public ArrayList<String> readFileList(String username) throws RemoteException;

	public ArrayList<String> readVersionList(String username, String fileName) throws RemoteException;

	public String readFileThroughPath(String string) throws RemoteException;

	public boolean isExisted(String username, String filename) throws RemoteException;

	public boolean makeDir(String string) throws RemoteException;

	public void clearFile(File file) throws RemoteException;

	boolean deleteDir(String username) throws RemoteException;

	boolean deleteFile(String username, String filename) throws RemoteException;
}
