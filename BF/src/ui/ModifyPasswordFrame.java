package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextField;

import rmi.RemoteHelper;
import ui.RegisterFrame.ButtonActionListener;

public class ModifyPasswordFrame {
	private JTextField oldpasswordField;// 原密码
	private JTextField newPasswordField;// 新密码
	private JTextField userField;// 用户名
	private JButton buttonConfirm;// 确认按钮

	public String newpassword;
	public String username;
	public String oldpassword;
	JFrame frame;
	JLabel label;
	LoginFrame lf;
	MainFrame mainframe;
	RemoteHelper remoteHelper;

	public ModifyPasswordFrame() {

		frame = new JFrame("Modify password");
		frame.setLayout(null);

		label = new JLabel("Modify password");
		frame.add(label);
		label.setBounds(110, 30, 260, 50);
		label.setFont(new Font("微软雅黑", 1, 25));

		// 账号密码区
		JLabel label1 = new JLabel("old password");// 原密码
		JLabel label2 = new JLabel("new password");// 新密码
		JLabel label3 = new JLabel("username");// 用户名

		oldpasswordField = new JTextField();
		newPasswordField = new JTextField();
		userField = new JTextField();

		frame.add(label1);
		label1.setBounds(70, 200, 150, 50);
		label1.setFont(new Font("宋体", 1, 15));
		frame.add(oldpasswordField);
		oldpasswordField.setFont(new Font("微软雅黑", 1, 15));
		oldpasswordField.setBounds(240, 200, 170, 40);

		frame.add(label2);
		label2.setBounds(70, 250, 150, 50);
		label2.setFont(new Font("宋体", 1, 15));
		frame.add(newPasswordField);
		newPasswordField.setFont(new Font("微软雅黑", 1, 15));
		newPasswordField.setBounds(240, 250, 170, 40);

		frame.add(label3);
		label3.setBounds(70, 150, 150, 50);
		label3.setFont(new Font("宋体", 1, 15));
		frame.add(userField);
		userField.setFont(new Font("微软雅黑", 1, 15));
		userField.setBounds(240, 150, 170, 40);

		// 确认按钮
		buttonConfirm = new JButton("Modify");
		frame.add(buttonConfirm);
		buttonConfirm.setBounds(200, 320, 100, 50);
		buttonConfirm.addActionListener(new ButtonActionListener());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(720, 320, 500, 500);
		frame.setVisible(true);

	}

	class ButtonActionListener implements ActionListener {
		String b = "";

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				username = userField.getText();
				oldpassword = oldpasswordField.getText();
				newpassword = newPasswordField.getText();
				b = RemoteHelper.getInstance().getUserService().modifyPassword(username, oldpassword, newpassword);
				RemoteHelper.getInstance().getIOService().writeFile(newpassword, username + "\\" + "password.txt");
				if (b.equals("0")) {
					JOptionPane.showMessageDialog(null, "Modify password successfully, please login again.");
					frame.dispose();
				} else if (b.equals("-1")) {
					JOptionPane.showMessageDialog(null, "The user don't exist!");
				} else if (b.equals("1")) {
					JOptionPane.showMessageDialog(null, "The old password is wrong!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
