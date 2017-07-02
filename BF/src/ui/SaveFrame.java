package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class SaveFrame extends JFrame {
	private JTextField textField;// 输入文件名
	private JRadioButton checkbox1;// 后缀单选框
	private JRadioButton checkbox2;
	private JLabel checkLabel1;
	private JLabel checkLabel2;
	String filename;
	String username;
	static String End = ".bf";// 后缀

	public SaveFrame() {
		new JFrame("Save");
		setVisible(true);
		setBounds(400, 250, 300, 200);
		setLayout(null);

		JLabel inputLabel = new JLabel("Input the file name");
		inputLabel.setBounds(10, 20, 150, 30);
		inputLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		add(inputLabel);

		textField = new JTextField();
		textField.setBounds(20, 60, 215, 30);
		textField.setFont(new Font("微软雅黑", Font.BOLD, 15));
		add(textField);

		checkLabel1 = new JLabel(".bf");
		checkLabel1.setBounds(20, 120, 30, 20);
		checkLabel1.setFont(new Font("微软雅黑", Font.BOLD, 15));
		checkbox1 = new JRadioButton(".bf", true);
		checkbox1.setBounds(50, 120, 20, 20);

		checkLabel2 = new JLabel(".ook");
		checkLabel2.setBounds(100, 120, 40, 20);
		checkLabel2.setFont(new Font("微软雅黑", Font.BOLD, 15));

		checkbox2 = new JRadioButton(".ook", false);
		checkbox2.setBounds(140, 120, 30, 20);
		checkbox1.addActionListener(new radioButtonListener());
		checkbox2.addActionListener(new radioButtonListener());
		ButtonGroup group = new ButtonGroup();

		group.add(checkbox1);
		group.add(checkbox2);
		add(checkLabel1);
		add(checkLabel2);
		add(checkbox1);
		add(checkbox2);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(180, 120, 100, 20);
		btnConfirm.addActionListener(new ConfirmListener());
		add(btnConfirm);
	}

	class ConfirmListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = MainFrame.codeArea.getText();
			filename = MainFrame.fileName = textField.getText();
			username = MainFrame.userName;
			// 文件路径
			String path = username + "\\" + filename + "_version" + "\\" + "version_";

			try {
				String tempCode = null;
				// 发生重名,提示是否覆盖
				if (RemoteHelper.getInstance().getIOService().isExisted(username, filename + End)) {
					boolean hasSameCode = false;
					// 检查当前代码是否与历史版本代码有重合
					for (int i = 1; i <= 3; i++) {
						tempCode = null;
						try {
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 1 + End);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						if (tempCode.equals(code)) {
							hasSameCode = true;
							break;
						}
					}
					// 历史版本中有相同code
					if (hasSameCode) {
						JOptionPane.showMessageDialog(null, "Same Code in versions.");
						textField.setText("");
						// MainFrame.codeArea.setText("");
						// MainFrame.inputArea.setText("");
						// MainFrame.outputArea.setText("");
					} else {
						// 保存文件名重合，选择是否覆盖
						int option = JOptionPane.showConfirmDialog(null,
								"File name occupied!Do you want to cover the old one?", "Tip",
								JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {

							// 覆盖的时候将新文件写入version_1,version_1里的写到version_2,version_2里的写到version_3
							RemoteHelper.getInstance().getIOService().writeFile(code, username + "\\" + filename + End);

							// version_2到version_3
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 2 + End);
							RemoteHelper.getInstance().getIOService().writeFile(tempCode, path + 3 + End);

							// version_1到version_2
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path + 1 + End);
							RemoteHelper.getInstance().getIOService().writeFile(tempCode, path + 2 + End);

							// 新文件到version_1
							RemoteHelper.getInstance().getIOService().writeFile(code, path + 1 + End);

							textField.setText("");
							MainFrame.codeArea.setText("");
							JOptionPane.showMessageDialog(null, "Save succeeded!");
						} else {
							JOptionPane.showMessageDialog(null, "Please input another name.");
							textField.setText("");
						}
					}
				} else {
					// 未发生重名，新建一个文件在用户的目录下
					RemoteHelper.getInstance().getIOService().newFile(username + "\\" + filename + End);
					code = MainFrame.codeArea.getText();
					RemoteHelper.getInstance().getIOService().writeFile(code, username + "\\" + filename + End);
					// 同时创建一个文件名+version 的历史版本目录
					RemoteHelper.getInstance().getIOService().makeDir(username + "\\" + filename + "_version");
					// 历史版本在目录下创建三个空文件
					for (int i = 0; i < 3; i++) {
						RemoteHelper.getInstance().getIOService().newFile(path + (i + 1) + End);
					}
					// 将新文件写到version_1里面
					RemoteHelper.getInstance().getIOService().writeFile(code, path + 1 + End);
					JOptionPane.showMessageDialog(null, "Save succeeded!");
					textField.setText("");

				}

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			dispose();
		}
	}

	/**
	 * 文件后缀
	 * 
	 * @author Administrator
	 * 
	 */
	class radioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals(".bf")) {
				End = ".bf";
			} else if (cmd.equals(".ook")) {
				End = ".ook";
			}
		}

	}
}
