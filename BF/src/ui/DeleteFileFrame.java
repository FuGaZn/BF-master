package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;

import rmi.RemoteHelper;

public class DeleteFileFrame extends JFrame {
	private JTextField textField;// 输入文件名区
	public JTextArea textArea;// 显示所有文件区
	// static String filename;

	public DeleteFileFrame() {
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

		JButton buttonOpen = new JButton("delete");
		buttonOpen.setFont(new Font("微软雅黑", Font.BOLD, 15));
		buttonOpen.setBounds(160, 150, 100, 25);
		buttonOpen.addActionListener(new DeleteActionListener());
		add(buttonOpen);

		setTextArea(MainFrame.userName);

	}

	class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isExisted = false;
			String username = MainFrame.userName;
			String filename = textField.getText();
			ArrayList<String> list = new ArrayList<String>();// 展示所有存在的文件
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

				boolean b = false, c = false;
				try {
					String str = "";
					if (filename.substring(filename.length() - 3).equals("txt")) {// 删除TXT文件
						// 删除文件
						b = RemoteHelper.getInstance().getIOService().deleteFile(username, filename);
					} else {
						if (filename.contains(".bf")) {
							str = filename.substring(0, filename.length() - 3);
						} else {
							str = filename.substring(0, filename.length() - 4);
						}
						// 删除文件
						b = RemoteHelper.getInstance().getIOService().deleteFile(username, filename);
						// 删除版本文件夹
						c = RemoteHelper.getInstance().getIOService().deleteDir(username + "\\" + str + "_version");

					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				textField.setText("");
				MainFrame.codeArea.setText("");
				JOptionPane.showMessageDialog(null, "Delete file successfully!");
			}

			dispose();
		}

	}

	// 展示文件列表
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
