//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		String res = code;
		if (!res.contains("Ook")) {
			res = bfChange(code, param);
		} else {
			res = OokChange(code, param);
		}
		return res;
	}

	public String bfChange(String code, String param) {
		if (param == null) {
			param = "0";
		}
		int[] Array = new int[1000];
		int ArrayInd = 0;
		ArrayList<Integer> loopPtr = new ArrayList<Integer>();
		int paramIndex = 0;
		int stCount = 0;
		String result = "";

		while (stCount < code.length()) {
			switch (code.charAt(stCount)) {
			case '>':
				ArrayInd++;
				stCount++;
				break;
			case '<':
				ArrayInd--;
				stCount++;
				break;
			case '+':
				Array[ArrayInd]++;
				stCount++;
				break;
			case '-':
				Array[ArrayInd]--;
				stCount++;
				break;
			case ',':
				Array[ArrayInd] = param.charAt(paramIndex);
				paramIndex++;
				stCount++;
				break;
			case '.':
				result += (char) Array[ArrayInd];
				stCount++;
				break;
			case '[':
				if (Array[ArrayInd] == 0) {
					stCount++;
					while (code.charAt(stCount) != ']') {
						stCount++;
					}
					stCount++;
				} else {
					loopPtr.add(stCount);
					stCount++;
				}
				break;
			case ']':
				if (Array[ArrayInd] != 0) {
					stCount = loopPtr.get(loopPtr.size() - 1) + 1;
				} else {
					stCount++;
					loopPtr.remove(loopPtr.size() - 1);
				}
				break;
			}
		}
		return result;
	}

	public String OokChange(String str, String data) {
		String str2 = "";
		String stElement = "";
		if (data == null) {
			data = "0";
		}
		for (int i = 0; i + 10 <= str.length(); i = i + 10) {
			stElement = str.substring(i, i + 10);
			if (stElement.equals("Ook. Ook? ")) {
				str2 = str2 + ">";
			} else if (stElement.equals("Ook? Ook. ")) {
				str2 = str2 + "<";
			} else if (stElement.equals("Ook. Ook. ")) {
				str2 = str2 + "+";
			} else if (stElement.equals("Ook! Ook! ")) {
				str2 = str2 + "-";
			} else if (stElement.equals("Ook! Ook. ")) {
				str2 = str2 + ".";
			} else if (stElement.equals("Ook. Ook! ")) {
				str2 = str2 + ",";
			} else if (stElement.equals("Ook! Ook? ")) {
				str2 = str2 + "[";
			} else if (stElement.equals("Ook? Ook! ")) {
				str2 = str2 + "]";
			}
		}
		String result = bfChange(str2, data);
		return result;
	}
}