package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;

import rmi.RemoteHelper;

public class OpenFrame extends JFrame {
	private JTextField textField;// 输入文件名区域
	public JTextArea textArea;// 展示文件区域
	static String filename;

	public OpenFrame() {
		new JFrame("Open");
		setVisible(true);
		setBounds(400, 250, 300, 250);
		setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("微软雅黑", Font.BOLD, 15));
		add(textArea);

		JScrollPane sp = new JScrollPane(textArea);
		sp.setBounds(20, 0, 240, 133);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp);

		textField = new JTextField();
		textField.setBounds(20, 150, 130, 25);
		textField.setFont(new Font("微软雅黑", Font.BOLD, 15));
		textField.setColumns(10);
		add(textField);

		JButton buttonOpen = new JButton("open");
		buttonOpen.setFont(new Font("微软雅黑", Font.BOLD, 15));
		buttonOpen.setBounds(160, 150, 100, 25);
		buttonOpen.addActionListener(new OpenActionListener());
		add(buttonOpen);

		setTextArea(MainFrame.userName);

	}

	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isExisted = false;
			String username = MainFrame.userName;
			filename = textField.getText();
			ArrayList<String> list = new ArrayList<String>();
			try {
				list = RemoteHelper.getInstance().getIOService().readFileList(username);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(filename)) {
					isExisted = true;
				}
			}
			if (isExisted == false) {
				JOptionPane.showMessageDialog(null, "This file don't exist!");
			} else {

				String code = "";
				//文件后缀
				if (filename.contains("bf")) {
					SaveFrame.End = ".bf";
					MainFrame.fileName = filename.substring(0, filename.length() - 3);
				} else if (filename.contains("ook")) {
					SaveFrame.End = ".ook";
					MainFrame.fileName = filename.substring(0, filename.length() - 4);
				} else {
					MainFrame.fileName = filename;
				}

				try {
					code = RemoteHelper.getInstance().getIOService().readFile(username, filename);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				MainFrame.codeArea.setText(code);
				textField.setText("");
				JOptionPane.showMessageDialog(null, "Open file successfully!");
			}
			dispose();
		}

	}

	public void setTextArea(String username) {
		textArea.setText("");
		textArea.append("File List:\n");
		ArrayList<String> fileNames = new ArrayList<String>();
		try {
			fileNames = RemoteHelper.getInstance().getIOService().readFileList(username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fileNames.size(); i++) {
			if (i != fileNames.size() - 1)
				textArea.append(fileNames.get(i) + "\n");
			else
				textArea.append(fileNames.get(i));
		}
	}
}
