package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginFrame extends JFrame {
	JTextField textField1;// 账号
	JTextField textField2;// 密码

	private JButton buttonConfirm;// 确认按钮
	private JButton buttonsignup;// 注册按钮
	private JButton buttonChangePassword;// 更改密码按钮
	public String username;
	public String password;
	JFrame frame;
	String b = "";// 登陆状态
	MainFrame mainFrame;

	public LoginFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		frame = new JFrame("Login");
		frame.setLayout(null);

		JLabel label = new JLabel("User Login");
		frame.add(label);
		label.setBounds(190, 30, 200, 50);
		label.setFont(new Font("微软雅黑", 1, 25));

		JLabel label1 = new JLabel("User ID");
		JLabel label2 = new JLabel("Password");

		textField1 = new JTextField();
		textField2 = new JTextField();

		frame.add(label1);
		label1.setBounds(110, 150, 100, 50);
		label1.setFont(new Font("宋体", 1, 15));
		frame.add(textField1);
		textField1.setFont(new Font("微软雅黑", 1, 15));
		textField1.setBounds(240, 150, 170, 40);

		frame.add(label2);
		label2.setBounds(110, 200, 100, 50);
		label2.setFont(new Font("宋体", 1, 15));
		frame.add(textField2);
		textField2.setFont(new Font("微软雅黑", 1, 15));
		textField2.setBounds(240, 200, 170, 40);

		// 确认键
		buttonConfirm = new JButton("confirm");
		// 注册键
		buttonsignup = new JButton("register");

		frame.add(buttonConfirm);
		buttonConfirm.setBounds(100, 300, 100, 50);

		frame.add(buttonsignup);
		buttonsignup.setBounds(300, 300, 100, 50);

		buttonConfirm.addActionListener(new LogActionListener());
		buttonsignup.addActionListener(new RegisterActionListener());

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(700, 300, 500, 500);
		frame.setVisible(true);

	}

	/**
	 * 登陆按钮
	 * 
	 * @author Administrator
	 *
	 */
	class LogActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			username = textField1.getText();
			password = textField2.getText();
			try {
				b = RemoteHelper.getInstance().getUserService().login(username, password);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			// 判断账号是否存在或密码是否错误
			if (b.equals("0")) {
				JOptionPane.showMessageDialog(null, "Login successly! Welcome, " + username);
				frame.dispose();
				MainFrame.userName = username;
				MainFrame.sign.setText("user: " + username);
				MainFrame.isLogin = true;
				MainFrame.userName = username;
				MainFrame.password = password;

			} else if (b.equals("1")) {// 密码错误
				JOptionPane.showMessageDialog(null, "Login failed! Password is wrong!");
			} else if (b.equals("-1"))// 账号不存在
				JOptionPane.showMessageDialog(null, "Login failed! This account doesn't exist!");

		}
	}

	/**
	 * 注册按钮
	 * 
	 * @author Administrator
	 *
	 */
	class RegisterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new RegisterFrame();
		}
	}

}
